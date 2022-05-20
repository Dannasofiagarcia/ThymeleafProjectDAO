package com.edu.icesi.dev.controller;

import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.service.interfaces.ProductCategoryService;
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
public class ProductcategoryControllerImp implements ProductcategoryController {

	ProductCategoryService categoryService;

	@Autowired
	public ProductcategoryControllerImp(ProductCategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping("/category/add/")
	public String showAddCategory(Model model) {
		model.addAttribute("productcategory", new Productcategory());

		return "categories/add-category";
	}

	@GetMapping("/category/delete/{id}")
	public String deleteCategory(@PathVariable("id") Integer id, Model model) {
		Productcategory category = categoryService.findById(id);
		if (category == null)
			throw new IllegalArgumentException("Invalid category id:" + id);
		categoryService.delete(category);
		model.addAttribute("categories", categoryService.findAll());
		return "categories/index";
	}

	@GetMapping("/category/{id}")
	public String getCategory(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("categories", categoryService.findById(id));
		return "categories/index";
	}

	@GetMapping("/category/")
	public String indexCategory(Model model) {
		model.addAttribute("categories", categoryService.findAll());
		return "categories/index";
	}

	@PostMapping("/category/add")
	public String addCategory(@Validated(ValidatedAdd.class) @ModelAttribute Productcategory category,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (action.equals("Add category")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("productcategory", category);
				return "categories/add-category";
			}
			categoryService.saveProductCategory(category.getModifieddate(), category.getName());
		}
		return "redirect:/category/";
	}

	@GetMapping("/category/edit/{id}")
	public String showUpdateCategory(@PathVariable("id") Integer id, Model model) {
		Productcategory category = categoryService.findById(id);
		if (category == null)
			throw new IllegalArgumentException("Invalid category id:" + id);
		model.addAttribute("productcategory", category);
		return "categories/update-category";
	}

	@PostMapping("/category/edit/{id}")
	public String updateCategory(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,
			@Validated(ValidatedEdit.class) @ModelAttribute Productcategory category, BindingResult bindingResult,
			Model model) {
		if (action.equals("Edit category")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("productcategory", category);
				return "categories/update-category";
			}
			categoryService.editProductCategory(id, category.getModifieddate(), category.getName());
			model.addAttribute("categories", categoryService.findAll());
		}
		return "redirect:/category/";
	}

}
