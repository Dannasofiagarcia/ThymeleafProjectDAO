package com.edu.icesi.dev.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.edu.icesi.dev.validated.ValidatedAdd;
import com.edu.icesi.dev.validated.ValidatedEdit;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * The persistent class for the workorder database table.
 * 
 */
@Entity
@NamedQuery(name = "Workorder.findAll", query = "SELECT w FROM Workorder w")
public class Workorder implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "WORKORDER_WORKORDERID_GENERATOR", allocationSize = 1, sequenceName = "WORKORDER_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKORDER_WORKORDERID_GENERATOR")
	private Integer workorderid;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Due date cannot be blank")
	private Date duedate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "End date cannot be blank")
	private Date enddate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Modified date cannot be blank")
	private Date modifieddate;

	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Order quantity cannot be blank")
	@PositiveOrZero(groups = { ValidatedAdd.class,
			ValidatedEdit.class }, message = "Order quantity must be positive or 0")
	private Integer orderqty;

	@PositiveOrZero(groups = { ValidatedAdd.class,
			ValidatedEdit.class }, message = "Order quantity must be positive or 0")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Scrrapped quantity cannot be blank")
	private Integer scrappedqty;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Start date cannot be blank")
	private Date startdate;

	// bi-directional many-to-one association to Product
	@ManyToOne
	@JoinColumn(name = "productid")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Product cannot be null")
	private Product product;

	// bi-directional many-to-one association to Scrapreason
	@ManyToOne
	@JoinColumn(name = "scrapreasonid")
	private Scrapreason scrapreason;

	/**
	 * // bi-directional many-to-one association to Workorderrouting
	 * 
	 * @OneToMany(mappedBy = "workorder") private List<Workorderrouting>
	 *                     workorderroutings;
	 **/

	public Workorder() {
	}

	public Integer getWorkorderid() {
		return this.workorderid;
	}

	public void setWorkorderid(Integer workorderid) {
		this.workorderid = workorderid;
	}

	public Date getDuedate() {
		return this.duedate;
	}

	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}

	public Date getEnddate() {
		return this.enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getModifieddate() {
		return this.modifieddate;
	}

	public void setModifieddate(Date modifieddate) {
		this.modifieddate = modifieddate;
	}

	public Integer getOrderqty() {
		return this.orderqty;
	}

	public void setOrderqty(Integer orderqty) {
		this.orderqty = orderqty;
	}

	public Integer getScrappedqty() {
		return this.scrappedqty;
	}

	public void setScrappedqty(Integer scrappedqty) {
		this.scrappedqty = scrappedqty;
	}

	public Date getStartdate() {
		return this.startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Scrapreason getScrapreason() {
		return this.scrapreason;
	}

	public void setScrapreason(Scrapreason scrapreason) {
		this.scrapreason = scrapreason;
	}

}