package com.edu.icesi.dev.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.edu.icesi.dev.dao.interfaces.WorkorderDAO;
import com.edu.icesi.dev.model.Workorder;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class WorkorderDAOImp implements WorkorderDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Workorder save(Workorder workorder) {
		entityManager.persist(workorder);
		return workorder;
	}

	@Override
	public Workorder update(Workorder workorder) {
		entityManager.merge(workorder);
		return workorder;
	}

	@Transactional
	@Override
	public void delete(Workorder workorder) {
		entityManager.remove(workorder);

	}

	@Override
	public Workorder findById(Integer workorderId) {
		String jpql = "Select w from Workorder w WHERE w.workorderid = :workorderId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("workorderId", workorderId);
		Workorder p = null;
		try {
			p = (Workorder) query.getSingleResult();
		} catch (NoResultException e) {

		}
		return p;
	}

	@Override
	public List<Workorder> findAll() {
		String query = "Select w from Workorder w";
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<Workorder> findByScrapreasonId(Integer scrapreasonId) {
		String jpql = "SELECT w FROM Workorder w WHERE w.scrapreason.scrapreasonid = :scrapreasonId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("scrapreasonId", scrapreasonId);

		return query.getResultList();
	}

	@Override
	public List<Workorder> findByOrderqty(Integer orderqty) {
		String jpql = "SELECT w FROM Workorder w WHERE w.orderqty = :orderqty";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("orderqty", orderqty);

		return query.getResultList();
	}

}
