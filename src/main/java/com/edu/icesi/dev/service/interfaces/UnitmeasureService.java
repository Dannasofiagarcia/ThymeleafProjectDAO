package com.edu.icesi.dev.service.interfaces;

import java.util.Date;
import java.util.Optional;

import com.edu.icesi.dev.model.Unitmeasure;

public interface UnitmeasureService {

	public Unitmeasure saveUnitmeasure(String name, Date modifiedDate);

	public Unitmeasure editProduct(Integer id, String name, Date modifiedDate);

	public Optional<Unitmeasure> findById(Integer id);

	public Iterable<Unitmeasure> findAll();

	public void delete(Unitmeasure unitmeasure);

}
