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

import com.edu.icesi.dev.dao.interfaces.WorkorderDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Productcategory;
import com.edu.icesi.dev.model.Productsubcategory;
import com.edu.icesi.dev.model.Scrapreason;
import com.edu.icesi.dev.model.Unitmeasure;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.repositories.ProductCategoryRepository;
import com.edu.icesi.dev.repositories.ProductRepository;
import com.edu.icesi.dev.repositories.ProductSubcategoryRepository;
import com.edu.icesi.dev.repositories.ScrapreasonRepository;
import com.edu.icesi.dev.repositories.UnitmeasureRepository;
import com.edu.icesi.dev.service.WorkorderServiceImp;

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
public class WorkorderDAOTest {

	@InjectMocks
	private static WorkorderServiceImp workorderService;

	@Mock
	private static ProductRepository productRepository;

	@Mock
	private static WorkorderDAO workorderDAO;

	@Mock
	private static ProductSubcategoryRepository productsubcategoryRepository;

	@Mock
	private static ProductCategoryRepository productcategoryRepository;

	@Mock
	private static UnitmeasureRepository unitmeasureRepository;

	@Mock
	private static ScrapreasonRepository scrapreasonRepository;

	private static Productsubcategory productsubcategory;
	private static Productcategory productcategory;
	private static Unitmeasure unitmeasure;
	private static Unitmeasure unitmeasure2;
	private static Product productTest;
	private static Workorder workorder;
	private static Scrapreason scrapreason;

	private static Integer idProductsubcategory;
	private static Integer idProductcategory;
	private static Integer idUnitmeasure;
	private static Integer idUnitmeasure2;
	private static Integer idProductTest;
	private static Integer idWorkorder;
	private static Integer idScrapreason;

	private static Date sellstart;
	private static Date sellend;

	@BeforeAll
	public static void setUpProduct() throws ParseException {

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
		sellstart = df.parse("23/09/2020");
		sellend = df.parse("23/10/2020");

		productTest.setSellstartdate(sellstart);
		productTest.setSellenddate(sellend);
		productTest.setUnitmeasure1(unitmeasure);
		productTest.setUnitmeasure2(unitmeasure2);
		idProductTest = productTest.getProductid();

		workorder = new Workorder();
		workorder.setOrderqty(10);
		workorder.setScrappedqty(20);
		workorder.setStartdate(sellstart);
		workorder.setEnddate(sellend);
		workorder.setProduct(productTest);
		workorder.setWorkorderid(100);
		productTest.addWorkorder(workorder);
		workorder.setScrapreason(scrapreason);
		idWorkorder = workorder.getWorkorderid();

		scrapreason = new Scrapreason();
		scrapreason.setName("Name");
		scrapreason.setScrapreasonid(1002);
		scrapreason.setWorkorders(new ArrayList<Workorder>());
		scrapreason.addWorkorder(workorder);
		idScrapreason = scrapreason.getScrapreasonid();

	}

	@BeforeEach
	public void init() {
		Optional<Productsubcategory> optionalProductsubcategory = Optional.of(productsubcategory);
		Mockito.when(productsubcategoryRepository.findById(idProductsubcategory))
				.thenReturn(optionalProductsubcategory);

		Mockito.when(workorderDAO.findById(idWorkorder)).thenReturn(workorder);

		Optional<Productcategory> optionalProductcategory = Optional.of(productcategory);
		Mockito.when(productcategoryRepository.findById(idProductcategory)).thenReturn(optionalProductcategory);

		Optional<Product> optionalProduct = Optional.of(productTest);
		Mockito.when(productRepository.findById(idProductTest)).thenReturn(optionalProduct);

		Optional<Unitmeasure> optionalUnitmeasure = Optional.of(unitmeasure);
		Mockito.when(unitmeasureRepository.findById(idUnitmeasure)).thenReturn(optionalUnitmeasure);

		Optional<Unitmeasure> optionalUnitmeasure2 = Optional.of(unitmeasure2);
		Mockito.when(unitmeasureRepository.findById(idUnitmeasure2)).thenReturn(optionalUnitmeasure2);

		Optional<Scrapreason> optionalScrapreason = Optional.of(scrapreason);
		Mockito.when(scrapreasonRepository.findById(idScrapreason)).thenReturn(optionalScrapreason);
	}

