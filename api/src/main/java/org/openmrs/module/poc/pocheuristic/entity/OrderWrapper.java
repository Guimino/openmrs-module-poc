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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.DrugOrder;
import org.openmrs.Order;
import org.openmrs.TestOrder;

public class OrderWrapper extends BaseOpenmrsData {
	
	private DrugOrder drugOrder;
	
	private TestOrder testOrder;
	
	private Concept concept;
	
	public OrderWrapper(final Order order) {
		
		if (order instanceof DrugOrder) {
			this.drugOrder = (DrugOrder) order;
		} else if (order instanceof TestOrder) {
			this.testOrder = (TestOrder) order;
		}
		this.concept = order.getConcept();
	}
	
	public OrderWrapper() {
		
	}
	
	public DrugOrder getDrugOrder() {
		return this.drugOrder;
	}
	
	public void setDrugOrder(final DrugOrder drugOrder) {
		this.drugOrder = drugOrder;
	}
	
	public TestOrder getTestOrder() {
		return this.testOrder;
	}
	
	public void setTestOrder(final TestOrder testOrder) {
		this.testOrder = testOrder;
	}
	
	@Override
	public Integer getId() {
		return null;
	}
	
	@Override
	public void setId(final Integer arg0) {
	}
	
	@Override
	public String getUuid() {
		if (this.drugOrder != null) {
			return this.drugOrder.getUuid();
		}
		if (this.testOrder != null) {
			return this.testOrder.getUuid();
		}
		return super.getUuid();
	}
	
	// private void copyInheritanceAttributes(final Order order, final Order
	// target) {
	//
	// try {
	// BeanUtils.copyProperties(order, target);
	// } catch (final Exception e) {
	//
	// throw new RuntimeException(e.getMessage());
	// }
	// }
	
	public Concept getConcept() {
		return this.concept;
	}
	
	public void setConcept(final Concept concept) {
		this.concept = concept;
	}
}
