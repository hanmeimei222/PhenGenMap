function showAdditionalPanel()
{
	var queryType=$("#queryType").val();
	if(queryType == 'nStepNodes')
	{
		$("#step").attr('class','show');
	}
	else
	{
		$("#step").attr('class','hidden');
	}
}
function showLevelPanel()
{
	if($("#levelCheck").is(':checked'))
	{
		$("#levelPanel").attr('class','show');
	}
	else
	{
		$("#levelPanel").attr('class','hidden');
	}
}


function submitQuery()
{
	//1.获取输入的MP列表
	var mpList = $("#mpList").val();
	//2.获取查询条件
	var queryType=$("#queryType").val();
	var step = 0;
	if(queryType == 'nStepNodes')
	{
		step = $("#step").val();
	}
	//3.判断是否过滤层
	var levelFilter = $("#levelCheck").is(':checked');
	var levels ="";
	if(levelFilter)
	{
		levels = $("#levels").val();
	}
	data = {"mpList":mpList,"queryType":queryType,"levels":levels,"step":step};
	$.ajax({
		type : "post",
		data : data,
		url : "pheQuery.do",
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

