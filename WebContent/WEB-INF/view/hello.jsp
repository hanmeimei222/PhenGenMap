<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/d3.v3.min.js"></script>
<script type="text/javascript" src="js/drawGraph.js"></script>
<link rel="stylesheet" type="text/css" href="css/graphStyle.css">

<title>Insert title here</title>


</head>
<body>
	<h2>查询一个MP节点n步内的所有节点</h2>
	MPO ID:<input type="text" id="mp_id" name="mp_id" class="input_text"/><br>
	步 数：<input type="text" id="step_num" name="step_num" class="input_text"/><br>
	
	<a href="javascript:void(0);" onclick="draw('genNStepNeighbor')">画图</a><br>
	
	
	<h2>查询一个MP子孙在给定层次中的节点</h2>
	ROOT ID:<input type="text" id="root_id" name="root_id" class="input_text"/><br>
	levels：<input type="text" id="levels" name="levels" class="input_text"/><br>
	
	<a href="javascript:void(0);" onclick="draw('getPostInLevels')">画图</a><br>
	
	
	<graph></graph>

</body>
</html>