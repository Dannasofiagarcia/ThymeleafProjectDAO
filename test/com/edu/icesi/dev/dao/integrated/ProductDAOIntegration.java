package com.edu.icesi.dev.dao.integrated;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.edu.icesi.dev.Taller1DannaGarciaApplication;
import com.edu.icesi.dev.dao.interfaces.ProductDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.model.Scrapreason;
import com.edu.icesi.dev.model.Unitmeasure;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.service.ProductCategoryServiceImp;
import com.edu.icesi.dev.service.interfaces.ProductSubcategoryService;
import com.edu.icesi.dev.service.interfaces.ScrapreasonService;
import com.edu.icesi.dev.service.interfaces.UnitmeasureService;
import com.edu.icesi.dev.service.interfaces.WorkorderService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Taller1DannaGarciaApplication.class)
//@ExtendWith(SpringExtension.class)
//@Rollback(false)
public class ProductDAOIntegration {

	private ProductDAO productDAO;
	private WorkorderService workorderService;
	private ProductSubcategoryService subcategoryService;
	private ProductCategoryServiceImp categoryService;
	private UnitmeasureService unitmeasureService;
	private ScrapreasonService scrapreasonService;

	private Productcategory category1;
	private Productcategory category2;

	private Product product1;
	private Product product2;
	private Product product3;

	private Product test;
	private Product test2;
	private Product test3;

	private Workorder workorder1;
	private Workorder workorder2;
	private Workorder workorder3;

	private Productsubcategory subcategory1;
	private Productsubcategory subcategory2;

	private Unitmeasure unitmeasure1;
	private Unitmeasure unitmeasure2;

	private Scrapreason scrapreason;

	@Autowired
	public ProductDAOIntegration(ProductDAO productDAO, WorkorderService workorderService,
			ProductSubcategoryService subcategoryService, ProductCategoryServiceImp categoryService,
			UnitmeasureService unitmeasureService, ScrapreasonService scrapreasonService) {
		this.productDAO = productDAO;
		this.workorderService = workorderService;
		this.subcategoryService = subcategoryService;
		this.categoryService = categoryService;
		this.unitmeasureService = unitmeasureService;
		this.scrapreasonService = scrapreasonService;
	}

