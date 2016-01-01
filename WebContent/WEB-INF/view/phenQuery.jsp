<!DOCTYPE HTML>
<html>
<head>
<title>Home</title>

<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script src="js/cytoscape/cytoscape.js"></script>
<script src="js/cytoscape/jquery.cytoscape.js-panzoom.js"></script>
<script src="js/cytoscapeDraw.js"></script>
<script src="js/query.js"></script>

<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />


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
					<h3>Query</h3>
					<div>
						<div class="row post-content-row">
							<label class="post-content-row-title">
								step1:输入要查询的MP列表，一行一个</label>

							<div class="form-group">
								<textarea id="mpList" name="mpList" class="form-control" rows="3"></textarea>
							</div>
						</div>
						<div class="row post-content-row">
							<label class="post-content-row-title"> step2:选择要查询的类型</label> 
							<select
								id="queryType" name="queryType" class="form-control" onchange="showAdditionalPanel()">
								<option value="single">查询MP信息</option>
								<option value="postNodes">查询MP及其后继节点信息</option>
								<option value="preNodes">查询该MP及其前驱节点信息</option>
								<option value="nStepNodes">查询以该MP为中心n步可达的节点</option>
								<option value="path">查询节点间的可达路径</option>
							</select>
							<div class="form-group">
							<input  type="number" id="step" name="step" class="hidden" placeholder="输入节点可达步数"/>
							</div>
						</div>


						<div class="row post-content-row">
						<label class="post-content-row-title"> step3:设置过滤层级</label>
							<div class="form-group">
								<label class="mycheckbox" onchange="showLevelPanel()"> 
								<input  type="checkbox" id="levelCheck" name="levelCheck">设置节点所在层级
								</label>
							</div>
							<div id="levelPanel" class="hidden">
							<div class="form-group">
								<input type="text" id="levels" name="levels" class="form-control" placeholder="文本要过滤的层，分号分隔">
							</div>
							</div>
						</div>

						<div class="row post-content-row">
							<label class="post-content-row-title"> step4:提交查询</label>

							<div class="form-group">
								<button type="button" onclick="submitQuery()" class="btn btn-default">绘图</button>
							</div>
						</div>

					</div>

				</div>
				<div class="col-md-10 services-right">
					<h3>Result</h3>
					<div>
						<div id="cy"></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<!--services end here-->
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