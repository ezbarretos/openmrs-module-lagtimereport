<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp"%>

<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/jquery-3.1.1.min.js"/>

<script type="text/javascript">
	function forceMaxLength(object, maxLength) {
		if (object.value.length >= maxLength) {
			object.value = object.value.substring(0, maxLength);
		}
	}
</script>

<script type="text/javascript">
$(document).ready(function() {
	$("#cancel").click(function() {
		window.location.href = "${pageContext.request.contextPath}/module/lagtimereport/lagtimereportList.list";
	});
});

</script>

<h2>
	<spring:message code="lagtimereport.title" />
</h2>

<spring:hasBindErrors name="lagTimeTeportSetup">
	<spring:message code="fix.error" />
	<br />
</spring:hasBindErrors>
<form method="post">
	<fieldset>
		<table>
			<tr>
				<td><spring:message code="general.name" /></td>
				<td><spring:bind path="lagTimeTeportSetup.name">
						<input type="text" name="name"
							value="<c:out value="${status.value}"/>" size="35" />
						<c:if test="${status.errorMessage != ''}">
							<span class="error">${status.errorMessage}</span>
						</c:if>
					</spring:bind></td>
			</tr>
			<tr>
				<td valign="top"><spring:message code="general.description" /></td>
				<td valign="top"><spring:bind
						path="lagTimeTeportSetup.description">
						<textarea name="description" rows="5" cols="40"
							onkeypress="return forceMaxLength(this, 1024);"><c:out
								value="${status.value}" /></textarea>
						<c:if test="${status.errorMessage != ''}">
							<span class="error">${status.errorMessage}</span>
						</c:if>
					</spring:bind></td>
			</tr>
		</table>
		<br />
		<tr>
			<td><input type="submit"
				value="<spring:message code="lagtimereport.save"/>" name="save"></td>
			<td><input type="button"
				value="<openmrs:message code="lagtimereport.cancel"/>" name="cancel"
				id="cancel"></td>
		</tr>
	</fieldset>
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>