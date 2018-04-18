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

import java.util.ArrayList;
import java.util.List;

import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.ConversionUtil;
import org.openmrs.module.webservices.rest.web.Hyperlink;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.api.Converter;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

public class NeedsPagingEncounterDrugOrder<T> extends NeedsPaging<T> {
	
	public NeedsPagingEncounterDrugOrder(final List<T> unpagedResults, final RequestContext context) {
		super(unpagedResults, context);
	}
	
	@Override
	public SimpleObject toSimpleObject(final Converter preferredConverter) throws ResponseException {
		final List<Object> results = new ArrayList<>();
		for (final T match : this.getPageOfResults()) {
			results.add(ConversionUtil.convertToRepresentation(match, this.context.getRepresentation(), preferredConverter));
		}
		
		final SimpleObject ret = new SimpleObject().add("results", results);
		final boolean hasMore = this.hasMoreResults();
		if ((this.context.getStartIndex() > 0) || hasMore) {
			final List<Hyperlink> links = new ArrayList<>();
			if (hasMore) {
				links.add(this.context.getNextLink());
			}
			if (this.context.getStartIndex() > 0) {
				links.add(this.context.getPreviousLink());
			}
			ret.add("links", links);
		}
		if (Boolean.valueOf(this.context.getParameter("totalCount"))) {
			ret.add("totalCount", this.getTotalCount());
		}
		return ret;
	}
	
}
