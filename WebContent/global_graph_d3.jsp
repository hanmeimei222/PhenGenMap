<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>


<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/globalGraph_d3.js"></script>
<script src="js/d3/d3.js"></script>
<script src="js/d3/DrawGlobalGraph.js"></script>

<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
</head>
<body onload="initClassInfo()">
	<div class="header-home">
		<div class="fixed-header">
			<div class="top-nav">
				<span class="menu"> </span>
				<ul>
					<li class="active"><a href="index.html">Home</a></li>
					<li><a href="about.html">About</a></li>
					<li><a href="advice.html">Adviceses</a></li>
					<li><a href="blog.html">Blog</a></li>
					<li><a href="contact.html">contact</a></li>
				</ul>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
	<!--banner end here-->
	<!--services start here-->
	<div class="services">
		<div class="mycontainer">
			<div class="services-main ">

				<div class="row">
					<div class="col-md-2 services-left">
						<div>
							<div class="row">
								<span>Pathway过滤 </span>
								<button type="button" style="float: right" onclick="drawGraph()">画图</button>
							</div>
							<div class="row post-content-row">
								<div class="col-md-12 services-left-pathway"
									id="pathway_first_class"></div>
								<div class=" services-left-pathway" id="pathway_second_class"></div>
								<div class="services-left-pathway" id="pathway_name"></div>
							</div>
						</div>
					</div>

					<div class="col-md-8 services-middle">
						<div>
							<h4>显示边的类型</h4>
							<div class="col-md-2">
								<input type="checkbox" id="chkEdge" name="chkAll"
									onchange="selectAllEdge()" />全选
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="e1" name="chkEdgeType" />Gene-PPI
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="e2" name="chkEdgeType" checked />Gene-MP
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="e3" name="chkEdgeType" />Gene-Pathway
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="e4" name="chkEdgeType" />MP-MP
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="e5" name="chkEdgeType" />PPI-PPI
							</div>
						</div>
						<div>
							<h4>查询结果</h4>
							<h5 style="float:right"><a id="download" href="#">点击下载</a></h5>
							<div class="row post-content-row">
								<div id="graph"></div>
							</div>
						</div>
					</div>

					<div class="col-md-2 services-right">
						<div>
							<div class="row">
								<span style="float: right">MP过滤</span>
								<button type="button" onclick="drawGraph()">画图</button>
							</div>
							<div class="row post-content-row">
								<div class="col-md-12 services-left" id="phen_1_class"></div>
							</div>
						</div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<!-- services-main end here -->
		</div>
		<!-- container end here -->
	</div>
	<!-- services end here -->
	<!--footer start here-->
	<div class="footer">
		<div class="container">
			<div class="copyrigyht">
				<p>
					Copyright &copy; 2015.Company name All rights reserved.More
					Templates <a href="http://www.cssmoban.com/" target="_blank"
						title="模板之家">模板之家</a> - Collect from <a
						href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>
				</p>
			</div>
		</div>
	</div>
	<!--footer end here-->
	<!--copy right start here-->
	<!--copy right end here-->
</body>
</html>