package com.edu.icesi.dev.controller;

import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.service.UnitmeasureServiceImp;
import com.edu.icesi.dev.service.interfaces.ProductService;
import com.edu.icesi.dev.service.interfaces.ProductSubcategoryService;
import com.edu.icesi.dev.validated.ValidatedAdd;
import com.edu.icesi.dev.validated.ValidatedEdit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductControllerImp implements ProductController {
	ProductService productService;
	ProductSubcategoryService productSubcategoryService;
	UnitmeasureServiceImp unitmeasureService;

	@Autowired
	public ProductControllerImp(ProductService productService, ProductSubcategoryService productSubcategoryService,
			UnitmeasureServiceImp unitmeasureService) {
		super();
		this.productService = productService;
		this.productSubcategoryService = productSubcategoryService;
		this.unitmeasureService = unitmeasureService;
	}

	@GetMapping("/product/add/")
	public String showAddProduct(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("subcategories", productSubcategoryService.findAll());
		model.addAttribute("unitmeasures", unitmeasureService.findAll());
		return "products/add-product";
	}

	@GetMapping("/product/delete/{id}")
	public String deleteProduct(@PathVariable("id") Integer id, Model model) {
		Product product = productService.findById(id);
		if (product == null)
			throw new IllegalArgumentException("Invalid product id:" + id);
		productService.delete(product);
		model.addAttribute("products", productService.findAll());
		return "products/index";
	}

	@GetMapping("/product/{id}")
	public String getSubcategory(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("products", productService.findById(id));
		return "products/index";
	}

	@GetMapping("/product/")
	public String indexProducts(Model model) {
		model.addAttribute("products", productService.findAll());
		return "products/index";
	}

	@PostMapping("/product/add/")
	public String addProduct(@ModelAttribute @Validated(ValidatedAdd.class) Product product,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (action.equals("Add product")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("product", product);
				model.addAttribute("subcategories", productSubcategoryService.findAll());
				model.addAttribute("unitmeasures", unitmeasureService.findAll());
				return "products/add-product";
			}
			productService.saveProduct(product);
		}
		return "redirect:/product/";
	}

	@GetMapping("/product/edit/{id}")
	public String showUpdateProduct(@PathVariable("id") Integer id, Model model) {
		Product product = productService.findById(id);
		if (product == null)
			throw new IllegalArgumentException("Invalid product id:" + id);
		model.addAttribute("product", product);
		model.addAttribute("subcategories", productSubcategoryService.findAll());
		model.addAttribute("unitmeasures", unitmeasureService.findAll());
		return "products/update-product";
	}

	@PostMapping("/product/edit/{id}")
	public String updateProduct(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,
			@ModelAttribute @Validated(ValidatedEdit.class) Product product, BindingResult bindingResult, Model model) {
		if (action.equals("Edit product")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("product", product);
				model.addAttribute("subcategories", productSubcategoryService.findAll());
				model.addAttribute("unitmeasures", unitmeasureService.findAll());
				return "products/update-product";
			}
			productService.editProduct(id, product);
			model.addAttribute("products", productService.findAll());
		}
		return "redirect:/product/";
	}
}
