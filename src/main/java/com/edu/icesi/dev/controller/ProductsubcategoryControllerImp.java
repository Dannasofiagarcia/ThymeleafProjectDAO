package com.edu.icesi.dev.controller;

import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.service.interfaces.ProductCategoryService;
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
public class ProductsubcategoryControllerImp implements ProductsubcategoryController {

	ProductSubcategoryService subcategoryService;
	ProductCategoryService categoryService;

	@Autowired
	public ProductsubcategoryControllerImp(ProductSubcategoryService subcategoryService,
			ProductCategoryService categoryService) {
		super();
		this.subcategoryService = subcategoryService;
		this.categoryService = categoryService;
	}

	@GetMapping("/subcategory/add")
	public String showAddSubcategory(Model model) {
		model.addAttribute("productsubcategory", new Productsubcategory());
		model.addAttribute("categories", categoryService.findAll());

		return "subcategories/add-subcategory";
	}

	@GetMapping("/subcategory/delete/{id}")
	public String deleteSubcategory(@PathVariable("id") Integer id, Model model) {
		Productsubcategory subcategory = subcategoryService.findById(id);
		if (subcategory == null)
			throw new IllegalArgumentException("Invalid subcategory id:" + id);
		subcategoryService.delete(subcategory);
		model.addAttribute("subcategories", subcategoryService.findAll());
		return "subcategories/index";
	}

	@GetMapping("/subcategory/{id}")
	public String getSubcategory(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("subcategories", subcategoryService.findById(id));
		return "subcategories/index";
	}

	@GetMapping("/subcategory/")
	public String indexSubcategory(Model model) {
		model.addAttribute("subcategories", subcategoryService.findAll());
		return "subcategories/index";
	}

	@PostMapping("/subcategory/add")
	public String addSubcategory(@Validated(ValidatedAdd.class) @ModelAttribute Productsubcategory subcategory,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (action.equals("Add sub-category")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("productsubcategory", subcategory);
				model.addAttribute("categories", categoryService.findAll());
				return "subcategories/add-subcategory";
			}
			subcategoryService.saveProductSubcategory(subcategory.getModifieddate(), subcategory.getName(),
					subcategory.getProductcategory().getProductcategoryid());

		}
		return "redirect:/subcategory/";
	}

	@GetMapping("/subcategory/edit/{id}")
	public String showUpdateSubcategory(@PathVariable("id") Integer id, Model model) {
		Productsubcategory subcategory = subcategoryService.findById(id);
		if (subcategory == null)
			throw new IllegalArgumentException("Invalid subcategory id:" + id);
		model.addAttribute("productsubcategory", subcategory);
		model.addAttribute("categories", categoryService.findAll());
		return "subcategories/update-subcategory";
	}

	@PostMapping("/subcategory/edit/{id}")
	public String updateSubcategory(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,
			@Validated(ValidatedEdit.class) @ModelAttribute Productsubcategory subcategory, BindingResult bindingResult,
			Model model) {
		if (action.equals("Edit sub-category")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("productsubcategory", subcategory);
				model.addAttribute("categories", categoryService.findAll());
				return "subcategories/update-subcategory";
			}
			subcategoryService.editProductSubcategory(id, subcategory.getModifieddate(), subcategory.getName(),
					subcategory.getProductcategory().getProductcategoryid());
			model.addAttribute("productsubcategory", subcategoryService.findAll());
		}
		return "redirect:/subcategory/";
	}

}
