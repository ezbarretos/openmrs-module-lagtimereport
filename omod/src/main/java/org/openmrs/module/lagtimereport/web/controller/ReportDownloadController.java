/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Encounter;
import org.openmrs.Form;
import org.openmrs.api.EncounterService;
import org.openmrs.api.FormService;
import org.openmrs.module.lagtimereport.FormValue;
import org.openmrs.module.lagtimereport.LagTimeReport;
import org.openmrs.module.lagtimereport.LagtimeReporting;
import org.openmrs.module.lagtimereport.service.LagTimeReportService;
import org.openmrs.module.lagtimereport.service.LagTimeReportSetupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author ossemaeb
 */
@Controller
public class ReportDownloadController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private String file = "c:\\LagTimeRepot.pdf";
	
	@Autowired
	LagTimeReportService reportService;
	
	@Autowired
	LagTimeReportSetupService lagtimeService;
	
	@Autowired
	private EncounterService encounterService;
	
	@Autowired
	private FormService formService;
	
	public LagTimeReportService getReportService() {
		return reportService;
	}
	
	public void setReportService(LagTimeReportService reportService) {
		this.reportService = reportService;
	}
	
	public LagTimeReportSetupService getLagtimeService() {
		return lagtimeService;
	}
	
	public void setLagtimeService(LagTimeReportSetupService lagtimeService) {
		this.lagtimeService = lagtimeService;
	}
	
	public EncounterService getEncounterService() {
		return encounterService;
	}
	
	public void setEncounterService(EncounterService encounterService) {
		this.encounterService = encounterService;
	}
	
	public FormService getFormService() {
		return formService;
	}
	
	public void setFormService(FormService formService) {
		this.formService = formService;
	}
	
	@RequestMapping(value = "/module/lagtimereport/downloadReporting", method = RequestMethod.GET)
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response,
	        @RequestParam(value = "reportId", required = false) Integer reportId) throws IOException {
		
		try {
			createPDF(reportId);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		File file = new File("c://LagTimeRepot.pdf");
		InputStream is = new FileInputStream(file);
		
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
		// Read from the file and write into the response
		OutputStream os = response.getOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		while ((len = is.read(buffer)) != -1) {
			os.write(buffer, 0, len);
		}
		os.flush();
		os.close();
		is.close();
	}
	
	public void createPDF(Integer reportId) throws Exception {
		Document document = new Document();
		OutputStream outputStream = new FileOutputStream(new File(file));
		try {
			
			PdfWriter.getInstance(document, outputStream);
			document.open();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		LagTimeReport lagTimeReport = reportService.getLagTimeReport(reportId);
		
		Paragraph title = new Paragraph();
		addEmptyLine(title, 1);
		Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
		title.add(new Paragraph("Lag Time Report " + reportId, titleFont));
		document.add(title);
		addEmptyLine(title, 1);
		
		PdfPTable tableHeader = new PdfPTable(2);
		tableHeader.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
		tableHeader.setTotalWidth(100);
		tableHeader.setWidths(new int[] { 4, 10 });
		tableHeader.setHorizontalAlignment(Element.ALIGN_LEFT);
		Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
		PdfPCell cell0 = new PdfPCell();
		
		tableHeader.setSpacingBefore(5);
		tableHeader.setWidthPercentage(100);
		tableHeader.getDefaultCell().setColspan(1);
		tableHeader.addCell(new Phrase("Lag Time Report Setup Name:", headerFont));
		tableHeader.addCell(lagTimeReport.getLagtimereportSetup().getName());
		
		tableHeader.addCell(new Phrase("Version:", headerFont));
		tableHeader.addCell(String.valueOf(lagTimeReport.getLagtimereportSetup().getVersion()));
		
		tableHeader.addCell(new Phrase("Descriçăo:", headerFont));
		tableHeader.addCell(lagTimeReport.getLagtimereportSetup().getDescription());
		
		tableHeader.addCell(new Phrase("Form(s):", headerFont));
		StringBuilder sb = new StringBuilder();
		for (Form form : lagTimeReport.getLagtimereportSetup().getForms()) {
			sb.append(form.getName());
			sb.append(", ");
			
		}
		tableHeader.addCell(sb.toString());
		
		tableHeader.addCell(new Phrase("Report Date Range:", headerFont));
		tableHeader.addCell(dateFormat.format(lagTimeReport.getStartDate()) + " to "
		        + dateFormat.format(lagTimeReport.getEndDate()));
		cell0.setBorder(PdfPCell.NO_BORDER);
		tableHeader.addCell(cell0);
		tableHeader.addCell(cell0);
		document.add(tableHeader);
		
		PdfPTable table = new PdfPTable(6);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 5.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f });
		table.setSpacingBefore(10);
		
		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(BaseColor.WHITE);
		
		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
		cell.setPadding(6);
		
		// write table header
		cell.setPhrase(new Phrase("Form", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Total Encounters in Paper", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Total Encounters in System", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("% Entered", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Average lag Time (days)", font));
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Median Lag Time (days)", font));
		table.addCell(cell);
		
		List<Encounter> encounters = encounterService.getEncounters(null, null, lagTimeReport.getStartDate(),
		    lagTimeReport.getEndDate(), lagTimeReport.getLagtimereportSetup().getForms(), null, null, null, null, false);
		
		Map<Integer, Integer> encounterCount = new HashMap<Integer, Integer>();
		Map<Integer, Integer> countDays = new HashMap<Integer, Integer>();
		Map<Integer, List<Integer>> medianDaysAux = new HashMap<Integer, List<Integer>>();
		Map<Integer, Integer> medianDays = new HashMap<Integer, Integer>();
		
		int sumDays = 0;
		for (Encounter encounter : encounters) {
			Integer count = encounterCount.get(encounter.getForm().getId());
			if (count == null) {
				count = 0;
			}
			
			encounterCount.put(encounter.getForm().getId(), count + 1);
			
			long diff = encounter.getDateCreated().getTime() - encounter.getEncounterDatetime().getTime();
			int convertToDays = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
			sumDays += convertToDays;
			countDays.put(encounter.getForm().getId(), sumDays);
			
			List<Integer> dateDiff = medianDaysAux.get(encounter.getForm().getId());
			if (dateDiff == null) {
				dateDiff = new ArrayList<Integer>();
			}
			dateDiff.add(convertToDays);
			medianDaysAux.put(encounter.getForm().getId(), dateDiff);
			
			Collections.sort(medianDaysAux.get(encounter.getForm().getId()));
			int formId = encounter.getForm().getId();
			int list = medianDaysAux.get(formId).size();
			int middle = (list / 2);
			
			medianDays.put(encounter.getForm().getId(), middle);
		}
		
		List<FormValue> formValues = new ArrayList<FormValue>();
		for (FormValue formValue : lagTimeReport.getFormValue()) {
			formValues.add(formValue);
		}
		
		List<LagtimeReporting> list = new ArrayList<LagtimeReporting>();
		for (int i = 0; i < lagTimeReport.getLagtimereportSetup().getForms().size(); i++) {
			
			int formId = formValues.get(i).getForms();
			if (encounterCount.containsKey(formId)) {
				Form form = formService.getForm(formId);
				int formValue = formValues.get(i).getNumberOfForm();
				
				DecimalFormat df = new DecimalFormat("#.#");
				Double percentageEntered = Double
				        .parseDouble(df.format(((double) encounterCount.get(formId) / (double) formValue) * 100));
				
				Integer averageDays = countDays.get(formId) / encounterCount.get(formId);
				
				LagtimeReporting reporting = new LagtimeReporting(form.getName(), formValue, encounterCount.get(formId),
				        percentageEntered, averageDays, medianDays.get(formId));
				list.add(reporting);
			}
			
		}
		for (LagtimeReporting report : list) {
			table.addCell(report.getForms());
			table.addCell(String.valueOf(report.getFormValue()));
			table.addCell(String.valueOf(report.getCountEncounter()));
			table.addCell(String.valueOf(report.getPercentageEntered()));
			table.addCell(String.valueOf(report.getAverageDays()));
			table.addCell(String.valueOf(report.getMedianDays()));
		}
		
		document.add(table);
		
		document.close();
		outputStream.close();
	}
	
	private static void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
}
