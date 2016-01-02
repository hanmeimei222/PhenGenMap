function submitPathwayQuery()
{
	//1.获取输入的MP列表
	//var mpList = $("#mpList").val();
	var pwayList = "mmu00071";
	//2.获取查询条件 
	//var queryType=$("#queryType").val();
	var queryType = "sglways";
	
	data = {"pwayList":pwayList,"queryType":queryType};
	$.ajax({
		type : "post",
		data : data,
		url : "pwayQuery.do",
		dataType : "json",
		success : function(msg) {
			cytoDrawPathway(msg);
		}
	});
}