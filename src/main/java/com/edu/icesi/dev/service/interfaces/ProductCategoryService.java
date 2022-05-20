package com.edu.icesi.dev.service.interfaces;

import java.util.Date;
import java.util.List;

import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;

public interface ProductCategoryService {

	public Productcategory saveProductCategory(Date modifieddate, String name);

	public Productcategory saveProductCategoryV2(Productcategory category);

	public Productcategory editProductCategory(Integer id, Date modifieddate, String name);

	public Productcategory editProductCategoryV2(Integer id, Productcategory category);

	public Productcategory findById(Integer id);

	public Iterable<Productcategory> findAll();

	public void delete(Productcategory product);

	public List<Productsubcategory> getSubcategories(Integer id);

}
