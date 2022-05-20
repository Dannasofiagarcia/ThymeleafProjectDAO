package com.edu.icesi.dev.daos;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.edu.icesi.dev.dao.interfaces.SubcategoryDAO;
import com.edu.icesi.dev.model.Productsubcategory;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

@Repository
@Scope("singleton")
public class SubcategoryDAOImp implements SubcategoryDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Productsubcategory save(Productsubcategory productsubcategory) {
		entityManager.persist(productsubcategory);
		return productsubcategory;
	}

	@Override
	public Productsubcategory update(Productsubcategory productsubcategory) {
		entityManager.merge(productsubcategory);
		return productsubcategory;
	}

	@Transactional
	@Override
	public void delete(Productsubcategory productsubcategory) {
		entityManager.remove(productsubcategory);

	}

	@Override
	public Productsubcategory findById(Integer productsubcategoryId) {
		String jpql = "Select ps from Productsubcategory ps WHERE ps.productsubcategoryid=:productsubcategoryId";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("productsubcategoryId", productsubcategoryId);

		Productsubcategory ps = null;
		try {
			ps = (Productsubcategory) query.getSingleResult();
		} catch (NoResultException e) {

		}
		return ps;
	}

	@Override
	public List<Productsubcategory> findAll() {
		String query = "Select ps from Productsubcategory ps";
		return entityManager.createQuery(query).getResultList();
	}

	@Override
	public List<Productsubcategory> findByCategoryId(Integer productcategoryid) {
		String jpql = "SELECT ps FROM Productsubcategory ps WHERE ps.productcategory.productcategoryid =:productcategoryid";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("productcategoryid", productcategoryid);

		return query.getResultList();
	}

	@Override
	public List<Productsubcategory> findByName(String name) {
		String jpql = "SELECT ps FROM Productsubcategory ps WHERE ps.name =:name";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("name", name);

		return query.getResultList();

	}

	/**
	 * La(s) subcategoría (s) con sus datos y cantidad de productos (que iniciaron a
	 * venderse en rango de fechas dadas), ordenados por el nombre. Recibe como
	 * parámetro una categoría de productos y retorna todas las subcategorías que
	 * cumplen con tener al menos una un producto en las fechas dadas.
	 * 
	 * 
	 */
	@Override
	public List<Productsubcategory> findByCategoryAndDate(Integer categoryid, Date sellstartdate, Date sellenddate) {
		String jpql = "SELECT ps FROM Productsubcategory ps WHERE ps.productcategory.productcategoryid=:categoryid AND (Select count(p) from Product p WHERE p.productsubcategory.productsubcategoryid = ps.productsubcategoryid AND p.sellstartdate >= sellstartdate AND p.sellenddate <= sellenddate) > 1 ORDER BY ps.name ASC";

		// String jpql = "SELECT ps FROM Productsubcategory ps WHERE
		// ps.productcategory=:category";

		// String jpql = "SELECT ps FROM Productsubcategory"
		// + "WHERE ps.productcategory.productcategoryid = category.productcategoryid "
		// + "AND p.productsubcategory.productsubcategoryid = ps.productsubcategory "
		// + "AND p.sellstartdate >= sellstartdate AND p.sellenddate <= sellenddate "
		// + "AND size(ps.products) >= 2";
		Query query = entityManager.createQuery(jpql);
		query.setParameter("categoryid", categoryid);
		// query.setParameter("sellstartdate", sellstartdate);
		// query.setParameter("sellenddate", sellenddate);
		return query.getResultList();
	}

}
