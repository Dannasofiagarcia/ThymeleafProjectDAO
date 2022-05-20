package com.edu.icesi.dev.service.interfaces;

import java.sql.Timestamp;
import java.util.Optional;

import com.edu.icesi.dev.model.Scrapreason;

public interface ScrapreasonService {

	Scrapreason saveScrapreason(String name, Timestamp modifiedDate);

	Scrapreason editScrapreason(Integer id, String name, Timestamp modifiedDate);

	Optional<Scrapreason> findById(Integer id);

	Iterable<Scrapreason> findAll();

	void delete(Scrapreason scrapreason);

}
