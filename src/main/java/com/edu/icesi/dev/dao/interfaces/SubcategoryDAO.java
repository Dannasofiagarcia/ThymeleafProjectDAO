package com.edu.icesi.dev.dao.interfaces;

import java.util.Date;
import java.util.List;

import com.edu.icesi.dev.model.Productsubcategory;

public interface SubcategoryDAO {

	Productsubcategory save(Productsubcategory productsubcategory);

	Productsubcategory update(Productsubcategory productsubcategory);

	void delete(Productsubcategory productsubcategory);

	Productsubcategory findById(Integer productsubcategoryId);

	List<Productsubcategory> findAll();

	List<Productsubcategory> findByCategoryId(Integer categoryId);

	List<Productsubcategory> findByName(String name);

	List<Productsubcategory> findByCategoryAndDate(Integer categoryid, Date sellstartdate, Date sellenddate);

}
