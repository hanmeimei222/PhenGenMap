function showInputPanel(){
	if($("#chkGene").is(':checked'))
	{
		$("#geneInputPanel").attr('class','show');
	}
	else
	{
		$("#geneInputPanel").attr('class','hidden');
	}
	if($("#chkPathway").is(":checked"))
	{
		$("#pathwayInputPanel").attr('class','show');
	}
	else
	{
		$("#pathwayInputPanel").attr('class','hidden');
	}
}


function showAllPathway(){
	$.ajax({
		type : "post",
		url : "allPathway.do",
		dataType : "json",
		success : function(msg) {
			if(msg!=""){
			$("#downloadPanel1").attr('class','show');
			}else{
			$("#downloadPanel1").attr('class','hidden');
			}
			d3DrawPathway(msg.data);
			if(msg.path!="")
			{
				$("#downloadAllPathway").attr("href",msg.path);
			}
		}
	});
}

function submitPathwayQuery()
{
	var geneList="";
	var pathwayList="";
	var queryType="";
	//获取查询类型 &&获取输入的pathway列表&&获取输入gene列表
	if($("#chkGene").is(':checked'))
	{
		geneList= $("#geneList").val();
		queryType += 'gene_';
	}
	if($("#chkPathway").is(":checked"))
	{
		pathwayList = $("#pathwayList").val();
		queryType += 'pathway_';
	}
	
	data = {"param":{"geneList":geneList,"pathwayList":pathwayList},"queryType":queryType};
	$.ajax({
		type : "post",
		data : data,
		url : "pwayQuery.do",
		dataType : "json",
		success : function(msg) {
			if(msg!=""){
			$("#downloadPanel2").attr('class','show');
			}else{
			$("#downloadPanel2").attr('class','hidden');
			}
			if(msg.path!="")
			{
				$("#download").attr("href",msg.path);
			}
			var simArr = msg.data.treenode;
			for(var i=0;i<simArr.length;i++)
			{
				drawSimPathway(msg.data.treenode[i],msg.data.treenode[i].children[0].id);
			}
			
			cytoscapeDraw(msg.data.graph);
			
			
			
		}
	});
}