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
import com.edu.icesi.dev.dao.interfaces.WorkorderDAO;
import com.edu.icesi.dev.model.Scrapreason;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.service.interfaces.ScrapreasonService;

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
public class WorkorderDAOIntegration {
	private WorkorderDAO workorderDAO;
	private ScrapreasonService scrapreasonService;

	private Workorder workorder1;
	private Workorder workorder2;

	private Scrapreason scrapreason1;
	private Scrapreason scrapreason2;

	@Autowired
	public WorkorderDAOIntegration(WorkorderDAO workorderDAO, ScrapreasonService scrapreasonService) {
		super();
		this.workorderDAO = workorderDAO;
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

		ArrayList<Workorder> products = (ArrayList<Workorder>) workorderDAO.findAll();
		if (products.size() != 0) {
			for (int i = 0; i < products.size(); i++) {
				workorderDAO.delete(products.get(i));
			}
		}

		// ************************
		// Órdenes de trabajo
		// ************************
		workorder1 = new Workorder();
		workorder1.setOrderqty(10);

		workorder2 = new Workorder();
		workorder2.setOrderqty(20);

		workorderDAO.save(workorder1);
		workorderDAO.save(workorder2);

		// ************************
		// Scrapreasons
		// ************************

		scrapreason1 = scrapreasonService.saveScrapreason("Scrapreason1", new Timestamp(0));
		scrapreasonService.findById(scrapreason1.getScrapreasonid()).get().addWorkorder(workorder1);

		scrapreason2 = scrapreasonService.saveScrapreason("Scrapreason2", new Timestamp(10000));
		scrapreasonService.findById(scrapreason2.getScrapreasonid()).get().addWorkorder(workorder2);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByScrapreasonId() {

		setUp();

		List<Workorder> test1 = workorderDAO.findByScrapreasonId(scrapreason1.getScrapreasonid());
		List<Workorder> test2 = workorderDAO.findByScrapreasonId(scrapreason2.getScrapreasonid());

		Assertions.assertEquals(test1.size(), 1);
		Assertions.assertEquals(test2.size(), 1);
		Assertions.assertTrue(test1.get(0).getScrapreason().getName().equals("Scrapreason1"));
		Assertions.assertTrue(test2.get(0).getScrapreason().getName().equals("Scrapreason2"));

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindByOrderqty() {

		setUp();
		List<Workorder> test1 = workorderDAO.findByOrderqty(10);
		List<Workorder> test2 = workorderDAO.findByOrderqty(20);

		Assertions.assertEquals(test1.size(), 1);
		Assertions.assertEquals(test2.size(), 1);

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindById() {

		setUp();
		Workorder test1 = workorderDAO.findById(workorder1.getWorkorderid());
		Workorder test2 = workorderDAO.findById(workorder2.getWorkorderid());

		Assertions.assertEquals(test1.getWorkorderid(), workorder1.getWorkorderid());
		Assertions.assertEquals(test2.getWorkorderid(), workorder2.getWorkorderid());

	}

	@Test
	@Transactional()
	@Tag("Query")
	void testFindAll() {
		setUp();
		List<Workorder> test1 = workorderDAO.findAll();

		Assertions.assertEquals(test1.size(), 2);
	}

	@Test
	@Transactional()
	@Tag("Delete")
	void testDelete() {

		setUp();
		workorderDAO.delete(workorder1);
		workorderDAO.delete(workorder2);

		List<Workorder> products = workorderDAO.findAll();

		Assertions.assertEquals(products.size(), 0);

	}

}
