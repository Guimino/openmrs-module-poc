/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.poc.web.resource;

import org.openmrs.module.poc.pocheuristic.entity.OrderWrapper;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource(name = RestConstants.VERSION_1
        + "/orderresource", supportedClass = OrderWrapper.class, supportedOpenmrsVersions = { "1.10.*", "1.11.*",
        "1.12.*", "2.0.*", "2.1.*" })
public class OrderWrapperResource extends DataDelegatingCrudResource<OrderWrapper> {
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(final Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			final DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty("drugOrder", Representation.REF);
			description.addProperty("testOrder", Representation.REF);
			description.addProperty("concept", Representation.REF);
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (rep instanceof FullRepresentation) {
			final DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty("drugOrder");
			description.addProperty("testOrder");
			description.addProperty("concept");
			description.addProperty("auditInfo");
			description.addSelfLink();
			return description;
		} else {
			return null;
		}
	}
	
	/**
	 * Display string for {@link Order}
	 * 
	 * @param order
	 * @return ConceptName
	 */
	@PropertyGetter("display")
	public String getDisplayString(final OrderWrapper order) {
		return order.getConcept().getDisplayString();
	}
	
	@Override
	public OrderWrapper getByUniqueId(final String uniqueId) {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public OrderWrapper newDelegate() {
		return new OrderWrapper();
	}
	
	@Override
	public OrderWrapper save(final OrderWrapper delegate) {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	protected void delete(final OrderWrapper delegate, final String reason, final RequestContext context)
	        throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public void purge(final OrderWrapper delegate, final RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
}
