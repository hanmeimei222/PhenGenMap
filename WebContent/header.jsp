<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

<title>Home</title>
</head>
<body>
	<div class="header">
		<div class="container">
			<div class="header-main">
				<div class="logo">
					<a href="#"><img src="images/logo_4.png" alt=""></a>
				</div>
				<div class="top-nav">
					<span class="menu"> <img src="images/icon.png" alt="" /></span>
					<ul class="nav nav-pills nav-justified res">
						
						<li><a  href="index.jsp" id="1" ><i
								class="glyphicon glyphicon-home"> </i>Home</a></li>
						<li><a href="globalGraph.jsp" id="2" ><i
								class="glyphicon glyphicon-globe"> </i>GlobalAsso</a></li>
						<li><a href="assoQuery.jsp" id="3" ><i
								class="glyphicon glyphicon-zoom-in"> </i>AssoSearch</a></li>
						
						<li><a href="phenotype.jsp" id="4" ><i
								class="glyphicon glyphicon-sort-by-attributes "> </i>Phenotype</a></li>
						<li><a href="pathway.jsp"  id="5"><i
								class="glyphicon glyphicon-tasks"> </i>Pathway</a></li>
						<li><a href="summary.do" id="6" ><i
								 class="glyphicon glyphicon-list-alt"></i>CBGAnalysis</a></li>
						
						
						
					</ul>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
</body>

<script src="js/header.js"> </script>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</html>