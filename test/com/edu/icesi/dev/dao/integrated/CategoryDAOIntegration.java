package com.edu.icesi.dev.dao.integrated;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.transaction.Transactional;

import com.edu.icesi.dev.Taller1DannaGarciaApplication;
import com.edu.icesi.dev.dao.interfaces.CategoryDAO;
import com.edu.icesi.dev.model.Productcategory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(classes = Taller1DannaGarciaApplication.class)
@ExtendWith(SpringExtension.class)
@Rollback(false)
public class CategoryDAOIntegration {

	private CategoryDAO categoryDAO;

	private Productcategory category1;
	private Productcategory category2;

	@Autowired
	public CategoryDAOIntegration(CategoryDAO categoryDAO) {
		super();
		this.categoryDAO = categoryDAO;
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

		ArrayList<Productcategory> products = (ArrayList<Productcategory>) categoryDAO.findAll();
		if (products.size() != 0) {
			for (int i = 0; i < products.size(); i++) {
				categoryDAO.delete(products.get(i));
			}
		}

		// *********************
		// Categorias pruebas
		// *********************

		category1 = new Productcategory();
		category1.setName("Category1");
		category2 = new Productcategory();
		category2.setName("Category1");

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindById() {

		setUp();
		categoryDAO.save(category1);
		categoryDAO.save(category2);

		Productcategory test1 = categoryDAO.findById(category1.getProductcategoryid());
		Productcategory test2 = categoryDAO.findById(category2.getProductcategoryid());

		Assertions.assertNotNull(test1);
		Assertions.assertNotNull(test2);

		Assertions.assertEquals(test1.getProductcategoryid(), category1.getProductcategoryid());
		Assertions.assertEquals(test2.getProductcategoryid(), category2.getProductcategoryid());

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindAll() {

		setUp();
		categoryDAO.save(category1);
		categoryDAO.save(category2);

		ArrayList<Productcategory> test1 = (ArrayList<Productcategory>) categoryDAO.findAll();

		Assertions.assertEquals(test1.size(), 2);
	}

	@Test
	@Transactional()
	@Tag("Delete")
	void testDelete() {

		setUp();
		categoryDAO.save(category1);
		categoryDAO.save(category2);
		categoryDAO.delete(category1);
		categoryDAO.delete(category2);

		ArrayList<Productcategory> test1 = (ArrayList<Productcategory>) categoryDAO.findAll();

		Assertions.assertEquals(test1.size(), 0);

	}

}
