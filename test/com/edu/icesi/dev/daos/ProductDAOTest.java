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

import com.edu.icesi.dev.dao.interfaces.ProductDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.model.Unitmeasure;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.repositories.ProductCategoryRepository;
import com.edu.icesi.dev.repositories.ProductSubcategoryRepository;
import com.edu.icesi.dev.repositories.UnitmeasureRepository;
import com.edu.icesi.dev.service.ProductServiceImp;
import com.edu.icesi.dev.service.interfaces.ProductService;

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
public class ProductDAOTest {

	@InjectMocks
	private static ProductService productService;

	@Mock
	private static ProductDAO productDAO;

	@Mock
	private static ProductSubcategoryRepository productsubcategoryRepository;

	@Mock
	private static ProductCategoryRepository productcategoryRepository;

	@Mock
	private static UnitmeasureRepository unitmeasureRepository;

	private static Productsubcategory productsubcategory;
	private static Productcategory productcategory;
	private static Unitmeasure unitmeasure;
	private static Unitmeasure unitmeasure2;
	private static Product productTest;

	private static Integer idProductsubcategory;
	private static Integer idProductcategory;
	private static Integer idUnitmeasure;
	private static Integer idUnitmeasure2;
	private static Integer idProductTest;

	@BeforeAll
	public static void setUpProduct() throws ParseException {
		productService = new ProductServiceImp(productsubcategoryRepository, productcategoryRepository, productDAO,
				unitmeasureRepository);
		productsubcategory = new Productsubcategory();
		productcategory = new Productcategory();
		unitmeasure = new Unitmeasure();
		unitmeasure.setUnitmeasurecode(1);
		idUnitmeasure = unitmeasure.getUnitmeasurecode();
		unitmeasure2 = new Unitmeasure();
		unitmeasure2.setUnitmeasurecode(2);
		idUnitmeasure2 = unitmeasure2.getUnitmeasurecode();

		productTest = new Product();
		productsubcategory.setProductcategory(productcategory);
		productTest.setProductsubcategory(productsubcategory);
		productTest.setProductnumber("Product number");
		productTest.setWorkorders(new ArrayList<Workorder>());

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date sellstart = df.parse("23/09/2020");
		Date sellend = df.parse("23/10/2020");

		productTest.setSellstartdate(sellstart);
		productTest.setSellenddate(sellend);
		productTest.setUnitmeasure1(unitmeasure);
		productTest.setUnitmeasure2(unitmeasure2);
		idProductTest = productTest.getProductid();

	}

	@BeforeEach
	public void init() {
		Optional<Productsubcategory> optionalProductsubcategory = Optional.of(productsubcategory);
		Mockito.when(productsubcategoryRepository.findById(idProductsubcategory))
				.thenReturn(optionalProductsubcategory);
		Mockito.when(productDAO.findById(idProductTest)).thenReturn(productTest);
		Optional<Productcategory> optionalProductcategory = Optional.of(productcategory);
		Mockito.when(productcategoryRepository.findById(idProductcategory)).thenReturn(optionalProductcategory);
		Optional<Unitmeasure> optionalUnitmeasure = Optional.of(unitmeasure);
		Mockito.when(unitmeasureRepository.findById(idUnitmeasure)).thenReturn(optionalUnitmeasure);
		Optional<Unitmeasure> optionalUnitmeasure2 = Optional.of(unitmeasure2);
		Mockito.when(unitmeasureRepository.findById(idUnitmeasure2)).thenReturn(optionalUnitmeasure2);
	}

	@Nested
	@DisplayName("Test of save product, correct case")
	class SaveProduct {
		@Test
		public void saveProductTest() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				product.setProductnumber("productnumber");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setUnitmeasure1(unitmeasure);
				product.setUnitmeasure2(unitmeasure2);
				product.setWorkorders(new ArrayList<Workorder>());

				product.setSize(10);
				product.setWeight(20);

