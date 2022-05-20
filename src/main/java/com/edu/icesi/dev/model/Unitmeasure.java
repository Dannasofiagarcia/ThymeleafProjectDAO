package com.edu.icesi.dev.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.edu.icesi.dev.validated.ValidatedAdd;
import com.edu.icesi.dev.validated.ValidatedEdit;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the unitmeasure database table.
 *
 */
@Entity
@NamedQuery(name = "Unitmeasure.findAll", query = "SELECT u FROM Unitmeasure u")
public class Unitmeasure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "UNITMEASURE_UNITMEASURECODE_GENERATOR", allocationSize = 1, sequenceName = "UNITMEASURE_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UNITMEASURE_UNITMEASURECODE_GENERATOR")
	private Integer unitmeasurecode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Modified date cannot be blank")
	private Date modifieddate;

	@NotBlank(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Name cannot be blank")
	private String name;

	/**
	 * // bi-directional many-to-one association to Billofmaterial
	 * 
	 * @OneToMany(mappedBy = "unitmeasure") private List<Billofmaterial>
	 *                     billofmaterials;
	 **/

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "unitmeasure1")
	private List<Product> products1;

	// bi-directional many-to-one association to Product
	@OneToMany(mappedBy = "unitmeasure2")
	private List<Product> products2;

	public Unitmeasure() {
	}

	public Product addProducts1(Product products1) {
		getProducts1().add(products1);
		products1.setUnitmeasure1(this);

		return products1;
	}

	public Product addProducts2(Product products2) {
		getProducts2().add(products2);
		products2.setUnitmeasure2(this);

		return products2;
	}

	public Date getModifieddate() {
		return this.modifieddate;
	}

	public String getName() {
		return this.name;
	}

	public List<Product> getProducts1() {
		return this.products1;
	}

	public List<Product> getProducts2() {
		return this.products2;
	}

	public Integer getUnitmeasurecode() {
		return this.unitmeasurecode;
	}

	public Product removeProducts1(Product products1) {
		getProducts1().remove(products1);
		products1.setUnitmeasure1(null);

		return products1;
	}

	public Product removeProducts2(Product products2) {
		getProducts2().remove(products2);
		products2.setUnitmeasure2(null);

		return products2;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProducts1(List<Product> products1) {
		this.products1 = products1;
	}

	public void setProducts2(List<Product> products2) {
		this.products2 = products2;
	}

	public void setUnitmeasurecode(Integer unitmeasurecode) {
		this.unitmeasurecode = unitmeasurecode;
	}

}