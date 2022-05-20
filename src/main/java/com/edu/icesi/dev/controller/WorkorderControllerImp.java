package com.edu.icesi.dev.controller;

import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.service.interfaces.ProductService;
import com.edu.icesi.dev.service.interfaces.ScrapreasonService;
import com.edu.icesi.dev.service.interfaces.WorkorderService;
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
public class WorkorderControllerImp implements WorkorderController {

	WorkorderService workorderService;
	ProductService productService;
	ScrapreasonService scrapreasonService;

	@Autowired
	public WorkorderControllerImp(WorkorderService workorderService, ProductService productService,
			ScrapreasonService scrapreasonService) {
		super();
		this.workorderService = workorderService;
		this.productService = productService;
		this.scrapreasonService = scrapreasonService;
	}

	@GetMapping("/workorder/add/")
	public String showAddWorkorder(Model model) {
		model.addAttribute("workorder", new Workorder());
		model.addAttribute("products", productService.findAll());
		model.addAttribute("scrapreasons", scrapreasonService.findAll());
		return "workorders/add-workorder";
	}

	@GetMapping("/workorder/delete/{id}")
	public String deleteWorkorder(@PathVariable("id") Integer id, Model model) {
		Workorder workorder = workorderService.findById(id);
		if (workorder == null)
			throw new IllegalArgumentException("Invalid workorder id:" + id);
		workorderService.delete(workorder);
		model.addAttribute("workorders", workorderService.findAll());
		return "workorders/index";
	}

	@GetMapping("/workorder/{id}")
	public String getWorkorder(@PathVariable("id") Integer id, Model model) {
		model.addAttribute("workorders", workorderService.findById(id));
		return "workorders/index";
	}

	@GetMapping("/workorder/")
	public String indexWorkorder(Model model) {
		model.addAttribute("workorders", workorderService.findAll());
		return "workorders/index";
	}

	@PostMapping("/workorder/add/")
	public String addWorkorder(@ModelAttribute @Validated(ValidatedAdd.class) Workorder workorder,
			BindingResult bindingResult, Model model, @RequestParam(value = "action", required = true) String action) {
		if (!action.equals("Cancel")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("workorder", workorder);
				model.addAttribute("products", productService.findAll());
				model.addAttribute("scrapreasons", scrapreasonService.findAll());
				return "workorders/add-workorder";
			}
			workorderService.saveWorkorder(workorder);
		}
		return "redirect:/workorder/";
	}

	@GetMapping("/workorder/edit/{id}")
	public String showUpdateWorkorder(@PathVariable("id") Integer id, Model model) {
		Workorder workorder = workorderService.findById(id);
		if (workorder == null)
			throw new IllegalArgumentException("Invalid workorder id:" + id);
		model.addAttribute("workorder", workorder);
		model.addAttribute("products", productService.findAll());
		model.addAttribute("scrapreasons", scrapreasonService.findAll());
		return "workorders/update-workorder";
	}

	@PostMapping("/workorder/edit/{id}")
	public String updateWorkorder(@PathVariable("id") Integer id,
			@RequestParam(value = "action", required = true) String action,
			@ModelAttribute @Validated(ValidatedEdit.class) Workorder workorder, BindingResult bindingResult,
			Model model) {
		if (action.equals("Edit work order")) {
			if (bindingResult.hasErrors()) {
				model.addAttribute("workorder", workorder);
				model.addAttribute("products", productService.findAll());
				model.addAttribute("scrapreasons", scrapreasonService.findAll());
				return "workorders/update-workorder";
			}
			workorderService.editWorkorder(id, workorder);
			model.addAttribute("workorder", workorderService.findAll());
		}
		return "redirect:/workorder/";
	}
}
