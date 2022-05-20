package com.edu.icesi.dev.daos;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.persistence.PersistenceContext;

import com.edu.icesi.dev.dao.interfaces.SubcategoryDAO;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.repositories.ProductCategoryRepository;
import com.edu.icesi.dev.service.ProductSubcategoryServiceImp;

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
public class SubcategoryDAOTest {

	@InjectMocks
	private static ProductSubcategoryServiceImp subcategoryService;

	@Mock
	private static SubcategoryDAO subcategoryDAO;

	@Mock
	private static ProductCategoryRepository categoryRepository;

	private static Productsubcategory subcategory;

	private static Productcategory category;

	private static Integer subcategoryId;

	private static Date sellstart;

	@BeforeAll
	public static void setUpSubcategory() throws ParseException {

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		sellstart = df.parse("23/09/2020");

		category = new Productcategory();
		category.setName("Category");
		category.setProductsubcategories(new ArrayList<Productsubcategory>());

		subcategory = new Productsubcategory();
		subcategory.setName("Subcategory");
		subcategory.setProductcategory(category);
		subcategoryId = subcategory.getProductsubcategoryid();
	}

	@BeforeEach
	public void init() {

		Mockito.when(subcategoryDAO.findById(subcategoryId)).thenReturn(subcategory);
		Optional<Productcategory> optionalProductcategory = Optional.of(category);
		Mockito.when(categoryRepository.findById(category.getProductcategoryid())).thenReturn(optionalProductcategory);
	}

	@Nested
	@DisplayName("Test of save subcategory")
	class SaveProduct {
		@Test
		public void saveSubcategoryTest() {

			Productsubcategory subcategoryTest = new Productsubcategory();
			subcategoryTest.setName("Subcategory");
			subcategoryTest.setProductcategory(category);

			Assertions.assertDoesNotThrow(() -> subcategoryService.saveProductSubcategory(subcategoryTest));
			Mockito.verify(subcategoryDAO).save(subcategoryTest);
			Mockito.verifyNoMoreInteractions(subcategoryDAO);

		}

		@Test
		public void saveSubcategoryWithWrongName() {

			Productsubcategory subcategoryTest = new Productsubcategory();
			subcategoryTest.setName("S");
			subcategoryTest.setProductcategory(category);

			assertThrows(RuntimeException.class, () -> subcategoryService.saveProductSubcategory(subcategoryTest));
			verify(subcategoryDAO, times(0)).save(subcategoryTest);

		}

		@Test
		public void saveSubcategoryWithoutCategory() {

			Productsubcategory subcategoryTest = new Productsubcategory();
			subcategoryTest.setName("Subcategory");
			subcategoryTest.setProductcategory(null);

			assertThrows(RuntimeException.class, () -> subcategoryService.saveProductSubcategory(subcategoryTest));
			verify(subcategoryDAO, times(0)).save(subcategoryTest);

		}
	}

	@Nested
	@DisplayName("Test of edit category")
	class EditProduct {
		@Test
		public void editSubcategoryTest() {

			Productsubcategory subcategoryToEdit = subcategoryService.findById(subcategoryId);
			verify(subcategoryDAO).findById(subcategoryId);

			subcategoryToEdit.setName("SubcategoryEdited");
			subcategoryToEdit.setProductcategory(category);

			when(subcategoryDAO.update(subcategoryToEdit)).thenReturn(subcategoryToEdit);
			assertDoesNotThrow(() -> subcategoryService.editProductSubcategory(subcategoryId, subcategoryToEdit));
			verify(subcategoryDAO).update(subcategoryToEdit);

		}

		@Test
		public void editSubcategoryWithWrongName() {

			Productsubcategory subcategoryToEdit = subcategoryService.findById(subcategoryId);
			verify(subcategoryDAO).findById(subcategoryId);

			subcategoryToEdit.setName("S");
			subcategoryToEdit.setProductcategory(category);

			when(subcategoryDAO.update(subcategoryToEdit)).thenReturn(subcategoryToEdit);
			assertThrows(RuntimeException.class,
					() -> subcategoryService.editProductSubcategory(subcategoryId, subcategoryToEdit));
			verify(subcategoryDAO, times(0)).update(subcategoryToEdit);
		}

		@Test
		public void editSubcategoryWithWrongCategory() {

			Productsubcategory subcategoryToEdit = subcategoryService.findById(subcategoryId);
			verify(subcategoryDAO).findById(subcategoryId);

			subcategoryToEdit.setName("SubcategoryEdited");
			subcategoryToEdit.setProductcategory(null);

			when(subcategoryDAO.update(subcategoryToEdit)).thenReturn(subcategoryToEdit);
			assertThrows(RuntimeException.class,
					() -> subcategoryService.editProductSubcategory(subcategoryId, subcategoryToEdit));
			verify(subcategoryDAO, times(0)).update(subcategoryToEdit);
		}

	}
}
