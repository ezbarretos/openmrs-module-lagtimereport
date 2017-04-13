package org.openmrs.module.lagtimereport.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.springframework.util.StringUtils;

public class LagTimeReportEditor extends PropertyEditorSupport {
	
	private Log log = LogFactory.getLog(getClass());
	
	public void setAsText(String text) throws IllegalArgumentException {
		LagTimeReportService ls = Context.getService(LagTimeReportService.class);
		if (StringUtils.hasText(text)) {
			try {
				setValue(ls.getLagTimeReport(Integer.valueOf(text)));
			}
			catch (Exception ex) {
				LagTimeReport ts = ls.getLagTimeReportByUuid(text);
				setValue(ts);
				if (ts == null) {
					log.error("Error setting text: " + text, ex);
					throw new IllegalArgumentException("Lag Time Report Setup not found: " + ex.getMessage());
				}
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		LagTimeReport t = (LagTimeReport) getValue();
		if (t == null) {
			return "";
		} else {
			return t.getId().toString();
		}
	}
}
