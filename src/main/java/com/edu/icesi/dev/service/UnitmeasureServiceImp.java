package com.edu.icesi.dev.service;

import java.util.Date;
import java.util.Optional;

import com.edu.icesi.dev.model.Unitmeasure;
import com.edu.icesi.dev.repositories.UnitmeasureRepository;
import com.edu.icesi.dev.service.interfaces.UnitmeasureService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitmeasureServiceImp implements UnitmeasureService {

	UnitmeasureRepository unitmeasureRepository;

	@Autowired
	public UnitmeasureServiceImp(UnitmeasureRepository unitmeasureRepository) {
		super();
		this.unitmeasureRepository = unitmeasureRepository;
	}

	@Override
	public Unitmeasure saveUnitmeasure(String name, Date modifiedDate) {
		if (name.equals("")) {
			throw new RuntimeException("no valid name - saveProduct()");
		}
		if (modifiedDate == null) {
			throw new RuntimeException("modifiedDate no exists - saveProduct()");
		}

		Unitmeasure um = new Unitmeasure();
		um.setName(name);
		um.setModifieddate(modifiedDate);
		unitmeasureRepository.save(um);

		return um;
	}

	@Override
	public Unitmeasure editProduct(Integer id, String name, Date modifiedDate) {
		if (name.equals("")) {
			throw new RuntimeException("no valid name - saveProduct()");
		}
		if (modifiedDate == null) {
			throw new RuntimeException("modifiedDate no exists - saveProduct()");
		}

		Unitmeasure um = unitmeasureRepository.findById(id).get();
		um.setName(name);
		um.setModifieddate(modifiedDate);
		unitmeasureRepository.save(um);

		return um;
	}

	/**
	 * public ArrayList<Workorder> getWorkorders(Integer id) {
	 * 
	 * return (ArrayList<Workorder>)
	 * productRepository.findById(id).get().getWorkorders(); }
	 * 
	 * public boolean confirmPassword(Long id, String currentPassword) { return
	 * userRepository.findById(id).get().getPassword().equals(currentPassword); }
	 **/

	@Override
	public Optional<Unitmeasure> findById(Integer id) {
		return unitmeasureRepository.findById(id);
	}

	@Override
	public Iterable<Unitmeasure> findAll() {
		return unitmeasureRepository.findAll();
	}

	/**
	 * public Iterable<Product> findAllPatients() { return
	 * userRepository.findByType(UserType.patient); }
	 * 
	 * public Iterable<UserApp> findAllDoctors() { return
	 * userRepository.findByType(UserType.doctor); }
	 **/

	@Override
	public void delete(Unitmeasure unitmeasure) {
		unitmeasureRepository.delete(unitmeasure);

	}

}
