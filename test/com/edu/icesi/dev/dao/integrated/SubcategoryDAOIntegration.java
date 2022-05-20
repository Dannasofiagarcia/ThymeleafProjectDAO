package com.edu.icesi.dev.dao.integrated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.edu.icesi.dev.Taller1DannaGarciaApplication;
import com.edu.icesi.dev.dao.interfaces.SubcategoryDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.model.Unitmeasure;
import com.edu.icesi.dev.service.interfaces.ProductCategoryService;
import com.edu.icesi.dev.service.interfaces.ProductService;
import com.edu.icesi.dev.service.interfaces.UnitmeasureService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Taller1DannaGarciaApplication.class)
//@ExtendWith(SpringExtension.class)
//@Rollback(false)
public class SubcategoryDAOIntegration {

	private SubcategoryDAO subcategoryDAO;
	private ProductCategoryService categoryService;
	private ProductService productService;
	private UnitmeasureService unitmeasureService;

	private Productsubcategory subcategory1;
	private Productsubcategory subcategory2;
	private Productsubcategory subcategory3;

	private Productsubcategory temp;

	private Product product1;
	private Product product2;
	private Product product3;

	private Productcategory category1;
	private Productcategory category2;

	private Unitmeasure unitmeasure1;
	private Unitmeasure unitmeasure2;

	private Date sellstart;
	private Date sellend;

	@Autowired
	public SubcategoryDAOIntegration(SubcategoryDAO subcategoryDAO, ProductCategoryService categoryService,
			ProductService productService, UnitmeasureService unitmeasureService) {
		super();
		this.subcategoryDAO = subcategoryDAO;
		this.categoryService = categoryService;
		this.productService = productService;
		this.unitmeasureService = unitmeasureService;
	}

