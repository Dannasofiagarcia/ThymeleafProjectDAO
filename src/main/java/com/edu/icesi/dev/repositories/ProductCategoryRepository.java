package com.edu.icesi.dev.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.edu.icesi.dev.model.Productcategory;

@Repository
public interface ProductCategoryRepository extends CrudRepository<Productcategory, Integer> {

}
