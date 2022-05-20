package com.edu.icesi.dev.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
 * The persistent class for the scrapreason database table.
 *
 */
@Entity
@NamedQuery(name = "Scrapreason.findAll", query = "SELECT s FROM Scrapreason s")
public class Scrapreason implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SCRAPREASON_SCRAPREASONID_GENERATOR", allocationSize = 1, sequenceName = "SCRAPREASON_SEQ")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCRAPREASON_SCRAPREASONID_GENERATOR")
	private Integer scrapreasonid;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Modified date cannot be blank")
	private Timestamp modifieddate;

	@NotBlank(groups = { ValidatedAdd.class, ValidatedEdit.class }, message = "Name cannot be blank")
	private String name;

	// bi-directional many-to-one association to Workorder
	@OneToMany(mappedBy = "scrapreason")
	private List<Workorder> workorders;

	public Scrapreason() {
	}

	public Workorder addWorkorder(Workorder workorder) {
		workorders.add(workorder);
		workorder.setScrapreason(this);

		return workorder;
	}

	public Timestamp getModifieddate() {
		return this.modifieddate;
	}

	public String getName() {
		return this.name;
	}

	public Integer getScrapreasonid() {
		return this.scrapreasonid;
	}

	public List<Workorder> getWorkorders() {
		return this.workorders;
	}

	public Workorder removeWorkorder(Workorder workorder) {
		getWorkorders().remove(workorder);
		workorder.setScrapreason(null);

		return workorder;
	}

	public void setModifieddate(Timestamp modifieddate) {
		this.modifieddate = modifieddate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setScrapreasonid(Integer scrapreasonid) {
		this.scrapreasonid = scrapreasonid;
	}

	public void setWorkorders(List<Workorder> workorders) {
		this.workorders = workorders;
	}

}