package com.edu.icesi.dev.daos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.PersistenceContext;

import com.edu.icesi.dev.dao.interfaces.CategoryDAO;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.service.ProductCategoryServiceImp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = { PersistenceContext.class })
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryDAOTest {

	@InjectMocks
	private static ProductCategoryServiceImp categoryService;

	@Mock
	private static CategoryDAO categoryDAO;

	private static Productcategory category1;

	private static Integer category1Id;

	private static Date sellstart;

	@BeforeAll
	public static void setUpCategory() throws ParseException {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		sellstart = df.parse("23/09/2020");

		category1 = new Productcategory();
		category1.setModifieddate(sellstart);
		category1.setName("Category1");
		category1Id = category1.getProductcategoryid();

	}

	@BeforeEach
	public void init() {

		Mockito.when(categoryDAO.findById(category1Id)).thenReturn(category1);

	}

	@Nested
	@DisplayName("Test of save product")
	class SaveProduct {
		@Test
		public void saveProductTest() {

			Productcategory category = new Productcategory();
			category.setModifieddate(sellstart);
			category.setName("Category1");

			Assertions.assertDoesNotThrow(() -> categoryService.saveProductCategoryV2(category));
			Mockito.verify(categoryDAO).save(category);
			Mockito.verifyNoMoreInteractions(categoryDAO);

		}

		@Test
		public void saveCategoryWithWrongName() {

			Productcategory category = new Productcategory();
			category.setModifieddate(sellstart);
			category.setName("C");

			assertThrows(RuntimeException.class, () -> categoryService.saveProductCategoryV2(category));
			verify(categoryDAO, times(0)).save(category1);

		}
	}

	@Nested
	@DisplayName("Test of edit category")
	class EditProduct {
		@Test
		public void editCategoryTest() {

			Productcategory categoryToEdit = categoryService.findById(category1Id);
			verify(categoryDAO).findById(category1Id);

			categoryToEdit.setName("Categoryyy");

			when(categoryDAO.update(categoryToEdit)).thenReturn(categoryToEdit);
			assertDoesNotThrow(() -> categoryService.editProductCategoryV2(category1Id, categoryToEdit));
			verify(categoryDAO).update(categoryToEdit);

		}

		@Test
		public void editCategoryWithWrongName() {
			Productcategory categoryToEdit = categoryService.findById(category1Id);
			verify(categoryDAO).findById(category1Id);

			categoryToEdit.setName("C");

			when(categoryDAO.update(categoryToEdit)).thenReturn(categoryToEdit);
			assertThrows(RuntimeException.class,
					() -> categoryService.editProductCategoryV2(category1Id, categoryToEdit));
			verify(categoryDAO, times(0)).update(categoryToEdit);
		}

	}

}
