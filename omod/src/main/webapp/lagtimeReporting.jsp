<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
	</div>
		
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>