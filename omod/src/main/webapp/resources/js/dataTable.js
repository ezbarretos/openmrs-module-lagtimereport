/**
 * 
 */
jQuery(document).ready(function() {
	var table1 = $('#reportTable').DataTable({
		"bPaginate" : true,
		"iDisplayLength" : 10,
		"bLengthChange" : false,
		"bFilter" : true,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : true,
		"bSortable" : false,
	});
	
	var table2 = $('#retiredReportTable').DataTable({
		"bPaginate" : true,
		"iDisplayLength" : 10,
		"bLengthChange" : false,
		"bFilter" : true,
		"bSort" : false,
		"bInfo" : false,
		"bAutoWidth" : true,
		"bSortable" : false,
	});

});



