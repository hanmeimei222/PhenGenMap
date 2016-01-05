function submitPathwayQuery()
{
	//1.获取输入的pathway列表
	var pwayList = $("#pwayList").val();
//	var pwayList = "mmu00071";
	//2.获取查询条件 
	var queryType=$("#queryType").val();
//	var queryType = "allpathways";
	
	queryData = {"pwayList":pwayList,"queryType":queryType};
	$.ajax({
		type : "post",
		data : queryData,
		url : "pwayQuery.do",
		dataType : "json",
		success : function(msg) {
			d3DrawPathway(msg);
//			cytoDrawPathway(msg);
		}
	});
}