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
	function forceMaxLength(object, maxLength) {
		if (object.value.length >= maxLength) {
			object.value = object.value.substring(0, maxLength);
		}
	}
</script>

<script type="text/javascript">
	$(document).ready(function() {
		redirectCancel();
		ckeckAllForms();
	});

	function redirectCancel() {
		$("#cancel").click(function() {
			window.location.href = "${pageContext.request.contextPath}/module/lagtimereport/lagtimereportList.list";
		});
	}

	function ckeckAllForms() {
		$('input[name="selectForms"]').click(function() {
			$('input[name="forms"]').prop("checked", $(this).prop("checked"));
		});
	}
</script>

<!-- <div class="container">
	<div class="row">
		<div class="col-md-4">AS</div>
		<div class="col-md-8">BS</div>
	</div>
	<div class="row">
		<div class="col-md-4">T1</div>
		<div class="col-md-4">T2</div>
		<div class="col-md-4">T3</div>
	</div>
	<div class="row">
		<div class="col-md-4">Bt1</div>
		<div class="col-md-8">Bt2</div>
	</div>
</div> -->


<div class="widget">
	<h4>
		<b class="boxHeader"><spring:message code="lagtimereport.title" /></b>
	</h4>

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
			</br>
			<tr>
				<td><spring:message code="lagtimereport.select.forms" /></td>
				<td><input type="checkbox" name="selectForms" id="selectForms"
					multiple="multiple" /> <spring:message
						code="lagtimereport.selectAll.forms" /></td>
			</tr>

			<table frame="box">
				<c:forEach var="forms" items="${listLagTimeReportForms}"
					varStatus="status">
					<c:set var="formAlreadyExists" value="${false}" />
					<c:if test="${(status.index - 1) > 0}">
						<c:forEach var="lagtime" items="${lagTimeTeportSetup.forms}"
							begin="0" end="${status.index - 1}" varStatus="inner">
							<c:if test="${lagtime.formId == forms.formId}">
								<tr>
									<td><input type="checkbox" name="forms"
										id="forms" value="${forms.formId }"
										${lagtime.formId == forms.formId ? "checked":""} />
									</td>
									<td>${forms.name }</td>

								</tr>
								<c:set var="formAlreadyExists" value="${true}" />
							</c:if>
						</c:forEach>
					</c:if>
					<c:if test="${not formAlreadyExists}">
						<tr>
							<td><input type="checkbox" name="forms"
								id="forms" value="${forms.formId }" }/>
							</td>
							<td>${forms.name }</td>
					</c:if>
				</c:forEach>

			</table>
			<br />
			<tr>
				<td><input type="submit"
					value="<spring:message code="lagtimereport.save"/>" name="save"></td>
				<td><input type="button"
					value="<openmrs:message code="lagtimereport.cancel"/>"
					name="cancel" id="cancel"></td>
			</tr>
		</fieldset>
	</form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>