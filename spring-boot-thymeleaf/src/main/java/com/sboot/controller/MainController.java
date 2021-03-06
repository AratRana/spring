package com.sboot.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sboot.model.Product;
import com.sboot.properties.AppProperties;
import com.sboot.service.ProductService;

@Controller
public class MainController {

	@Autowired
	private ProductService productService;

	private AppProperties app;

	@Autowired
	public void setApp(AppProperties app) {
		this.app = app;
	}

	@GetMapping("/")
	public void index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("home");
	}

	@GetMapping("/home")
	public String home(HttpServletRequest request) {
		request.setAttribute("mode", "PD_VIEW");
		Collection<Product> products = productService.findAll();
		request.setAttribute("products", products);
		return "home";
	}

	@GetMapping("/editProduct")
	public String editProduct(@RequestParam Long id, HttpServletRequest request) {
		request.setAttribute("product", productService.findById(id));
		request.setAttribute("mode", "PD_EDIT");
		return "home";
	}

	@PostMapping("/save")
	public void updateProduct(@ModelAttribute Product product, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		productService.update(product);
		response.sendRedirect("home");
	}

	@GetMapping("/newProduct")
	public String newProduct(HttpServletRequest request) {
		request.setAttribute("mode", "PD_NEW");
		request.setAttribute("product",new Product());
		return "home";
	}

	@GetMapping("/delete")
	public void deleteProduct(@RequestParam Long id, HttpServletResponse response) throws IOException {
		productService.deletProduct(id);
		response.sendRedirect("home");
	}

	@GetMapping("/prop")
	public String showProperties(HttpServletRequest request) {
		request.setAttribute("mode", "PROP_VIEW");
		request.setAttribute("appProperties", app.toString());
		return "home";
	}
}
