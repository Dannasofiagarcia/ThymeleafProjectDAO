package com.edu.icesi.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edu.icesi.dev.model.Scrapreason;

@Repository
public interface ScrapreasonRepository extends CrudRepository<Scrapreason, Integer> {
}
