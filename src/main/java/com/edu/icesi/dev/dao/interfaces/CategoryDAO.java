package com.edu.icesi.dev.dao.interfaces;

import java.util.List;

import com.edu.icesi.dev.model.Productcategory;

public interface CategoryDAO {

	Productcategory save(Productcategory productcategory);

	Productcategory update(Productcategory productcategory);

	void delete(Productcategory productcategory);

	Productcategory findById(Integer productcategoryId);

	List<Productcategory> findAll();
	/**
	 * List<Productcategory> findByWorkorderSize();
	 * 
	 * List<Product> findBySubcategoryId(Integer subcategoryId);
	 * 
	 * List<Product> findByProductNumberId(Integer productNumber);
	 * 
	 * List<Product> findByUnitMeasureCode(Integer unitmeasureCode);
	 **/
}
