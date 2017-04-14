<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

<div class="widget">
	<h2>
		<b class="boxHeader"><spring:message code="lagtimereport.reporting.title" /> ${printReport.reportId}</b>
	</h2>
	<div>
		<table>
			<tr>
				<td><spring:message code="lagtimereport.reporting.setup.name" />:</td>
				<td>${printReport.lagtimereportSetup.name}</td>
			</tr>
			<tr>
				<td><spring:message code="lagtimereport.revision" />:</td>
				<td>${printReport.lagtimereportSetup.version }</td>
			</tr>
			<tr>
				<td><spring:message code="general.description" />:</td>
				<td>${printReport.lagtimereportSetup.description }</td>
			</tr>
			<tr>
				<td><spring:message code="lagtimereport.forms" />:</td>
				<td>
				<c:forEach var="forms" items="${printReport.lagtimereportSetup.forms}">
								${forms.name} <c:out value="${','}" />
								</c:forEach></td>
			</tr>
			<tr>
				<td><spring:message code="lagtimereport.dates" />:</td>
				<td><fmt:formatDate value="${printReport.startDate}" /> to <fmt:formatDate value="${printReport.endDate}" /></td>
			</tr>
		</table>
		</br>
		<table class="table table-striped">
				<thead>
					<tr>
						<th><spring:message code="lagtimereport.reporting.form" /></th>
						<th><spring:message code="lagtimereport.reporting.total.encounters.paper" /></th>
						<th><spring:message code="lagtimereport.reporting.total.encouter.system" /></th>
						<th><spring:message code="lagtimereport.reporting.percentage.entered" /></th>
						<th><spring:message code="lagtimereport.reporting.average.lagtimes" /></th>
						<th><spring:message code="lagtimereport.reporting.median.lagtimes" /></th>
					</tr>
				</thead>

				<c:forEach var="report" items="${printReport.formValue}">
					<tr>
						<td>A</td>
						<td>${report.numberOfForm}</td>
						<td>A</td>
						<td>A</td>
						<td>A</td>
						<td>A</td>
					</tr>
				</c:forEach>
			</table>
			<input type="submit"
				value="<openmrs:message code="lagtimereport.reporting.exit"/>" name="exit" id="exit"/>
	</div>
		
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>