<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script>
function draw()
{
	$.ajax(
			{
				type:"get",
				url:"\mvc\json.do",
				dataType:"json",
				success:function(msg)
				{
					alert(msg);
				}
			});
}


</script>
<body>
	<a href="javascript:void(0);" onclick="draw()">画图</a>
</body>
</html>