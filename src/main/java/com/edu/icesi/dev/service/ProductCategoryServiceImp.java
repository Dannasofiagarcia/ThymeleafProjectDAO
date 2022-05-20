package com.edu.icesi.dev.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.edu.icesi.dev.dao.interfaces.CategoryDAO;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.service.interfaces.ProductCategoryService;

import org.springframework.stereotype.Service;

@Service
public class ProductCategoryServiceImp implements ProductCategoryService {

	private CategoryDAO productCategoryRepository;

	public ProductCategoryServiceImp(CategoryDAO productCategoryRepository) {
		this.productCategoryRepository = productCategoryRepository;
	}

	@Override
	public Productcategory saveProductCategory(Date modifieddate, String name) {

		if (name.length() < 3) {
			throw new RuntimeException("No valid targettype - saveFetTaskinstance()");
		}

		Productcategory productCategory = new Productcategory();
		productCategory.setModifieddate(modifieddate);
		productCategory.setName(name);
		productCategory.setProductsubcategories(new ArrayList<Productsubcategory>());
		productCategoryRepository.save(productCategory);

		return productCategory;
	}

	@Override
	public Productcategory saveProductCategoryV2(Productcategory category) {

		if (category.getName().length() < 3) {
			throw new RuntimeException("No valid targettype - saveFetTaskinstance()");
		}

		if (category.getProductsubcategories() == null) {
			category.setProductsubcategories(new ArrayList<Productsubcategory>());
		}
		productCategoryRepository.save(category);

		return category;
	}

	@Override
	public Productcategory editProductCategory(Integer id, Date modifieddate, String name) {
		Productcategory pc;

		if (name.length() < 3) {
			throw new RuntimeException("No Productcategory - editProductCategory()");
		}

		if (productCategoryRepository.findById(id) == null) {
			throw new RuntimeException("product no exist - editProductCategory()");
		} else {
			pc = productCategoryRepository.findById(id);
		}
		pc.setName(name);
		pc.setModifieddate(modifieddate);
		productCategoryRepository.update(pc);
		return pc;
	}

	@Override
	public Productcategory editProductCategoryV2(Integer id, Productcategory category) {
		Productcategory pc;

		if (category.getName().length() < 3) {
			throw new RuntimeException("No Productcategory - editProductCategory()");
		}

		if (productCategoryRepository.findById(id) == null) {
			throw new RuntimeException("product no exist - editProductCategory()");
		} else {
			pc = productCategoryRepository.findById(id);
		}
		pc.setName(category.getName());
		pc.setModifieddate(category.getModifieddate());
		productCategoryRepository.update(pc);
		return pc;
	}

	@Override
	public Productcategory findById(Integer id) {

		return productCategoryRepository.findById(id);
	}

	@Override
	public Iterable<Productcategory> findAll() {
		return productCategoryRepository.findAll();
	}

	/**
	 * public Iterable<Product> findAllPatients() { return
	 * userRepository.findByType(UserType.patient); }
	 * 
	 * public Iterable<UserApp> findAllDoctors() { return
	 * userRepository.findByType(UserType.doctor); }
	 **/

	@Override
	public void delete(Productcategory category) {
		productCategoryRepository.delete(category);

	}

	@Override
	public List<Productsubcategory> getSubcategories(Integer id) {

		return productCategoryRepository.findById(id).getProductsubcategories();
	}

	/**
	 * public boolean confirmPassword(Long id, String currentPassword) { return
	 * userRepository.findById(id).get().getPassword().equals(currentPassword); }
	 **/

}
