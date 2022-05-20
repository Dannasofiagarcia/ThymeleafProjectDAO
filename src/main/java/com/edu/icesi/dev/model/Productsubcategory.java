package com.edu.icesi.dev.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.edu.icesi.dev.validated.ValidatedAdd;
import com.edu.icesi.dev.validated.ValidatedEdit;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the productsubcategory database table.
 *
 */
@Entity
@NamedQuery(name = "Productsubcategory.findAll", query = "SELECT p FROM Productsubcategory p")
public class Productsubcategory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PRODUCTSUBCATEGORY_PRODUCTSUBCATEGORYID_GENERATOR", allocationSize = 1, sequenceName = "PRODUCTSUBCATEGORY_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTSUBCATEGORY_PRODUCTSUBCATEGORYID_GENERATOR")
	private Integer productsubcategoryid;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Modified date cannot be blank")
	private Date modifieddate;

	@NotBlank(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Name cannot be blank")
	@Size(groups = { ValidatedAdd.class, ValidatedEdit.class }, min = 5, message = "{Name must be longer}")
	private String name;

	private Integer rowguid;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "productsubcategory")
	private List<Product> products;

	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Product category cannot be empty")
	@ManyToOne
	@JoinColumn(name = "productcategoryid")
	private Productcategory productcategory;

	public Productsubcategory() {
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setProductsubcategory(this);

		return product;
	}

	public Date getModifieddate() {
		return this.modifieddate;
	}

	public String getName() {
		return this.name;
	}

	public Productcategory getProductcategory() {
		return this.productcategory;
	}

	public List<Product> getProducts() {
		return this.products;
	}

	public Integer getProductsubcategoryid() {
		return this.productsubcategoryid;
	}

	public Integer getRowguid() {
		return this.rowguid;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setProductsubcategory(null);

		return product;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductcategory(Productcategory productcategory) {
		this.productcategory = productcategory;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void setProductsubcategoryid(Integer productsubcategoryid) {
		this.productsubcategoryid = productsubcategoryid;
	}

	public void setRowguid(Integer rowguid) {
		this.rowguid = rowguid;
	}

}