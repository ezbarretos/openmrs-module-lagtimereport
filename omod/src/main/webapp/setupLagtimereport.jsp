<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp"%>

<openmrs:htmlInclude file="/moduleResources/lagtimereport/js/jquery-3.1.1.min.js" />

<script type="text/javascript">
$(document).ready(function() {
	$("#save").click(function() {
		$("form").submit();
	});
	$("#cancel").click(function() {
		window.location.href = "${pageContext.request.contextPath}/module/lagtimereport/lagtimereport.list";
	});
});

</script>

<div class="widget">
	<h4>
		<b class="boxHeader"><spring:message
				code="lagtimereport.setup.title" /></b>
	</h4>

	<form method="post">
	${setupLagtimereport.lagTimeReportId}
	${setupLagtimereport.version}
	<input type="hidden" name="lagTimeReportId" id="lagTimeReportId" value="${setupLagtimereport.lagTimeReportId}"/>
	<input type="hidden" name="version" id="version" value="${setupLagtimereport.version}"/>
			<fieldset>
			<table>
				<tr>
					<td><openmrs:message code="general.name" /></td>
					<td>
					<input type="text" name="name" value="${setupLagtimereport.name }" size="35" />
					</td>
				</tr>
				<tr>
					<td valign="top"><openmrs:message code="general.description" /></td>
					<td>
					<textarea name="description" rows="5" cols="50">${setupLagtimereport.description }</textarea>
					</td>
				</tr>
			</table>
			<br />
			<tr>
			<td> <input type="button"
				value="<openmrs:message code="lagtimereport.save"/>" name="save" id="save">
				</td>
				<td> <input type="button"
				value="<openmrs:message code="lagtimereport.cancel"/>" name="cancel" id="cancel">
				</td>
				</tr>
		</fieldset>
	</form>

</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>