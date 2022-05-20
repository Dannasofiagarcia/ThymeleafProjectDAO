package com.edu.icesi.dev.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

import com.edu.icesi.dev.model.Scrapreason;
import com.edu.icesi.dev.model.Workorder;
import com.edu.icesi.dev.repositories.ScrapreasonRepository;
import com.edu.icesi.dev.service.interfaces.ScrapreasonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrapreasonServiceImp implements ScrapreasonService {

	ScrapreasonRepository scrapreasonRepository;

	@Autowired
	public ScrapreasonServiceImp(ScrapreasonRepository scrapreasonRepository) {
		super();
		this.scrapreasonRepository = scrapreasonRepository;
	}

	@Override
	public Scrapreason saveScrapreason(String name, Timestamp modifiedDate) {
		if (name.equals("")) {
			throw new RuntimeException("no valid name - saveProduct()");
		}
		if (modifiedDate == null) {
			throw new RuntimeException("modifiedDate no exists - saveProduct()");
		}

		Scrapreason sr = new Scrapreason();
		sr.setName(name);
		sr.setModifieddate(modifiedDate);
		sr.setWorkorders(new ArrayList<Workorder>());
		scrapreasonRepository.save(sr);

		return sr;
	}

	@Override
	public Scrapreason editScrapreason(Integer id, String name, Timestamp modifiedDate) {
		if (name.equals("")) {
			throw new RuntimeException("no valid name - saveProduct()");
		}
		if (modifiedDate == null) {
			throw new RuntimeException("modifiedDate no exists - saveProduct()");
		}

		Scrapreason sr = new Scrapreason();
		sr.setName(name);
		sr.setModifieddate(modifiedDate);
		scrapreasonRepository.save(sr);

		return sr;
	}

	@Override
	public Optional<Scrapreason> findById(Integer id) {
		return scrapreasonRepository.findById(id);
	}

	@Override
	public Iterable<Scrapreason> findAll() {
		return scrapreasonRepository.findAll();
	}

	/**
	 * public Iterable<Product> findAllPatients() { return
	 * userRepository.findByType(UserType.patient); }
	 * 
	 * public Iterable<UserApp> findAllDoctors() { return
	 * userRepository.findByType(UserType.doctor); }
	 **/

	@Override
	public void delete(Scrapreason scrapreason) {
		scrapreasonRepository.delete(scrapreason);

	}

}
