<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localMenu.jsp" %>

<openmrs:htmlInclude file="/moduleResources/lagtimereport/js/jquery-3.1.1.min.js" />
<openmrs:htmlInclude file="/moduleResources/lagtimereport/css/bootstrap.min.css" />
<openmrs:htmlInclude file="/moduleResources/lagtimereport/css/jquery.dataTables.min.css" />
<openmrs:htmlInclude file="/moduleResources/lagtimereport/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/moduleResources/lagtimereport/js/dataTable.js"/>

<div class="widget">
<h4><b class="boxHeader"><spring:message code="lagtimereport.title" /></b></h4>

		<a
		href="${pageContext.request.contextPath}/module/lagtimereport/setupLagtimereport.form"><spring:message
				code="lagtimereport.setup.menu" /></a>

<br/>
<form method="post">
	
<fieldset>
<table id="reportTable" class="display">
<thead>
  <tr>
   <th></th>
   <th>Name</th>
   <th>Description</th>
   <th>Revision</th>
   <th>Created on</th>
  </tr>
  </thead>
  <c:forEach var="report" items="${reports}">
      <tr>
      	<td><input type="checkbox" name="retire" id="retire" value="${report.lagTimeReportId }" /></td>
        <td id="name"><a href="setupLagtimereport.form?lagtimereportId=${report.lagTimeReportId}" id="lagtimeId">${report.name}</a></td>
        <td id="description">${report.description}</td>
        <td id="name">${report.version}</td>
        <td id="description">${report.dateCreated}</td>
      </tr>		
  </c:forEach>
  </table>
  <br /> <input type="submit"
				value="<openmrs:message code="lagtimereport.retire"/>" name="retire">
		</fieldset>
  </form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>
