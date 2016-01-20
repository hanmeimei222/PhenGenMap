<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home</title>


<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script src="js/typeahead.bundle.js"></script>
<script src="js/handlebars.min.js"></script>


<script src="js/cytoscape/cytoscape.min.js"></script>

<script src="js/cytoscapeDraw.js"></script>
<script src="js/gpquery.js"></script>

<script src="js/globalGraph.js"></script>

<script
	src="https://cdn.rawgit.com/cpettitt/dagre/v0.7.4/dist/dagre.min.js"></script>
<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-dagre/1.1.2/cytoscape-dagre.js"></script>


<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

<style>
body {
	font-family: helvetica;
	font-size: 14px;
}

#cy {
	width: 90%;
	height: 600px;
}
</style>
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
					<div class="col-md-6 services-left">
						<div>
						<div class="row">
							<h3>Pathway过滤</h3>
							</div>
							<div class="row post-content-row">
								<div class="col-md-4 services-left" id="pathway_first_class">
								</div>
								<div class="col-md-4 services-left" id="pathway_second_class">
								</div>
								<div class="col-md-4 service-left" id="pathway_name"></div>
							</div>
						</div>
					</div>

					<div class="col-md-6 services-right">
						<div>
							<div class="row">
								<h3 style="float: right">MPO过滤</h3>
							</div>
							<div class="row post-content-row">
								<div class="col-md-4 service-left" id="phen_3_class"></div>
								<div class="col-md-4 services-left" id="phen_2_class"></div>
								<div class="col-md-4 services-left" id="phen_1_class"></div>

							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2 services-left"></div>
					<div class="col-md-8 services-middle">
						<div class="row">
							<div class="col-md-3">
								<input type="checkbox" id="chkGene" name="chkGene" />Gene
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="chkMp" name="chkMp" />MPO
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="chkPathway" name="chkPathway" />Pathway
							</div>
							<div class="col-md-2">
								<input type="checkbox" id="chkPPI" name="chkPPI" />PPI
							</div>
						</div>
					</div>
					<div class="col-md-2 services-right">
					<button type="button" onclick="drawGraph()">画图</button>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12 services-left">
						<div>
							<h3>查询结果</h3>
							<div class="row post-content-row">
								<div>
									<div id="cy"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-2 services-left"></div>
					<div class="col-md-8 services-middle">
						<div class="row">
							<h3>图内检索</h3>
							<div class="col-md-3"></div>
						</div>
					</div>
					<div class="col-md-2 services-right"></div>
				</div>
				<div class="row">
					<h3>统计信息</h3>
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