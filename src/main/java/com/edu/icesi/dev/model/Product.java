package com.edu.icesi.dev.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
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
import javax.validation.constraints.PositiveOrZero;

import com.edu.icesi.dev.validated.ValidatedAdd;
import com.edu.icesi.dev.validated.ValidatedEdit;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * The persistent class for the product database table.
 *
 */
@Entity
@Data
@NamedQuery(name = "Product.findAll", query = "SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "PRODUCT_PRODUCTID_GENERATOR", allocationSize = 1, sequenceName = "PRODUCT_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCT_PRODUCTID_GENERATOR")
	private Integer productid;

	@NotBlank(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Name may not be empty")
	private String name;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Sell end date cannot be blank")
	private Date sellenddate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Start end date cannot be blank")
	private Date sellstartdate;

	@PositiveOrZero(groups = { ValidatedAdd.class,
			ValidatedEdit.class }, message = "Size must be a positive number or 0")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Size may not be empty")
	private Integer size;

	@PositiveOrZero(groups = { ValidatedAdd.class,
			ValidatedEdit.class }, message = "Weight must be a positive number or 0")
	private double weight;

	@NotBlank(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Product number cannot be blank")
	private String productnumber;

	@Column(name = "class")
	private String class_;

	@ManyToOne
	@JoinColumn(name = "productsubcategoryid")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Productsubcategory cannot be blank")
	public Productsubcategory productsubcategory;

	@ManyToOne
	@JoinColumn(name = "sizeunitmeasurecode")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Unitmeasure cannot be blank")
	private Unitmeasure unitmeasure1;

	@ManyToOne
	@JoinColumn(name = "weightunitmeasurecode")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Unitmeasure cannot be blank")
	private Unitmeasure unitmeasure2;

	@OneToMany(mappedBy = "product")
	private List<Workorder> workorders;

	public Product() {
	}

	public Workorder addWorkorder(Workorder workorder) {
		getWorkorders().add(workorder);
		workorder.setProduct(this);

		return workorder;
	}

	public String getClass_() {
		return this.class_;
	}

	public String getName() {
		return this.name;
	}

	public Integer getProductid() {
		return this.productid;
	}

	public String getProductnumber() {
		return this.productnumber;
	}

	public Productsubcategory getProductsubcategory() {
		return this.productsubcategory;
	}

	public Date getSellenddate() {
		return this.sellenddate;
	}

	public Date getSellstartdate() {
		return this.sellstartdate;
	}

	public Integer getSize() {
		return this.size;
	}

	public Unitmeasure getUnitmeasure1() {
		return this.unitmeasure1;
	}

	public Unitmeasure getUnitmeasure2() {
		return this.unitmeasure2;
	}

	public double getWeight() {
		return this.weight;
	}

	public List<Workorder> getWorkorders() {
		return this.workorders;
	}

	public Workorder removeWorkorder(Workorder workorder) {
		getWorkorders().remove(workorder);
		workorder.setProduct(null);

		return workorder;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public void setProductnumber(String productnumber) {
		this.productnumber = productnumber;
	}

	public void setProductsubcategory(Productsubcategory productsubcategory) {
		this.productsubcategory = productsubcategory;
	}

	public void setSellenddate(Date sellenddate2) {
		this.sellenddate = sellenddate2;
	}

	public void setSellstartdate(Date sellstartdate) {
		this.sellstartdate = sellstartdate;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setUnitmeasure1(Unitmeasure unitmeasure1) {
		this.unitmeasure1 = unitmeasure1;
	}

	public void setUnitmeasure2(Unitmeasure unitmeasure2) {
		this.unitmeasure2 = unitmeasure2;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setWorkorders(List<Workorder> workorders) {
		this.workorders = workorders;
	}

}