				Assertions.assertDoesNotThrow(() -> productService.saveProduct(product));
				Mockito.verify(productDAO).save(product);
				Mockito.verifyNoMoreInteractions(productDAO);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		@Test
		public void saveProductWithoutProductsubcategory() {

			try {
				Product product = new Product();
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setProductnumber("productnumber");

				product.setUnitmeasure1(new Unitmeasure());
				product.setUnitmeasure2(new Unitmeasure());
				product.setWorkorders(new ArrayList<Workorder>());

				product.setSize(10);
				product.setWeight(20);

				when(productDAO.save(product)).thenReturn(product);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		@Test
		public void saveProductWithoutUnitmeasure1() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setProductnumber("productnumber");

				product.setUnitmeasure1(new Unitmeasure());

				product.setSize(10);
				product.setWeight(20);

				when(productDAO.save(product)).thenReturn(product);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		@Test
		public void saveProductWithoutUnitmeasure2() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setProductnumber("productnumber");

				product.setUnitmeasure2(new Unitmeasure());

				product.setSize(10);
				product.setWeight(20);

				when(productDAO.save(product)).thenReturn(product);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);

			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		@Test
		public void saveProductNull() {
			Product product = null;
			when(productDAO.save(product)).thenReturn(product);
			assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
			verify(productDAO, times(0)).save(product);

		}

		@Test
		public void saveProductWithoutProductnumber() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setUnitmeasure1(new Unitmeasure());
				product.setUnitmeasure2(new Unitmeasure());

				product.setSize(10);
				product.setWeight(20);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		@Test
		public void saveProductWithoutCorrectSize() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setUnitmeasure1(new Unitmeasure());
				product.setUnitmeasure2(new Unitmeasure());

				product.setSize(0);
				product.setWeight(20);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		@Test
		public void saveProductWithoutCorrectWeight() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setSellenddate(sellend);
				product.setUnitmeasure1(new Unitmeasure());
				product.setUnitmeasure2(new Unitmeasure());

				product.setSize(10);
				product.setWeight(0);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		@Test
		public void saveProductWithoutSellstartdate() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				product.setProductnumber("productnumber");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellenddate(sellend);
				product.setUnitmeasure1(new Unitmeasure());
				product.setUnitmeasure2(new Unitmeasure());

				product.setSize(10);
				product.setWeight(20);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		@Test
		public void saveProductWithoutSellenddate() {
			try {
				Product product = new Product();
				product.setProductsubcategory(productsubcategory);
				product.setProductnumber("productnumber");
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				Date sellstart = df.parse("23/09/2020");
				Date sellend = df.parse("23/10/2020");

				product.setSellstartdate(sellstart);
				product.setUnitmeasure1(new Unitmeasure());
				product.setUnitmeasure2(new Unitmeasure());

				product.setSize(10);
				product.setWeight(20);
				assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
				verify(productDAO, times(0)).save(product);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}

	@Test
	public void saveProductWithoutCorrectDates() {
		try {
			Product product = new Product();
			product.setProductsubcategory(productsubcategory);
			product.setProductnumber("productnumber");
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			Date sellstart = df.parse("23/09/2020");
			Date sellend = df.parse("23/08/2020");

			product.setSellstartdate(sellstart);
			product.setSellenddate(sellend);
			product.setUnitmeasure1(new Unitmeasure());
			product.setUnitmeasure2(new Unitmeasure());

			product.setSize(10);
			product.setWeight(20);

			assertThrows(RuntimeException.class, () -> productService.saveProduct(product));
			verify(productDAO, times(0)).save(product);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Nested
	@DisplayName("Test of edit product")
	class EditProduct {
		@Test
		public void editProductTest() {
			Product productToEdit = productService.findById(idProductTest);
			verify(productDAO).findById(idProductTest);

			productToEdit.setProductnumber("Update product number");
			productToEdit.setName("Update product");
			productToEdit.setSize(20);
			productToEdit.setUnitmeasure1(unitmeasure);
			productToEdit.setUnitmeasure2(unitmeasure2);

			when(productDAO.update(productToEdit)).thenReturn(productToEdit);
			assertDoesNotThrow(() -> productService.editProduct(idProductTest, productToEdit));
			verify(productDAO).update(productToEdit);
		}

		@Test
		public void editProductWithoutProductsubcategory() {
			Product productToEdit = productService.findById(idProductTest);
			verify(productDAO).findById(idProductTest);

			Productsubcategory ps2 = new Productsubcategory();
			ps2.setProductsubcategoryid(22);
			ps2.setProductcategory(productcategory);
			productToEdit.setProductsubcategory(ps2);

			when(productDAO.update(productToEdit)).thenReturn(productToEdit);
			assertThrows(RuntimeException.class, () -> productService.editProduct(idProductTest, productToEdit));
			verify(productDAO, times(0)).update(productToEdit);

		}

		@Test
		public void editProductWithoutUnitmeasure() {
			Product productToEdit = productService.findById(idProductTest);
			verify(productDAO).findById(idProductTest);

			productToEdit.setUnitmeasure1(null);
			Unitmeasure um2 = new Unitmeasure();
			um2.setUnitmeasurecode(22);

			when(productDAO.update(productToEdit)).thenReturn(productToEdit);
			assertThrows(RuntimeException.class, () -> productService.editProduct(idProductTest, productToEdit));
			verify(productDAO, times(0)).update(productToEdit);

		}

	}
}