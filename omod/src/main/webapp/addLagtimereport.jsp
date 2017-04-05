<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp"%>

<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/jquery-3.1.1.min.js" />
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/css/bootstrap.min.css" />
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/bootstrap.min.js" />
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/css/jquery.dataTables.min.css" />
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/dataTable.js" />
<script type="text/javascript">
$(document).ready(function() {
    $("#datepicker").datepicker({
    	dateFormat: "dd-mm-yyyy"
    	});
    });
</script>
<div class="widget">
	<h4>
		<b class="boxHeader"><spring:message code="lagtimereport.add.title" /></b>
	</h4>
	
<spring:hasBindErrors name="lagTimeTeport">
	<spring:message code="fix.error" />
	<br />
</spring:hasBindErrors>
<form method="post" >
	<fieldset>
		<table id="lagTimeTeport">
			<tr>
				<td class="formLabel"><spring:message
						code="lagtimereport.addlagtimereport.select.report" /></td>
				<td><spring:bind path="lagTimeTeport">
						<select name="lagTimeTeport" id="lagTimeTeport">
							<option>--Seleccione--</option>
							<c:forEach var="lagtimereportlist" items="${listLagTimeReportSetup}">
								<option value="${lagtimereportlist.lagTimeReportId}">${lagtimereportlist.name}</option>
							</c:forEach>
						</select>
					</spring:bind></td>
			</tr>
			<tr>
				<td>Report Due Date<input type="date" name="dueDate" id="datepicker"/><img
						src="${pageContext.request.contextPath}/moduleResources/lagtimereport/images/calendarIcon.png"
						class="calendarIcon" alt=""/></td>
			</tr>
			<tr>
				<td>Start Date<input type="date" name="startDate" id="datepicker"/> <img
						src="${pageContext.request.contextPath}/moduleResources/lagtimereport/images/calendarIcon.png"
						class="calendarIcon" alt=""/></td>
				<td> End Date<input type="date" name="endDate" id="datepicker"/> <img
						src="${pageContext.request.contextPath}/moduleResources/lagtimereport/images/calendarIcon.png"
						class="calendarIcon" alt=""/></td>
			</tr>
			
			<%-- <c:forEach var="lagtime" items="${lagTimeTeportSetup.forms}" varStatus="inner">
								<tr>
									<td><input type="checkbox" name="forms"
										id="forms" value="${forms.formId }"
										${lagtime.formId == forms.formId ? "checked":""} />
									</td>
									<td>${forms.name }</td>
								</tr>
						</c:forEach> --%>

			<tr>
				<td><input type="submit"
					value="<spring:message code="lagtimereport.save"/>" name="save"></td>
				<td><input type="button"
					value="<openmrs:message code="lagtimereport.cancel"/>"
					name="cancel" id="cancel"></td>
			</tr>
		</table>
	</fieldset>
</form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>