package com.edu.icesi.dev.service;

import java.util.ArrayList;

import com.edu.icesi.dev.dao.interfaces.ProductDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.model.Unitmeasure;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.repositories.ProductCategoryRepository;
import com.edu.icesi.dev.repositories.ProductSubcategoryRepository;
import com.edu.icesi.dev.repositories.UnitmeasureRepository;
import com.edu.icesi.dev.service.interfaces.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImp implements ProductService {

	private ProductSubcategoryRepository subcategoryRepository;
	private ProductCategoryRepository categoryRepository;
	private ProductDAO productRepository;
	private UnitmeasureRepository unitmeasureRepository;

	@Autowired
	public ProductServiceImp(ProductSubcategoryRepository subcategoryRepository,
			ProductCategoryRepository categoryRepository, ProductDAO productRepository,
			UnitmeasureRepository unitmeasureRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
		this.unitmeasureRepository = unitmeasureRepository;
	}

	@Override
	public Product saveProduct(Product product) {

		Productsubcategory ps;
		Unitmeasure un1;
		Unitmeasure un2;

		if (product.getProductnumber().equals("")) {
			throw new RuntimeException("No valid productnumber - saveProduct()");
		}

		if (product.getSellenddate().after(product.getSellstartdate()) == false) {
			throw new RuntimeException("No valid dates - saveProduct()");
		}

		if (product.getWeight() < 0 || product.getSize() < 0) {
			throw new RuntimeException("No valid weight or size - saveProduct()");
		}

		if (subcategoryRepository.findById(product.getProductsubcategory().getProductsubcategoryid()).isEmpty()) {
			throw new RuntimeException("subcategory no exists - saveProduct()");
		} else {
			ps = subcategoryRepository.findById(product.getProductsubcategory().getProductsubcategoryid()).get();
			if (categoryRepository.findById(ps.getProductcategory().getProductcategoryid()).isEmpty()) {
				throw new RuntimeException("category no exists - saveProduct()");
			}
		}

		if (unitmeasureRepository.findById(product.getUnitmeasure1().getUnitmeasurecode()).isEmpty()) {
			throw new RuntimeException("unitmeasure1 no exists - saveProduct()");
		} else {
			un1 = unitmeasureRepository.findById(product.getUnitmeasure1().getUnitmeasurecode()).get();
		}

		if (unitmeasureRepository.findById(product.getUnitmeasure2().getUnitmeasurecode()).isEmpty()) {
			throw new RuntimeException("unitmeasure2 no exists - saveProduct()");
		} else {
			un2 = unitmeasureRepository.findById(product.getUnitmeasure2().getUnitmeasurecode()).get();
		}

		Product p = new Product();
		p.setName(product.getName());
		p.setProductnumber(product.getProductnumber());
		p.setProductsubcategory(ps);
		p.setSellenddate(product.getSellenddate());
		p.setSellstartdate(product.getSellstartdate());
		p.setSize(product.getSize());
		p.setUnitmeasure1(un1);
		p.setUnitmeasure2(un2);
		p.setWeight(product.getWeight());
		if (p.getWorkorders() == null) {
			p.setWorkorders(new ArrayList<Workorder>());
		}
		//

		productRepository.save(p);
		return p;
	}

	@Override
	public Product editProduct(Integer id, Product product) {

		Productsubcategory ps;
		Unitmeasure un1;
		Unitmeasure un2;
		Product p;
		if (productRepository.findById(id) == null) {
			throw new RuntimeException("product no exits - saveProduct()");
		} else {
			p = productRepository.findById(id);
		}

		if (product.getProductnumber().equals("")) {
			throw new RuntimeException("No valid productnumber - saveProduct()");
		}

		if (product.getSellenddate().after(product.getSellstartdate()) == false) {
			throw new RuntimeException("No valid dates - saveProduct()");
		}

		if (product.getWeight() < 0 || product.getSize() < 0) {
			throw new RuntimeException("No valid dates - saveProduct()");
		}

		if (subcategoryRepository.findById(product.getProductsubcategory().getProductsubcategoryid()).isEmpty()) {
			throw new RuntimeException("subcategory no exists - saveProduct()");
		} else {
			ps = subcategoryRepository.findById(product.getProductsubcategory().getProductsubcategoryid()).get();
			if (categoryRepository.findById(product.getProductsubcategory().getProductsubcategoryid()).isEmpty()) {
				throw new RuntimeException("category no exists - saveProduct()");
			}
		}

		if (unitmeasureRepository.findById(product.getUnitmeasure1().getUnitmeasurecode()).isEmpty()) {
			throw new RuntimeException("unitmeasure1 no exists - saveProduct()");
		} else {
			un1 = unitmeasureRepository.findById(product.getUnitmeasure1().getUnitmeasurecode()).get();
		}

		if (unitmeasureRepository.findById(product.getUnitmeasure2().getUnitmeasurecode()).isEmpty()) {
			throw new RuntimeException("unitmeasure2 no exists - saveProduct()");
		} else {
			un2 = unitmeasureRepository.findById(product.getUnitmeasure2().getUnitmeasurecode()).get();
		}

		// productRepository.deleteById(id.longValue());

		p.setName(product.getName());
		p.setProductnumber(product.getProductnumber());
		p.setProductsubcategory(ps);
		p.setSellenddate(p.getSellenddate());
		p.setSellstartdate(p.getSellstartdate());
		p.setSize(product.getSize());
		p.setUnitmeasure1(un1);
		p.setUnitmeasure2(un2);
		p.setWeight(product.getWeight());

		productRepository.update(p);
		return p;
	}

	@Override
	public Product findById(Integer id) {

		return productRepository.findById(id);
	}

	@Override
	public Iterable<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public void delete(Product product) {
		productRepository.delete(product);

	}

	@Override
	public ArrayList<Workorder> getWorkorders(Integer id) {

		return (ArrayList<Workorder>) productRepository.findById(id).getWorkorders();
	}
}
