package com.edu.icesi.dev.service.interfaces;

import java.util.Date;
import java.util.List;

import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productsubcategory;

public interface ProductSubcategoryService {

	public Productsubcategory saveProductSubcategory(Date modifieddate, String name, Integer productcategoryId);

	public Productsubcategory editProductSubcategory(Integer id, Date modifieddate, String name,
			Integer productcategoryId);

	public Productsubcategory findById(Integer id);

	public Iterable<Productsubcategory> findAll();

	public void delete(Productsubcategory productsubcategory);

	public List<Product> getProducts(Integer id);

	Productsubcategory editProductSubcategory(Integer id, Productsubcategory subcategory);

	Productsubcategory saveProductSubcategory(Productsubcategory subcategory);

}
