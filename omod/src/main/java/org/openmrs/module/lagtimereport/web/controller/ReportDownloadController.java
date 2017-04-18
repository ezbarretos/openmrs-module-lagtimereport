/**
 * 
 */
package org.openmrs.module.lagtimereport.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.lagtimereport.pdf.LagTimePDFView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author ossemaeb
 */
@Controller
public class ReportDownloadController {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/lagtimereport/downloadReporting", method = RequestMethod.GET)
	public void downloadPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		LagTimePDFView.CreatePDF();
		log.error("TESTE");
		
		File file = new File("c://FirstPdf.pdf");
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
}
