<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp"%>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<openmrs:htmlInclude
	file="/moduleResources/lagtimereport/js/jquery-3.1.1.min.js" />
<openmrs:htmlInclude
	file="/moduleResources/lagtimereport/css/bootstrap.min.css" />
<openmrs:htmlInclude
	file="/moduleResources/lagtimereport/css/jquery.dataTables.min.css" />
<openmrs:htmlInclude
	file="/moduleResources/lagtimereport/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude
	file="/moduleResources/lagtimereport/js/dataTable.js" />

<script type="text/javascript">
/* 	$(document).ready(function() {
		$('#retireCheck').each(function(i){
			  if($(this).prop("checked")) {
			    $('#retireForm').show();
			  } else {
			    $('#retireForm').hide();
			  }
			});
	}); */
	$(document).ready(function() {
		$('#includedRetired').click(function(){
			  window.location.href = "${pageContext.request.contextPath}/module/lagtimereport/lagtimereportList.list";
			});
	});
</script>

<div class="widget">
	<h4>
		<b class="boxHeader"><spring:message code="lagtimereport.title" /></b>
	</h4>
	<tr>
		<td><a
			href="${pageContext.request.contextPath}/module/lagtimereport/addLagTimeReportSetup.form"><spring:message
					code="lagtimereport.setup.menu" /></a></td>
		<td><input type="checkbox" name="includedRetired" id="includedRetired">
		<spring:message code="lagtimereport.includedRetired" /></td>
	</tr>
	<fieldset>
		<form method="post">
			<table id="reportTable" class="display">
				<thead>
					<tr>
						<th></th>
						<th><spring:message code="general.name" /></th>
						<th><spring:message code="general.description" /></th>
						<th><spring:message code="lagtimereport.revision" /></th>
						<th><spring:message code="general.dateCreated" /></th>
					</tr>
				</thead>
				<c:forEach var="report" items="${lagTimeReports}">
					<tr>
						<td><input type="checkbox" name="checkRetire"
							id="checkRetire" class="checkRetireId"
							value="${report.lagTimeReportId }" /></td>
						<td id="name"><a
							href="addLagTimeReportSetup.form?lagtimereportId=${report.lagTimeReportId}"
							id="lagtimeId">${report.name}</a></td>
						<td id="description">${report.description}</td>
						<td id="name">${report.version}</td>
						<td id="dateCreated"><fmt:formatDate
								value="${report.dateCreated}" /></td>
					</tr>
				</c:forEach>
			</table>
			<br /> <input type="submit"
				value="<openmrs:message code="lagtimereport.retire"/>" name="retire" />
		</form>
		
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
