function showInputPanel(){
	if($("#chkGene").is(':checked'))
	{
		$("#geneInputPanel").attr('class','show');
	}
	else
	{
		$("#geneInputPanel").attr('class','hidden');
	}
	if($("#chkMp").is(":checked"))
	{
		$("#mpInputPanel").attr('class','show');
	}
	else
	{
		$("#mpInputPanel").attr('class','hidden');
	}
	if($("#chkPathway").is(":checked"))
	{
		$("#pathwayInputPanel").attr('class','show');
	}
	else
	{
		$("#pathwayInputPanel").attr('class','hidden');
	}
	if($("#chkPPI").is(":checked"))
	{
		$("#ppiInputPanel").attr('class','show');
	}
	else
	{
		$("#ppiInputPanel").attr('class','hidden');
	}
}

function submitGPQuery()
{
	var mpList="";
	var geneList="";
	var pathwayList="";
	var ppiList ="";
	var queryType="";
	
	if($("#chkGene").is(':checked'))
	{
		geneList= $("#geneList").val();
		queryType += 'gene_';
	}
	if($("#chkMp").is(":checked"))
	{
		mpList = $("#mpList").val();
		queryType += 'mp_';
	}
	if($("#chkPathway").is(":checked"))
	{
		pathwayList = $("#pathwayList").val();
		queryType += 'pathway_';
	}
	if($("#chkPPI").is(":checked"))
	{
		ppiList = $("#ppiList").val();
		queryType += 'ppi_';
	}

	data = {"param":{"mpList":mpList,"geneList":geneList,"pathwayList":pathwayList,"ppiList":ppiList},"queryType":queryType};
	$.ajax({
		type : "post",
		data : data,
		url : "queryAsso.do",
		dataType : "json",
		success : function(msg) {
			if(msg!=""){
			$("#downloadPanel").attr('class','show');
			}else{
			$("#downloadPanel").attr('class','hidden');
			}
			cytoscapeDraw(msg.data);
			if(msg.path!="")
			{
				$("#download").attr("href",msg.path);
			}
		}
	});
}

