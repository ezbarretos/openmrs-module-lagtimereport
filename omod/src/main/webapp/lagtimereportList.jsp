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

<script type="text/javascript">
	$(document).ready(function() {
		showHideReasonField();
		showRetired();
	}); 
	
	function showHideReasonField(){
		$("#reasonForm").hide();
		$('input[name="checkRetire"]').click(function(){
			if ($(this).prop("checked")){
				$('#reasonForm').show();
			}else
		    {
				$("#reasonForm").hide();
		    }
		});
	}
	
	function showRetired() {
		$("#all").hide();
		$('input[name="includedRetired"]').click(function(){
			if ($(this).prop("checked")){
				$("#notRetired").hide();
					$("#all").show();
				
			}else {
				$("#all").hide();
				$("#notRetired").show();
			}
			  
			});
	}

</script>

<div class="widget">
	<h4>
		<b class="boxHeader"><spring:message code="lagtimereport.title" /></b>
	</h4>
	<tr>
		<td><a
			href="${pageContext.request.contextPath}/module/lagtimereport/addLagTimeReportSetup.form"><spring:message
					code="lagtimereport.setup.menu" /></a></td>&nbsp
		<td><input type="checkbox" name="includedRetired"
			id="includedRetired"> <spring:message
				code="lagtimereport.includedRetired" /></td>
	</tr>
	<fieldset id="notRetired">
		<form method="post">
			<table  id="reportTable" class="table table-striped">
				<thead>
					<tr>
					<th></th>
						<th></th>
						<th><spring:message code="general.name" /></th>
						<th><spring:message code="general.description" /></th>
						<th><spring:message code="lagtimereport.revision" /></th>
						<th><spring:message code="general.dateCreated" /></th>
					</tr>
				</thead>

				<c:forEach var="report" items="${lagTimeReports}">
					<tr><td><input type="hidden" name="id" value="${report.lagTimeReportId}"/></td>
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

			<div id="reasonForm">
				<textarea name="reason" id="reason" rows="5" cols="40"> </textarea>
			</div>

			<br /> <input type="submit"
				value="<openmrs:message code="lagtimereport.retire"/>" name="retire" />
		</form>
	</fieldset>

	<fieldset id="all">
		<table id="retiredReportTable" class="table table-striped">
			<thead>
				<tr>
				<th></th>
					<th></th>
					<th><spring:message code="general.name" /></th>
					<th><spring:message code="general.description" /></th>
					<th><spring:message code="lagtimereport.revision" /></th>
					<th><spring:message code="general.dateCreated" /></th>
				</tr>
			</thead>

			<c:forEach var="allReport" items="${retiredLagTimeReports}">
				<tr>
				<td><input type="hidden" name="id" value="${allReport.lagTimeReportId}"/></td>
					<c:choose>
						<c:when test="${allReport.retired == true}">
							<td><input type="checkbox" name="checkRetire" checked="checked"
								id="checkRetire" value="${allReport.lagTimeReportId}" disabled="disabled"/></td>
						</c:when>
						<c:otherwise>
							<td><input type="checkbox" name="checkRetire"
								id="checkRetire" value="${allReport.lagTimeReportId}" /></td>
						</c:otherwise>
					</c:choose>
					<td id="name"><a
						href="addLagTimeReportSetup.form?lagtimereportId=${allReport.lagTimeReportId}"
						id="lagtimeId">${allReport.name}</a></td>
					<td id="description">${allReport.description}</td>
					<td id="name">${allReport.version}</td>
					<td id="dateCreated"><fmt:formatDate
							value="${allReport.dateCreated}" /></td>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
