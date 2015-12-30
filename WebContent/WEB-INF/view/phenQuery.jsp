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
								step1:����Ҫ��ѯ��MP�б���һ��һ��</label>

							<div class="form-group">
								<textarea id="mpList" name="mpList" class="form-control" rows="3"></textarea>
							</div>
						</div>
						<div class="row post-content-row">
							<label class="post-content-row-title"> step2:ѡ��Ҫ��ѯ������</label> 
							<select
								id="queryType" name="queryType" class="form-control" onchange="showAdditionalPanel()">
								<option value="single">��ѯMP��Ϣ</option>
								<option value="postNodes">��ѯMP�����̽ڵ���Ϣ</option>
								<option value="preNodes">��ѯ��MP����ǰ���ڵ���Ϣ</option>
								<option value="nStepNodes">��ѯ�Ը�MPΪ����n���ɴ�Ľڵ�</option>
								<option value="path">��ѯ�ڵ��Ŀɴ�·��</option>
							</select>
							<div class="form-group">
							<input  type="number" id="step" name="step" class="hidden" placeholder="����ڵ�ɴﲽ��"/>
							</div>
						</div>


						<div class="row post-content-row">
						<label class="post-content-row-title"> step3:���ù��˲㼶</label>
							<div class="form-group">
								<label class="mycheckbox" onchange="showLevelPanel()"> 
								<input  type="checkbox" id="levelCheck" name="levelCheck">���ýڵ����ڲ㼶
								</label>
							</div>
							<div id="levelPanel" class="hidden">
							<div class="form-group">
								<input type="text" id="levels" name="levels" class="form-control" placeholder="�ı�Ҫ���˵Ĳ㣬�ֺŷָ�">
							</div>
							</div>
						</div>

						<div class="row post-content-row">
							<label class="post-content-row-title"> step4:�ύ��ѯ</label>

							<div class="form-group">
								<button type="button" onclick="submitQuery()" class="btn btn-default">��ͼ</button>
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
						title="ģ��֮��">ģ��֮��</a> - Collect from <a
						href="http://www.cssmoban.com/" title="��ҳģ��" target="_blank">��ҳģ��</a>
				</p>
			</div>
		</div>
	</div>
	<!--footer end here-->
	<!--copy right start here-->
	<!--copy right end here-->
</body>
</html>