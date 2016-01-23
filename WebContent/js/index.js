function initDataVersion()
{
	$.ajax({
		type : "get",
		url : "init.do",
		dataType : "json",
		success : function(msg) {
			$.each(msg,function(i,version)
			{
				$("#dataVersion").append('<option value='+version+'>'+version+'</option>');
			});
			$("#curVersion").html(msg[msg.length-1]);
		}
	});
}

function cutVersion()
{

	var version=$('#dataVersion option:selected').val();
	var data={"version":version};
	$.ajax({
		type : "get",
		data : data,
		url : "cutVersion.do",
		dataType : "json",
		success : function(msg) {
			if(msg)
			{
				alert("切换成功");
				$("#curVersion").html(version);
			}
			else
			{
				alert("服务器错误，请稍后再试");
			}
		}
	});
}