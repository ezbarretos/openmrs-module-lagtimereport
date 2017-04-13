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
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/css/jquery.dataTables.min.css"/>
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/jquery-ui.js"/>
<openmrs:htmlInclude
	file="${pageContext.request.contextPath}/moduleResources/lagtimereport/js/dataTable.js" />

<script type="text/javascript">
	$(document).ready(function() {
		datepicker();
		//forms();
		test();

	});

	function datepicker() {
		$(".datepick").datepicker();
	};

	function test() {
		$("select#lagtimereportId").change(function() {
			var lagtimereportId = $('#lagtimereportId').val();
			$.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/ws/module/lagtimereport/fetchForms",
				dataType : "json",
				data : {
					lagtimereportId : lagtimereportId
				},
				success : function(result) {
					var trHTML = '';
					$.each(result, function(index, item) {
						trHTML += '<tr><td><spring:bind path="lagTimeReport.formValue"><input type="hidden" name="forms" value="'+index+'"/></spring:bind></td>'
							+ item +
						'<td><spring:bind path="lagTimeReport.formValue"><input type="text" name="numberOfForm"/></spring:bind></td><tr>';
					});

					$('#formValue').html(trHTML);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert('Error: ' + textStatus + "-" + errorThrown);
				}
			});
		});
	}
</script>
<div class="widget">
	<h4>
		<b class="boxHeader"><spring:message
				code="lagtimereport.add.title" /></b>
	</h4>

	<spring:hasBindErrors name="lagTimeReport">
		<spring:message code="fix.error" />
		<br />
	</spring:hasBindErrors>
	<form method="post">
		<fieldset>
			<div>
				<table id="lagTimeTeport">
					<tr>
						<td class="formLabel"><spring:message
								code="lagtimereport.addlagtimereport.select.report" /></td>
						<td><spring:bind path="lagTimeReport.lagtimereportSetup">
								<select name="lagtimereportSetup" id="lagtimereportId">
									<option value="${0 }">--Seleccione--</option>
									<c:forEach var="lagtimereportlist"
										items="${listLagTimeReportSetup}">
										<option  value="${lagtimereportlist.lagTimeReportId}">${lagtimereportlist.name}</option>
									</c:forEach>
								</select>
							</spring:bind></td>
					</tr>
					<tr>
						<td><spring:bind path="lagTimeReport.dueDate">Report Due Date<input type="text" name="dueDate"
							id="dueDate" class="datepick" /><img
							src="${pageContext.request.contextPath}/moduleResources/lagtimereport/images/calendarIcon.png"
							class="calendarIcon" alt="" onclick="datepicker()" /></spring:bind></td>
					</tr>
					<tr>
						<td><spring:bind path="lagTimeReport.startDate">Start Date<input type="text" name="startDate"
							id="startDate" class="datepick"/> <img
							src="${pageContext.request.contextPath}/moduleResources/lagtimereport/images/calendarIcon.png"
							class="calendarIcon" alt="" /></spring:bind></td>
							
						<td><spring:bind path="lagTimeReport.endDate">End Date<input type="text" name="endDate" id="endDate" 
						class="datepick"/>
							<img
							src="${pageContext.request.contextPath}/moduleResources/lagtimereport/images/calendarIcon.png"
							class="calendarIcon" alt="" /></spring:bind></td>
					</tr>

				</table>
			</div>
			</br> </br>
			<p>Please enter the total number of paper encounter forms filled that occurred between
			Start Date and End Date for all encounter locations</p>
			<div id="formValue"></div>
			
			</br> </br>
			<div>
				<tr>
					<td><input type="submit"
						value="<spring:message code="lagtimereport.save"/>" name="save"></td>
					<td><input type="button"
						value="<openmrs:message code="lagtimereport.cancel"/>"
						name="cancel" id="cancel"></td>
				</tr>
			</div>
		</fieldset>
	</form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>