/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.poc.api.testorder.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.TestOrder;

public class TestOrderItem extends BaseOpenmrsData {
	
	private TestOrder testOrder;
	
	private Concept category;
	
	private TestOrderPOC parent;
	
	public TestOrderItem(final TestOrder testOrder, final Concept category) {
		
		this.testOrder = testOrder;
		this.category = category;
	}
	
	public TestOrderItem() {
		
	}
	
	public TestOrder getTestOrder() {
		return this.testOrder;
	}
	
	public void setTestOrder(final TestOrder testOrder) {
		this.testOrder = testOrder;
	}
	
	public Concept getCategory() {
		return this.category;
	}
	
	public void setCategory(final Concept category) {
		this.category = category;
	}
	
	public TestOrderPOC getParent() {
		return this.parent;
	}
	
	public void setParent(final TestOrderPOC parent) {
		this.parent = parent;
	}
	
	@Override
	public String toString() {
		return "TestOrderItem [testOrder=" + this.testOrder + ", category=" + this.category + ", parent=" + this.parent
		        + "]";
	}
	
	@Override
	public String getUuid() {
		
		if (this.testOrder != null) {
			return this.testOrder.getUuid();
		}
		return super.getUuid();
	}
	
	@Override
	public Integer getId() {
		return null;
	}
	
	@Override
	public void setId(final Integer arg0) {
		
	}
}
