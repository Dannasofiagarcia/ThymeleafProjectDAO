package com.edu.icesi.dev.dao.interfaces;

import java.util.List;

import com.edu.icesi.dev.model.Workorder;

public interface WorkorderDAO {

	Workorder save(Workorder workorder);

	Workorder update(Workorder workorder);

	void delete(Workorder workorder);

	Workorder findById(Integer workorderId);

	List<Workorder> findAll();

	List<Workorder> findByScrapreasonId(Integer scrapreasonId);

	List<Workorder> findByOrderqty(Integer orderqty);

}
