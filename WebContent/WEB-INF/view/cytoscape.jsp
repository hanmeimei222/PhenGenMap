<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="css/cytoscape/jquery.cytoscape.js-panzoom.css"
	rel="stylesheet" type="text/css" />
<link href="css/cytoscape/font-awesome.css" rel="stylesheet"
	type="text/css" />

<script src="js/jquery-1.9.1.min.js"></script>
<script src="js/cytoscape/cytoscape.js"></script>
<script src="js/cytoscape/jquery.cytoscape.js-panzoom.js"></script>

<script src="js/cytoscapeDraw.js"></script>


<title>Insert title here</title>
<style>
body {
	font-family: helvetica;
	font-size: 14px;
}

#cy {
	width: 1024px;
	height: 800px;
}
</style>


</head>
<body>
<body>
	<h2>查询一个MP节点n步内的所有节点</h2>
	MPO ID:<input type="text" id="mp_id" name="mp_id" class="input_text"/><br>
	步 数：<input type="text" id="step_num" name="step_num" class="input_text"/><br>
	
	<a href="javascript:void(0);" onclick="draw('getNStepNeighbor')">画图</a><br>
	
	<h2>查询一个MP子孙在给定层次中的节点</h2>
	ROOT ID:<input type="text" id="root_id" name="root_id" class="input_text"/><br>
	levels：<input type="text" id="levels" name="levels" class="input_text"/><br>
	
	<a href="javascript:void(0);" onclick="draw('getPostInLevels')">画图</a><br>
	<div id="cy"></div>
</body>

</html>