	public void setUp() {

		DateFormat df = null;

		try {
			df = new SimpleDateFormat("dd/MM/yyyy");
			sellstart = df.parse("23/09/2020");
			sellend = df.parse("23/10/2020");
		} catch (ParseException e) {

			e.printStackTrace();
		}

		ArrayList<Productsubcategory> subcategories = (ArrayList<Productsubcategory>) subcategoryDAO.findAll();
		if (subcategories.size() != 0) {
			for (int i = 0; i < subcategories.size(); i++) {
				subcategoryDAO.delete(subcategories.get(i));
			}
		}

		// ****************
		// Categorias
		// ****************

		category1 = new Productcategory();
		category1.setModifieddate(sellstart);
		category1.setName("Category1");
		category1.setProductsubcategories(new ArrayList<Productsubcategory>());
		category1 = categoryService.saveProductCategoryV2(category1);

		category2 = new Productcategory();
		category2.setModifieddate(sellstart);
		category2.setName("Category2");
		category2.setProductsubcategories(new ArrayList<Productsubcategory>());
		category2 = categoryService.saveProductCategoryV2(category2);

		// ****************
		// Subcategorias
		// ****************

		subcategory1 = new Productsubcategory();
		subcategory1.setModifieddate(sellstart);
		subcategory1.setName("Subcategory1");
		subcategory1.setProducts(new ArrayList<Product>());

		subcategory2 = new Productsubcategory();
		subcategory2.setModifieddate(sellstart);
		subcategory2.setName("Subcategory2");
		subcategory2.setProducts(new ArrayList<Product>());

		subcategory3 = new Productsubcategory();
		subcategory3.setModifieddate(sellstart);
		subcategory3.setName("Subcategory3");
		subcategory3.setProducts(new ArrayList<Product>());

		// ****************
		// Unidades de medida
		// ****************

		unitmeasure1 = unitmeasureService.saveUnitmeasure("Unitmeasure1", sellstart);

		unitmeasure2 = unitmeasureService.saveUnitmeasure("Unitmeasure2", sellstart);

		// ****************
		// Products
		// ****************

		product1 = new Product();
		product1.setName("Product1");
		product1.setProductnumber("Productnumber1");
		product1.setSellenddate(sellend);
		product1.setSellstartdate(sellstart);
		product1.setSize(10);
		product1.setUnitmeasure1(unitmeasure1);
		product1.setUnitmeasure2(unitmeasure2);
		product1.setWeight(20);

		product2 = new Product();
		product2.setName("Product2");
		product2.setProductnumber("Productnumber2");
		product2.setSellenddate(sellend);
		product2.setSellstartdate(sellstart);
		product2.setProductsubcategory(subcategory2);
		product2.setSize(10);
		product2.setUnitmeasure1(unitmeasure1);
		product2.setUnitmeasure2(unitmeasure2);
		product2.setWeight(20);

		product3 = new Product();
		product3.setName("Product3");
		product3.setProductnumber("Productnumber3");
		product3.setSellenddate(sellend);
		product3.setSellstartdate(sellstart);
		product3.setProductsubcategory(subcategory2);
		product3.setSize(10);
		product3.setUnitmeasure1(unitmeasure1);
		product3.setUnitmeasure2(unitmeasure2);
		product3.setWeight(20);

		subcategory1 = subcategoryDAO.save(subcategory1);
		subcategory2 = subcategoryDAO.save(subcategory2);
		subcategory3 = subcategoryDAO.save(subcategory3);

		category1.addProductsubcategory(subcategory1);
		category2.addProductsubcategory(subcategory2);
		category2.addProductsubcategory(subcategory3);

		product1.setProductsubcategory(subcategory1);
		product2.setProductsubcategory(subcategory2);
		product3.setProductsubcategory(subcategory2);

		product1 = productService.saveProduct(product1);
		product2 = productService.saveProduct(product2);
		product3 = productService.saveProduct(product3);

		subcategory1.addProduct(product1);
		subcategory2.addProduct(product2);
		subcategory3.addProduct(product2);

		// temp = subcategoryDAO.update(subcategory1);
		// temp = subcategoryDAO.update(subcategory2);
		// temp = subcategoryDAO.update(subcategory3);
		category1 = categoryService.saveProductCategoryV2(category1);
		category2 = categoryService.saveProductCategoryV2(category2);
		subcategory1 = subcategoryDAO.save(subcategory1);
		subcategory2 = subcategoryDAO.save(subcategory2);
		subcategory3 = subcategoryDAO.save(subcategory3);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByCategoryId() {

		setUp();

		List<Productsubcategory> test1 = subcategoryDAO.findByCategoryId(category1.getProductcategoryid());
		List<Productsubcategory> test2 = subcategoryDAO.findByCategoryId(category2.getProductcategoryid());

		Assertions.assertEquals(test1.size(), 1);
		Assertions.assertEquals(test2.size(), 2);
		Assertions.assertTrue(test1.get(0).getName().equals("Subcategory1"));
		Assertions.assertTrue(test2.get(0).getName().equals("Subcategory2"));
		Assertions.assertTrue(test2.get(1).getName().equals("Subcategory3"));

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByName() {
		setUp();

		List<Productsubcategory> test1 = subcategoryDAO.findByName("Subcategory1");
		List<Productsubcategory> test2 = subcategoryDAO.findByName("Subcategory2");
		List<Productsubcategory> test3 = subcategoryDAO.findByName("Subcategory3");

		Assertions.assertTrue(test1.get(0).getName().equals("Subcategory1"));
		Assertions.assertTrue(test2.get(0).getName().equals("Subcategory2"));
		Assertions.assertTrue(test3.get(0).getName().equals("Subcategory3"));

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByCategoryAndDate() {

		setUp();

		List<Productsubcategory> test1 = subcategoryDAO.findByCategoryAndDate(category1.getProductcategoryid(),
				sellstart, sellend);
		List<Productsubcategory> test2 = subcategoryDAO.findByCategoryAndDate(category2.getProductcategoryid(),
				sellstart, sellend);

		Assertions.assertEquals(test1.size(), 0);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindById() {

		setUp();

		Productsubcategory test1 = subcategoryDAO.findById(subcategory1.getProductsubcategoryid());
		Productsubcategory test2 = subcategoryDAO.findById(subcategory2.getProductsubcategoryid());
		Productsubcategory test3 = subcategoryDAO.findById(subcategory3.getProductsubcategoryid());

		Assertions.assertNotNull(test1);
		Assertions.assertNotNull(test2);
		Assertions.assertNotNull(test3);
		Assertions.assertEquals(test1.getProductsubcategoryid(), subcategory1.getProductsubcategoryid());
		Assertions.assertEquals(test2.getProductsubcategoryid(), subcategory2.getProductsubcategoryid());
		Assertions.assertEquals(test3.getProductsubcategoryid(), subcategory3.getProductsubcategoryid());

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindAll() {

		setUp();

		List<Productsubcategory> test1 = subcategoryDAO.findAll();

		Assertions.assertEquals(test1.size(), 3);
	}

	@Test
	@Transactional()
	@Tag("Delete")
	void testDelete() {

		setUp();

		subcategoryDAO.delete(subcategory1);
		subcategoryDAO.delete(subcategory2);
		subcategoryDAO.delete(subcategory3);

		List<Productsubcategory> test = subcategoryDAO.findAll();

		Assertions.assertEquals(test.size(), 0);

	}
}
