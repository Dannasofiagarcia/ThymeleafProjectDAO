package com.edu.icesi.dev.service;

import com.edu.icesi.dev.dao.interfaces.WorkorderDAO;
import com.edu.icesi.dev.model.Product;
import com.edu.icesi.dev.model.Scrapreason;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.repositories.ProductRepository;
import com.edu.icesi.dev.repositories.ScrapreasonRepository;
import com.edu.icesi.dev.service.interfaces.WorkorderService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkorderServiceImp implements WorkorderService {

	private ProductRepository productRepository;
	private ScrapreasonRepository screapreasonRepository;
	private WorkorderDAO workorderRepository;

	@Autowired
	public WorkorderServiceImp(ProductRepository productRepository, ScrapreasonRepository screapreasonRepository,
			WorkorderDAO workorderRepository) {
		super();
		this.productRepository = productRepository;
		this.screapreasonRepository = screapreasonRepository;
		this.workorderRepository = workorderRepository;
	}

	@Override
	public Workorder saveWorkorder(Workorder workorder) {
		Workorder wo;
		Product p;
		Scrapreason sr;
		if (workorder.getOrderqty() < 0) {
			throw new RuntimeException("No valid orderqty - saveWorkorder()");
		}
		if (workorder.getScrappedqty() < 0) {
			throw new RuntimeException("No valid scrappedqty - saveWorkorder()");
		}

		if (workorder.getStartdate().compareTo(workorder.getEnddate()) == 1) {
			throw new RuntimeException("No valid orderqty - saveWorkorder()");
		}

		if (screapreasonRepository.findById(workorder.getScrapreason().getScrapreasonid()).isEmpty()) {
			throw new RuntimeException("No valid screapreason - saveWorkorder()");
		} else {
			sr = screapreasonRepository.findById(workorder.getScrapreason().getScrapreasonid()).get();
		}

		if (productRepository.findById(workorder.getProduct().getProductid()).isEmpty()) {
			throw new RuntimeException("product no exists - saveWorkorder()");
		} else {
			p = productRepository.findById(workorder.getProduct().getProductid()).get();

			wo = new Workorder();
			wo.setDuedate(workorder.getDuedate());
			wo.setEnddate(workorder.getEnddate());
			wo.setModifieddate(workorder.getModifieddate());
			wo.setOrderqty(workorder.getOrderqty());
			wo.setProduct(p);
			wo.setScrappedqty(workorder.getScrappedqty());
			wo.setScrapreason(sr);
			wo.setStartdate(workorder.getStartdate());
			p.addWorkorder(wo);
			workorderRepository.save(wo);
		}

		return wo;
	}

	@Override
	public Workorder editWorkorder(Integer id, Workorder workorder) {
		Workorder wo;
		Product p;
		Scrapreason sr;

		if (workorder.getOrderqty() < 0) {
			throw new RuntimeException("No valid orderqty - saveWorkorder()");
		}
		if (workorder.getScrappedqty() < 0) {
			throw new RuntimeException("No valid scrappedqty - saveWorkorder()");
		}

		if (workorder.getStartdate().compareTo(workorder.getEnddate()) == 1) {
			throw new RuntimeException("No valid dates - saveWorkorder()");
		}

		if (screapreasonRepository.findById(workorder.getScrapreason().getScrapreasonid()).isEmpty()) {
			throw new RuntimeException("No valid screapreason - saveWorkorder()");
		} else {
			sr = screapreasonRepository.findById(workorder.getScrapreason().getScrapreasonid()).get();
		}

		if (productRepository.findById(workorder.getProduct().getProductid()).isEmpty()) {
			throw new RuntimeException("product no exists - saveWorkorder()");
		} else {
			p = productRepository.findById(workorder.getProduct().getProductid()).get();
		}

		if (workorderRepository.findById(id) == null) {
			throw new RuntimeException("workorder no exists - saveWorkorder()");
		} else {
			wo = workorderRepository.findById(id);
		}

		// p.removeWorkorder(wo);
		wo.setDuedate(workorder.getDuedate());
		wo.setEnddate(workorder.getEnddate());
		wo.setModifieddate(workorder.getModifieddate());
		wo.setOrderqty(workorder.getOrderqty());
		wo.setProduct(p);
		wo.setScrappedqty(workorder.getScrappedqty());
		wo.setScrapreason(sr);
		wo.setStartdate(workorder.getStartdate());
		p.addWorkorder(wo);
		// Debería también guardar los productos después de agregarle a producto la wo?
		workorderRepository.update(wo);
		return wo;
	}

	@Override
	public Workorder findById(Integer id) {
		return workorderRepository.findById(id);
	}

	@Override
	public Iterable<Workorder> findAll() {
		return workorderRepository.findAll();
	}

	@Override
	public void delete(Workorder workorder) {
		workorderRepository.delete(workorder);

	}

}
