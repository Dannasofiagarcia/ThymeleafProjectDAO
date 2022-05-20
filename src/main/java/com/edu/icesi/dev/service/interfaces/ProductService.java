package com.edu.icesi.dev.service.interfaces;

import java.util.ArrayList;

import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Workorder;

public interface ProductService {

	public Product saveProduct(Product product);

	public Product editProduct(Integer id, Product product);

	public Product findById(Integer id);

	public Iterable<Product> findAll();

	public void delete(Product product);

	public ArrayList<Workorder> getWorkorders(Integer id);

}