	public void setUp() {

		DateFormat df = null;
		Date sellstart = null;
		Date sellend = null;
		try {
			df = new SimpleDateFormat("dd/MM/yyyy");
			sellstart = df.parse("23/09/2020");
			sellend = df.parse("23/10/2020");
		} catch (ParseException e) {

			e.printStackTrace();
		}

		ArrayList<Product> products = (ArrayList<Product>) productDAO.findAll();
		if (products.size() != 0) {
			for (int i = 0; i < products.size(); i++) {
				productDAO.delete(products.get(i));
			}
		}

		// *********************
		// Subcategorias pruebas
		// *********************

		category1 = categoryService.saveProductCategory(new Date(), "Category1");
		category2 = categoryService.saveProductCategory(new Date(), "Category2");

		// *********************
		// Subcategorias pruebas
		// *********************

		subcategory1 = subcategoryService.saveProductSubcategory(new Date(), "Subcategory1",
				category1.getProductcategoryid());
		subcategory2 = subcategoryService.saveProductSubcategory(new Date(), "Subcategory2",
				category2.getProductcategoryid());

		// *********************
		// Unidades de medida pruebas
		// *********************

		unitmeasure1 = unitmeasureService.saveUnitmeasure("Unitmeasure1", new Date());
		unitmeasure2 = unitmeasureService.saveUnitmeasure("Unitmeasure2", new Date());

		// *********************
		// Product con 1 workorders
		// *********************
		product1 = new Product();
		product1.setName("Product1");
		product1.setProductsubcategory(subcategory1);
		product1.setProductnumber("product number1");
		product1.setSellstartdate(sellstart);
		product1.setSellenddate(sellend);
		product1.setUnitmeasure1(unitmeasure1);
		product1.setUnitmeasure2(unitmeasure2);
		product1.setWorkorders(new ArrayList<Workorder>());
		product1.setSize(10);
		product1.setWeight(20);

		// *********************
		// Product con 2 workorder
		// *********************
		product2 = new Product();
		product2.setName("Product2");
		product2.setProductsubcategory(subcategory2);
		product2.setProductnumber("product number2");
		product2.setSellstartdate(sellstart);
		product2.setSellenddate(sellend);
		product2.setUnitmeasure1(unitmeasure1);
		product2.setUnitmeasure2(unitmeasure2);
		product2.setWorkorders(new ArrayList<Workorder>());
		product2.setSize(20);
		product2.setWeight(30);

		// *********************
		// Product sin workorders
		// *********************
		product3 = new Product();
		product3.setName("Product3");
		product3.setProductsubcategory(subcategory2);
		product3.setProductnumber("product number3");
		product3.setSellstartdate(sellstart);
		product3.setSellenddate(sellend);
		product3.setUnitmeasure1(unitmeasure1);
		product3.setUnitmeasure2(unitmeasure2);
		product3.setWorkorders(new ArrayList<Workorder>());
		product3.setSize(20);
		product3.setWeight(30);

		// *********************
		// Scrapreason
		// *********************

		scrapreason = scrapreasonService.saveScrapreason("Scrapreason", new Timestamp(1000));

		productDAO.save(product1);
		productDAO.save(product2);
		productDAO.save(product3);

		// *********************
		// Workorders
		// *********************
		workorder1 = new Workorder();
		workorder1.setOrderqty(10);
		workorder1.setProduct(product1);
		workorder1.setScrappedqty(20);
		workorder1.setScrapreason(scrapreason);
		workorder1.setEnddate(sellend);
		workorder1.setStartdate(sellstart);
		workorderService.saveWorkorder(workorder1);

		workorder2 = new Workorder();
		workorder2.setOrderqty(10);
		workorder2.setProduct(product2);
		workorder2.setScrappedqty(20);
		workorder1.setEnddate(sellend);
		workorder1.setStartdate(sellstart);
		workorder2.setScrapreason(scrapreason);
		workorderService.saveWorkorder(workorder1);

		workorder3 = new Workorder();
		workorder3.setOrderqty(10);
		workorder3.setProduct(product3);
		workorder3.setScrappedqty(20);
		workorder1.setEnddate(sellend);
		workorder1.setStartdate(sellstart);
		workorder3.setScrapreason(scrapreason);
		workorderService.saveWorkorder(workorder1);

		product1.addWorkorder(workorder1);
		product2.addWorkorder(workorder2);
		product2.addWorkorder(workorder3);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByWorkorderSize() {

		setUp();

		List<Product> test = productDAO.findByWorkorderSize();

		Assertions.assertEquals(test.size(), 1);
		// Assertions.assertTrue(test.get(0).getName().equals("Product2"));

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindBySubcategoryId() {

		setUp();

		List<Product> testSubcategory1 = productDAO.findBySubcategoryId(subcategory1.getProductsubcategoryid());

		List<Product> testSubcategory2 = productDAO.findBySubcategoryId(subcategory2.getProductsubcategoryid());

		Assertions.assertEquals(testSubcategory1.size(), 1);
		Assertions.assertTrue(testSubcategory1.get(0).getProductsubcategory().getName().equals("Subcategory1"));
		Assertions.assertEquals(testSubcategory2.size(), 2);
		Assertions.assertTrue(testSubcategory2.get(0).getProductsubcategory().getName().equals("Subcategory2"));
		Assertions.assertTrue(testSubcategory2.get(1).getProductsubcategory().getName().equals("Subcategory2"));

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByProductNumber() {

		setUp();

		List<Product> test1 = productDAO.findByProductNumber("product number1");

		List<Product> test2 = productDAO.findByProductNumber("product number2");

		List<Product> test3 = productDAO.findByProductNumber("product number3");

		Assertions.assertEquals(test1.size(), 1);
		Assertions.assertTrue(test2.size() == 1);
		Assertions.assertTrue(test3.size() == 1);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByUnitMeasureCode() {

		setUp();
		productDAO.save(product1);
		productDAO.save(product2);
		productDAO.save(product3);
		List<Product> test1 = productDAO.findByUnitMeasure1Code(unitmeasure1.getUnitmeasurecode());
		List<Product> test2 = productDAO.findByUnitMeasure1Code(unitmeasure2.getUnitmeasurecode());

		Assertions.assertEquals(test1.size(), 3);
		Assertions.assertEquals(test2.size(), 0);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindById() {

		setUp();
		productDAO.save(product1);
		productDAO.save(product2);
		productDAO.save(product3);
		Product test1 = productDAO.findById(product1.getProductid());
		Product test2 = productDAO.findById(product2.getProductid());
		Product test3 = productDAO.findById(product3.getProductid());

		Assertions.assertNotNull(test1);
		Assertions.assertNotNull(test2);
		Assertions.assertNotNull(test3);
		Assertions.assertEquals(test1.getProductid(), product1.getProductid());
		Assertions.assertEquals(test2.getProductid(), product2.getProductid());
		Assertions.assertEquals(test3.getProductid(), product3.getProductid());

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindAll() {

		setUp();
		productDAO.save(product1);
		productDAO.save(product2);
		productDAO.save(product3);
		List<Product> test1 = productDAO.findAll();

		Assertions.assertEquals(test1.size(), 3);
	}

	@Test
	@Transactional()
	@Tag("Delete")
	void testDelete() {

		setUp();

		productDAO.delete(product1);
		productDAO.delete(product2);
		productDAO.delete(product3);

		List<Product> products = productDAO.findAll();

		Assertions.assertEquals(products.size(), 0);

	}

}
