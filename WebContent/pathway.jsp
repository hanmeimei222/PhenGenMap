<!DOCTYPE HTML>
<html>
<head>
<title>hello</title>
<meta charset=utf-8 />
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>


<script src="js/cytoscape/cytoscape.js"></script>
<script src="js/cytoscape/jquery.cytoscape.js-panzoom.js"></script>



<script src="js/pathwayquery.js"></script>
<script src="js/cytoscapeDrawPathway.js"></script>

<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

<style>
body {
	font-family: helvetica;
	font-size: 14px;
}

#cy {
	width: 90%;
	height: 400px;
}
</style>


</head>
<body>
<div class="col-md-2 services-left">
<label class="post-content-row-title">step1：输入要查询的pathway</label>



<div class="form-group"><textarea id="pwayList" name="pwayList"	class="form-control" rows="3">mmu00601
mmu00603</textarea></div>



<label class="post-content-row-title"> step2:选择要查询的类型</label> <select
								id="queryType" name="queryType" class="form-control"
								onchange="showAdditionalPanel()">
								<option value="allpathways">查询全体pathway信息</option>
								<option value="sglways">查询特定pathway信息</option>
								
							</select>
							
<button type="button" onclick="submitPathwayQuery()"
									class="btn btn-default">查询</button>
</div>

<div class="col-md-8 services-right">
<h3>查询结果</h3>
<div id="cy">test</div>
</div>

</body>
</html>