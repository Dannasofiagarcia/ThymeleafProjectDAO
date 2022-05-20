package com.edu.icesi.dev.service.interfaces;

import com.edu.icesi.dev.model.Workorder;

public interface WorkorderService {

	public Workorder saveWorkorder(Workorder workorder);

	public Workorder editWorkorder(Integer id, Workorder workorder);

	public Workorder findById(Integer id);

	public Iterable<Workorder> findAll();

	public void delete(Workorder workorder);

}
