package com.edu.icesi.dev.dao.interfaces;

import java.util.List;

import com.edu.icesi.dev.model.Product;

public interface ProductDAO {

	Product save(Product product);

	Product update(Product product);

	void delete(Product product);

	Product findById(Integer productId);

	List<Product> findAll();

	List<Product> findByWorkorderSize();

	List<Product> findBySubcategoryId(Integer subcategoryId);

	List<Product> findByProductNumber(String productNumber);

	List<Product> findByUnitMeasure1Code(Integer unitmeasureCode);
}