	@Nested
	@DisplayName("Test of save workorder")
	class SaveProduct {
		@Test
		public void saveWorkorderTest() {
			Workorder workorderTest = new Workorder();
			workorderTest.setOrderqty(10);
			workorderTest.setScrappedqty(20);
			workorderTest.setStartdate(sellstart);
			workorderTest.setEnddate(sellend);
			workorderTest.setProduct(productTest);
			workorderTest.setWorkorderid(100);
			workorderTest.setScrapreason(scrapreason);
			// productTest.addWorkorder(workorder);
			// scrapreason.addWorkorder(workorderTest);

			Assertions.assertDoesNotThrow(() -> workorderService.saveWorkorder(workorderTest));
			Mockito.verify(workorderDAO).save(workorderTest);
			Mockito.verifyNoMoreInteractions(workorderDAO);

		}

		@Test
		public void saveWorkorderWithoutScrapreason() {
			Workorder workorderTest = new Workorder();
			workorderTest.setOrderqty(10);
			// workorderTest.setProduct(productTest);
			workorderTest.setScrappedqty(20);
			workorderTest.setWorkorderid(100);
			workorderTest.setStartdate(sellstart);
			workorderTest.setEnddate(sellend);
			productTest.addWorkorder(workorder);
			// scrapreason.addWorkorder(workorderTest);

			when(workorderDAO.save(workorderTest)).thenReturn(workorderTest);
			assertThrows(RuntimeException.class, () -> workorderService.saveWorkorder(workorderTest));
			verify(workorderDAO, times(0)).save(workorderTest);

		}

		@Test
		public void saveWorkorderWithWrongOrderqty() {
			Workorder workorderTest = new Workorder();
			workorderTest.setOrderqty(-1);
			workorderTest.setScrappedqty(20);
			workorderTest.setStartdate(sellstart);
			workorderTest.setEnddate(sellend);
			// workorderTest.setProduct(productTest);
			productTest.addWorkorder(workorder);
			scrapreason.addWorkorder(workorderTest);

			when(workorderDAO.save(workorderTest)).thenReturn(workorderTest);
			assertThrows(RuntimeException.class, () -> workorderService.saveWorkorder(workorderTest));
			verify(workorderDAO, times(0)).save(workorderTest);

		}

		@Test
		public void saveWorkorderWithWrongScrapqty() {
			Workorder workorderTest = new Workorder();
			workorderTest.setOrderqty(10);
			workorderTest.setScrappedqty(-20);
			workorderTest.setStartdate(sellstart);
			workorderTest.setEnddate(sellend);
			// workorderTest.setProduct(productTest);
			productTest.addWorkorder(workorder);
			scrapreason.addWorkorder(workorderTest);

			when(workorderDAO.save(workorderTest)).thenReturn(workorderTest);
			assertThrows(RuntimeException.class, () -> workorderService.saveWorkorder(workorderTest));
			verify(workorderDAO, times(0)).save(workorderTest);

		}

		@Test
		public void saveWorkorderWithoutProduct() {
			Workorder workorderTest = new Workorder();
			workorderTest.setOrderqty(10);
			workorderTest.setScrappedqty(20);
			workorderTest.setStartdate(sellstart);
			workorderTest.setEnddate(sellend);
			// workorderTest.setProduct(productTest);
			// productTest.addWorkorder(workorder);
			scrapreason.addWorkorder(workorderTest);

			when(workorderDAO.save(workorderTest)).thenReturn(workorderTest);
			assertThrows(RuntimeException.class, () -> workorderService.saveWorkorder(workorderTest));
			verify(workorderDAO, times(0)).save(workorderTest);

		}

		@Test
		public void saveWorkorderWithoutCorrectDates() {
			Workorder workorderTest = new Workorder();
			workorderTest.setOrderqty(10);
			workorderTest.setScrappedqty(-20);
			workorderTest.setStartdate(sellend);
			workorderTest.setEnddate(sellstart);
			// workorderTest.setProduct(productTest);
			productTest.addWorkorder(workorder);
			scrapreason.addWorkorder(workorderTest);

			when(workorderDAO.save(workorderTest)).thenReturn(workorderTest);
			assertThrows(RuntimeException.class, () -> workorderService.saveWorkorder(workorderTest));
			verify(workorderDAO, times(0)).save(workorderTest);
		}
	}

