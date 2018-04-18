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

import java.util.Date;
import java.util.List;

import org.openmrs.Encounter;
import org.openmrs.EncounterType;
import org.openmrs.Patient;
import org.openmrs.api.EncounterService;
import org.openmrs.api.context.Context;
import org.openmrs.module.poc.pocheuristic.entity.EncounterWrapper;
import org.openmrs.module.poc.pocheuristic.service.PocHeuristicService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.EmptySearchResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.resource.openmrs1_8.PatientResource1_8;

@Resource(name = RestConstants.VERSION_1
        + "/encounterwrapper", supportedClass = EncounterWrapper.class, supportedOpenmrsVersions = { "1.9.*", "1.10.*",
        "1.11.*", "1.12.*", "2.0.*" })
public class EncounterWrapperResource extends DataDelegatingCrudResource<EncounterWrapper> {
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(final Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			final DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty("encounterDatetime");
			description.addProperty("patient", Representation.REF);
			description.addProperty("location", Representation.REF);
			description.addProperty("form", Representation.REF);
			description.addProperty("encounterType", Representation.REF);
			description.addProperty("obs", Representation.REF);
			description.addProperty("orders", Representation.REF);
			description.addProperty("voided");
			description.addProperty("visit", Representation.REF);
			description.addProperty("encounterProviders", Representation.REF);
			
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (rep instanceof FullRepresentation) {
			final DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("display");
			description.addProperty("encounterDatetime");
			description.addProperty("patient", Representation.REF);
			description.addProperty("location");
			description.addProperty("form");
			description.addProperty("encounterType");
			description.addProperty("obs");
			description.addProperty("orders");
			description.addProperty("voided");
			description.addProperty("auditInfo");
			description.addProperty("visit", Representation.DEFAULT);
			description.addProperty("encounterProviders", Representation.DEFAULT);
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		final DelegatingResourceDescription description = new DelegatingResourceDescription();
		
		description.addProperty("encounterDatetime"); // has a default value
		description.addRequiredProperty("patient");
		description.addRequiredProperty("encounterType");
		description.addProperty("location");
		description.addProperty("form");
		description.addProperty("orders");
		description.addProperty("obs");
		description.addProperty("visit");
		description.addProperty("encounterProviders");
		
		return description;
	}
	
	@Override
	public EncounterWrapper newDelegate() {
		final EncounterWrapper enc = new EncounterWrapper();
		enc.setEncounterDatetime(new Date());
		return enc;
	}
	
	@Override
	public EncounterWrapper save(final EncounterWrapper enc) {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public EncounterWrapper getByUniqueId(final String uuid) {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public void delete(final EncounterWrapper enc, final String reason, final RequestContext context)
	        throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@Override
	public void purge(final EncounterWrapper enc, final RequestContext context) throws ResponseException {
		throw new ResourceDoesNotSupportOperationException();
	}
	
	@PropertyGetter("display")
	public String getDisplayString(final EncounterWrapper encounter) {
		String ret = encounter.getEncounterType() == null ? "?" : encounter.getEncounterType().getName();
		ret += " ";
		ret += encounter.getEncounterDatetime() == null ? "?"
		        : Context.getDateFormat().format(encounter.getEncounterDatetime());
		return ret;
	}
	
	@Override
	protected PageableResult doSearch(final RequestContext context) {
		
		final String patientUuid = context.getRequest().getParameter("patient");
		final String encounterTypeUuid = context.getParameter("encounterType");
		
		final Patient patient = ((PatientResource1_8) Context.getService(RestService.class)
		        .getResourceBySupportedClass(Patient.class)).getByUniqueId(patientUuid);
		final EncounterType encounterType = Context.getEncounterService().getEncounterTypeByUuid(encounterTypeUuid);
		
		if ((patient != null) && (encounterType != null)) {
			
			return new NeedsPaging<>(Context.getService(PocHeuristicService.class)
			        .findEncountersByPatientAndEncounterType(patient, encounterType), context);
		}
		if (patientUuid != null) {
			if (patient == null) {
				return new EmptySearchResult();
			}
			final List<Encounter> encs = Context.getEncounterService().getEncountersByPatient(patient);
			return new NeedsPaging<>(encs, context);
		}
		
		return new ServiceSearcher<Encounter>(EncounterService.class, "getEncounters", "getCountOfEncounters")
		        .search(context.getParameter("q"), context);
	}
}
