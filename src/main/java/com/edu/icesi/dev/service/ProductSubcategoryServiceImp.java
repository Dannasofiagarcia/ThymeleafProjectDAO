package com.edu.icesi.dev.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edu.icesi.dev.dao.interfaces.SubcategoryDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.repositories.ProductCategoryRepository;
import com.edu.icesi.dev.service.interfaces.ProductSubcategoryService;

import org.springframework.stereotype.Service;

@Service
public class ProductSubcategoryServiceImp implements ProductSubcategoryService {

	private SubcategoryDAO subcategoryRepository;
	private ProductCategoryRepository categoryRepository;

	public ProductSubcategoryServiceImp(SubcategoryDAO subcategoryRepository,
			ProductCategoryRepository categoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public Productsubcategory saveProductSubcategory(Date modifieddate, String name, Integer productcategoryId) {

		Productcategory pc;
		Productsubcategory ps;

		if (name.length() < 5) {
			throw new RuntimeException("No valid name - saveProductSubcategory()");
		}

		if (categoryRepository.findById(productcategoryId).isEmpty()) {
			throw new RuntimeException("No valid productcategory - saveProductSubcategory()");
		} else {
			pc = categoryRepository.findById(productcategoryId).get();
		}

		ps = new Productsubcategory();
		ps.setProducts(new ArrayList<Product>());
		ps.setModifieddate(modifieddate);
		ps.setProductcategory(pc);
		ps.setName(name);
		pc.addProductsubcategory(ps);
		subcategoryRepository.save(ps);
		categoryRepository.save(pc);

		return ps;
	}

	@Override
	public Productsubcategory editProductSubcategory(Integer id, Date modifieddate, String name,
			Integer productcategoryId) {

		Productcategory pc;
		Productsubcategory ps;

		if (subcategoryRepository.findById(id) == null) {
			throw new RuntimeException("productsubcategory no exists - saveProductSubcategory()");
		} else {
			ps = subcategoryRepository.findById(id);
		}

		if (name.length() < 5) {
			throw new RuntimeException("No valid name - saveProductSubcategory()");
		}

		if (categoryRepository.findById(productcategoryId).isEmpty()) {
			throw new RuntimeException("No valid productcategory - saveProductSubcategory()");
		} else {
			pc = categoryRepository.findById(productcategoryId).get();
		}

		ps.setModifieddate(modifieddate);
		ps.setProductcategory(pc);
		ps.setName(name);
		pc.addProductsubcategory(ps);
		subcategoryRepository.update(ps);
		categoryRepository.save(pc);
		return ps;
	}

	@Override
	public Productsubcategory findById(Integer id) {
		return subcategoryRepository.findById(id);
	}

	@Override
	public Iterable<Productsubcategory> findAll() {
		return subcategoryRepository.findAll();
	}

	@Override
	public void delete(Productsubcategory productSubcategory) {
		subcategoryRepository.delete(productSubcategory);

	}

	@Override
	public List<Product> getProducts(Integer id) {

		return subcategoryRepository.findById(id).getProducts();
	}

	@Override
	public Productsubcategory saveProductSubcategory(Productsubcategory subcategory) {

		Productcategory pc;
		Productsubcategory ps;

		if (subcategory.getName().length() < 5) {
			throw new RuntimeException("No valid name - saveProductSubcategory()");
		}

		if (categoryRepository.findById(subcategory.getProductcategory().getProductcategoryid()).isEmpty()) {
			throw new RuntimeException("No valid productcategory - saveProductSubcategory()");
		} else {
			pc = categoryRepository.findById(subcategory.getProductcategory().getProductcategoryid()).get();
		}

		if (subcategory.getProducts() == null) {
			subcategory.setProducts(new ArrayList<Product>());
		}
		subcategoryRepository.save(subcategory);
		categoryRepository.save(pc);

		return subcategory;
	}

	@Override
	public Productsubcategory editProductSubcategory(Integer id, Productsubcategory subcategory) {

		Productcategory pc;
		Productsubcategory ps;

		if (subcategoryRepository.findById(id) == null) {
			throw new RuntimeException("productsubcategory no exists - saveProductSubcategory()");
		} else {
			ps = subcategoryRepository.findById(id);
		}

		if (subcategory.getName().length() < 5) {
			throw new RuntimeException("No valid name - saveProductSubcategory()");
		}

		if (categoryRepository.findById(subcategory.getProductcategory().getProductcategoryid()).isEmpty()) {
			throw new RuntimeException("No valid productcategory - saveProductSubcategory()");
		} else {
			pc = categoryRepository.findById(subcategory.getProductcategory().getProductcategoryid()).get();
		}

		ps.setModifieddate(subcategory.getModifieddate());
		ps.setProductcategory(pc);
		ps.setName(subcategory.getName());
		pc.addProductsubcategory(ps);
		subcategoryRepository.update(ps);
		categoryRepository.save(pc);
		return ps;
	}
}
