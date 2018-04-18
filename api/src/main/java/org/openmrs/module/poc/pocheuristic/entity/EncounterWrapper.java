/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.poc.pocheuristic.entity;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.EncounterProvider;
import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Visit;

public class EncounterWrapper extends BaseOpenmrsData {
	
	private Date encounterDatetime;
	
	private Patient patient;
	
	private Location location;
	
	private Form form;
	
	private EncounterType encounterType;
	
	private Set<OrderWrapper> orders = new LinkedHashSet<>();
	
	private Set<Obs> obs;
	
	private Visit visit;
	
	private final Set<EncounterProvider> encounterProviders = new LinkedHashSet<>();
	
	public EncounterWrapper(final Date encounterDatetime, final Patient patient, final Location location,
	    final Form form, final EncounterType encounterType, final Set<Obs> obs, final Visit visit,
	    final Set<Order> orders) {
		super();
		this.encounterDatetime = encounterDatetime;
		this.patient = patient;
		this.location = location;
		this.form = form;
		this.encounterType = encounterType;
		this.obs = obs;
		this.visit = visit;
		this.addOrders(orders);
	}
	
	public EncounterWrapper() {
	}
	
	public Date getEncounterDatetime() {
		return this.encounterDatetime;
	}
	
	public void setEncounterDatetime(final Date encounterDatetime) {
		this.encounterDatetime = encounterDatetime;
	}
	
	public Patient getPatient() {
		return this.patient;
	}
	
	public void setPatient(final Patient patient) {
		this.patient = patient;
	}
	
	public Location getLocation() {
		return this.location;
	}
	
	public void setLocation(final Location location) {
		this.location = location;
	}
	
	public Form getForm() {
		return this.form;
	}
	
	public void setForm(final Form form) {
		this.form = form;
	}
	
	public EncounterType getEncounterType() {
		return this.encounterType;
	}
	
	public void setEncounterType(final EncounterType encounterType) {
		this.encounterType = encounterType;
	}
	
	public Set<OrderWrapper> getOrders() {
		return this.orders;
	}
	
	public void setOrders(final Set<OrderWrapper> orders) {
		this.orders = orders;
	}
	
	public void addOrders(final Set<Order> orders) {
		
		for (final Order order : orders) {
			
			this.orders.add(new OrderWrapper(order));
		}
	}
	
	public Set<Obs> getObs() {
		return this.obs;
	}
	
	public void setObs(final Set<Obs> obs) {
		this.obs = obs;
	}
	
	public Visit getVisit() {
		return this.visit;
	}
	
	public void setVisit(final Visit visit) {
		this.visit = visit;
	}
	
	public Set<EncounterProvider> getEncounterProviders() {
		return this.encounterProviders;
	}
	
	@Override
	public Integer getId() {
		return null;
	}
	
	@Override
	public void setId(final Integer arg0) {
		// this is a wrapper entity
	}
}
