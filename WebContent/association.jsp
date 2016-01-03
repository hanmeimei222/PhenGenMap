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
	height: 400px;
}
</style>
</head>
<body>
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
			<div class="services-main">
				<div class="col-md-2 services-left">
					<div>
						<h3>查询计划</h3>
						<div class="row post-content-row">
							<label class="post-content-row-title">请选择要输入的节点类型</label>
							<div class="row post-content-row">
								<input type="checkbox" id="chkGene" name="chkGene" />Gene<br> 
								<input type="checkbox" id="chkMp" name="chkMp" />MPO<br> 
								<input 	type="checkbox" id="chkPathway" name="chkPathway" />Pathway<br>
							</div>
							<div>
							<div class="form-group">
								<button type="button" onclick="showInputPanel()"
									class="btn btn-default">下一步</button>
							</div>
							</div>
						</div>
						<div class="row post-content-row">
							<div class="form-group hidden" id="geneInputPanel">
								<label class="post-content-row-title"> 输入批量Gene：</label>
								<textarea id="geneList" name="geneList" class="form-control"
									rows="5">EDNRA
GLI3
PAX3
ADA</textarea>
							</div>
							<div class="form-group hidden" id="mpInputPanel">
								<label class="post-content-row-title"> 输入批量MP：</label>
								<textarea id="mpList" name="mpList" class="form-control"
									rows="5">MP:0002098
MP:0004576
MP:0008160
MP:0008161
MP:0000729
MP:0004206
MP:0001379
MP:0003415
</textarea>
							</div>

							<div class="form-group hidden" id="pathwayInputPanel">
								<label class="post-content-row-title"> 输入批量Pathway：</label>
								<textarea id="pathwayList" name="pathwayList"
									class="form-control" rows="5"></textarea>
							</div>
						</div>
						<div class="row post-content-row">
							<label class="post-content-row-title">提交查询</label>
							<div class="form-group">
								<button type="button" onclick="submitGPQuery()"
									class="btn btn-default">绘图</button>
							</div>
						</div>
						<div class="row post-content-row">
							<label class="post-content-row-title"> step5:图内检索</label>
							<div class="form-group">
								<button type="button" onclick="showQueryPanel()"
									class="btn btn-default">显示图内检索>></button>
							</div>
						</div>
						<div class="row post-content-row ">
							<div id="queryPanel" style="display: none;">
								<button id="reset" name="reset" class="btn btn-default">重置</button>
								<div id="search-wrapper">
									<input type="text" class="form-control" id="search" autofocus
										placeholder="Search In graph">
								</div>
								<div id="info"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="col-md-8 services-right">
			<h3>查询结果</h3>
			<div>
				<div id="cy"></div>
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