function initDataVersion()
{
	$.ajax({
		type : "get",
		url : "init.do",
		dataType : "json",
		success : function(msg) {
			$.each(msg.versions,function(i,version)
			{
				$("#dataVersion").append('<option value='+version+'>'+version+'</option>');
			});
			$("#curVersion").html(msg.curVersion);
		}
	});
	downloadSource();	
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
				
				downloadSource();
			}
			else
			{
				alert("服务器错误，请稍后再试");
			}
		}
	});
}

function downloadSource(){
	$.ajax({
		type : "get",
		url : "downloadSource.do",
		dataType : "json",
		success : function(msg) {
		if(msg!="")
		{	
			$("#downloadPanel").empty();
			$.each(msg,function(i,filepath)
			{
				var fname = filepath.substring(filepath.lastIndexOf("/")+1);
				$("#downloadPanel").append('<li><a id="download'+i+'"href="#"><i class="glyphicon glyphicon-ok-sign"> </i>'+fname+'</a></li>');
				$("#download"+i).attr("href",filepath);
			});
			
			
		}
		}
	});
}