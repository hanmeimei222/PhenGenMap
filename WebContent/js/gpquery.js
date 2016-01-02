function showInputPanel(){
	
	var queryType = $("#queryType").val();
	
	if(queryType == 'inputMP')
	{
		$("#mpInputPanel").attr('class','show');
		$("#geneInputPanel").attr('class','hidden');
	}
	if(queryType == 'inputGene')
	{
		$("#mpInputPanel").attr('class','hidden');
		$("#geneInputPanel").attr('class','show');
	}
	if(queryType == 'inputBoth')
	{
		$("#mpInputPanel").attr('class','show');
		$("#geneInputPanel").attr('class','show');	
	}
}

function submitGPQuery()
{
	var showMPA = $("#showMPA").is(":checked");
	var queryType = $("#queryType").val();
	var mpList="";
	var geneList="";
	if(queryType == 'inputBoth'||queryType == 'inputMP' )
	{
		mpList = $("#mpList").val();
	}
	if(queryType == 'inputBoth'|| queryType == 'inputGene')
	{
		geneList= $("#geneList").val();
	}

	data = {"mpList":mpList,"geneList":geneList,"showMPA":showMPA};
	$.ajax({
		type : "post",
		data : data,
		url : "queryGPA.do",
		dataType : "json",
		success : function(msg) {
			 cytoscapeDraw(msg);
		}
	});
}