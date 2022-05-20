package com.edu.icesi.dev.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.edu.icesi.dev.dao.interfaces.ProductDAO;
import com.edu.icesi.dev.model.Product;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class ProductDAOImp implements ProductDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Product save(Product product) {
		entityManager.persist(product);
		return product;
	}

	@Override
	public Product update(Product product) {
		entityManager.merge(product);
		return product;
	}

	@Transactional()
	@Override
	public void delete(Product product) {
		entityManager.remove(product);

	}

	@Override
	public Product findById(Integer productId) {
		String jpql = "Select p from Product p where p.productid=:productId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("productId", productId);
		Product p = null;
		try {
			p = (Product) query.getSingleResult();
		} catch (NoResultException e) {

		}
		return p;
	}

	@Override
	public List<Product> findAll() {
		String query = "Select p from Product p";
		return entityManager.createQuery(query).getResultList();
	}

	// PREGUNTAR POR ESTA CONSULTA

	/**
	 * Mostrar el listado productos para los productos que tienen al menos dos
	 * órdenes de trabajo
	 */
	@Override
	public List<Product> findByWorkorderSize() {
		String jpql = "SELECT p FROM Product p  WHERE (Select count(w) from Workorder w WHERE p.productid = w.product.productid) > 1";
		Query query = entityManager.createQuery(jpql);
		return query.getResultList();
	}

	@Override
	public List<Product> findBySubcategoryId(Integer subcategoryId) {
		String jpql = "SELECT p FROM Product p WHERE p.productsubcategory.productsubcategoryid = :subcategoryId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("subcategoryId", subcategoryId);

		return query.getResultList();
	}

	@Override
	public List<Product> findByProductNumber(String productNumber) {
		String jpql = "SELECT p FROM Product p WHERE p.productnumber = :productNumber";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("productNumber", productNumber);
		return query.getResultList();
	}

	// PREGUNTAR POR ESTA CONSULTA

	@Override
	public List<Product> findByUnitMeasure1Code(Integer unitmeasurecode) {
		String jpql = "SELECT p FROM Product p WHERE p.unitmeasure1.unitmeasurecode = :unitmeasurecode";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("unitmeasurecode", unitmeasurecode);
		return query.getResultList();
	}

}
