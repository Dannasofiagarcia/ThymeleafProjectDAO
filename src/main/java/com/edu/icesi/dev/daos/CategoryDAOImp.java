package com.edu.icesi.dev.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.edu.icesi.dev.dao.interfaces.CategoryDAO;
import com.edu.icesi.dev.model.Productcategory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class CategoryDAOImp implements CategoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Productcategory save(Productcategory productcategory) {
		entityManager.persist(productcategory);
		return productcategory;
	}

	@Override
	public Productcategory update(Productcategory productcategory) {
		entityManager.merge(productcategory);
		return productcategory;
	}

	@Transactional
	@Override
	public void delete(Productcategory productcategory) {
		entityManager.remove(productcategory);

	}

	@Override
	public Productcategory findById(Integer productcategoryId) {
		String jpql = "Select p from Productcategory p where p.productcategoryid=:productcategoryId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("productcategoryId", productcategoryId);
		Productcategory p = null;
		try {
			p = (Productcategory) query.getSingleResult();
		} catch (NoResultException e) {

		}
		return p;
	}

	@Override
	public List<Productcategory> findAll() {
		String query = "Select p from Productcategory p";
		return entityManager.createQuery(query).getResultList();
	}

}