	@Nested
	@DisplayName("Test of edit workorder")
	class EditProduct {
		@Test
		public void editProductTest() {
			Workorder workorderEdit = workorderService.findById(idWorkorder);
			verify(workorderDAO).findById(idWorkorder);

			workorderEdit.setOrderqty(200);
			workorderEdit.setScrappedqty(120);
			workorderEdit.setStartdate(sellstart);
			workorderEdit.setEnddate(sellend);
			// workorderEdit.setProduct(productTest);
			productTest.addWorkorder(workorderEdit);
			scrapreason.addWorkorder(workorderEdit);

			when(workorderDAO.update(workorderEdit)).thenReturn(workorderEdit);
			assertDoesNotThrow(() -> workorderService.editWorkorder(idWorkorder, workorderEdit));
			Mockito.verify(workorderDAO).update(workorderEdit);
		}

		@Test
		public void editWorkorderWithoutScrapreason() {
			Workorder workorderEdit = workorderService.findById(idWorkorder);
			verify(workorderDAO).findById(idWorkorder);

			workorderEdit.setOrderqty(200);
			workorderEdit.setScrappedqty(120);
			workorderEdit.setStartdate(sellstart);
			workorderEdit.setEnddate(sellend);
			// workorderEdit.setProduct(productTest);
			workorderEdit.setScrapreason(null);
			productTest.addWorkorder(workorderEdit);
			// scrapreason.addWorkorder(workorderEdit);

			when(workorderDAO.update(workorderEdit)).thenReturn(workorderEdit);
			assertThrows(RuntimeException.class, () -> workorderService.editWorkorder(idWorkorder, workorderEdit));
			verify(workorderDAO, times(0)).update(workorderEdit);

		}

		@Test
		public void editWorkorderWithWrongOrderqty() {
			Workorder workorderEdit = workorderService.findById(idWorkorder);
			verify(workorderDAO).findById(idWorkorder);

			workorderEdit.setOrderqty(-200);
			workorderEdit.setScrappedqty(120);
			workorderEdit.setStartdate(sellstart);
			workorderEdit.setEnddate(sellend);
			// workorderEdit.setProduct(productTest);
			productTest.addWorkorder(workorderEdit);
			scrapreason.addWorkorder(workorderEdit);

			when(workorderDAO.update(workorderEdit)).thenReturn(workorderEdit);
			assertThrows(RuntimeException.class, () -> workorderService.editWorkorder(idWorkorder, workorderEdit));
			verify(workorderDAO, times(0)).update(workorderEdit);

		}

		@Test
		public void editWorkorderWithWrongScrapqty() {
			Workorder workorderEdit = workorderService.findById(idWorkorder);
			verify(workorderDAO).findById(idWorkorder);

			workorderEdit.setOrderqty(200);
			workorderEdit.setScrappedqty(-120);
			workorderEdit.setStartdate(sellstart);
			workorderEdit.setEnddate(sellend);
			// workorderEdit.setProduct(productTest);
			productTest.addWorkorder(workorderEdit);
			scrapreason.addWorkorder(workorderEdit);

			when(workorderDAO.update(workorderEdit)).thenReturn(workorderEdit);
			assertThrows(RuntimeException.class, () -> workorderService.editWorkorder(idWorkorder, workorderEdit));
			verify(workorderDAO, times(0)).update(workorderEdit);

		}

		@Test
		public void editWorkorderWithoutProduct() {
			Workorder workorderEdit = workorderService.findById(idWorkorder);
			verify(workorderDAO).findById(idWorkorder);

			workorderEdit.setOrderqty(200);
			workorderEdit.setScrappedqty(120);
			workorderEdit.setStartdate(sellstart);
			workorderEdit.setEnddate(sellend);
			workorderEdit.setProduct(null);
			// productTest.addWorkorder(workorderEdit);
			scrapreason.addWorkorder(workorderEdit);

			when(workorderDAO.update(workorderEdit)).thenReturn(workorderEdit);
			assertThrows(RuntimeException.class, () -> workorderService.editWorkorder(idWorkorder, workorderEdit));
			verify(workorderDAO, times(0)).update(workorderEdit);

		}
	}

}
