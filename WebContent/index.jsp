<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
</head>
<body onload="initDataVersion()">
	<jsp:include page="header.jsp"></jsp:include>

	<div id="dataVersionDiv">
		选择数据版本
		<label>当前版本:</label><label id="curVersion"></label>
		 <select id="dataVersion" name="dataVersion"
			class="form-control">
			
		</select>
		<button type="button" onclick="cutVersion()">切换</button>
	</div>
	
	
	
	
	<jsp:include page="footer.jsp"></jsp:include>
	
	
</body>
<script type="text/javascript" src="js/index.js"></script>
</html>