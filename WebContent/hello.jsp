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
<script src="js/cytoDrawPathway.js"></script>

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
	<button type="button" onclick="submitPathwayQuery()" class="btn btn-default">查看全部pathway</button>
	<h3>Result</h3>
					<div>
						<div id="cy">test</div>
					</div>

</body>
</html>