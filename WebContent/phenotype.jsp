<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<title>Home</title>
</head>
<body>
	<jsp:include page="header.jsp"></jsp:include>

	<!--services start here-->
	<div class="services">
		<div class="mycontainer">
			<div class="services-main">
				<div class="col-md-2 services-left">
					<div>
						<h3>查询计划</h3>
						<div class="row post-content-row">
							<label class="post-content-row-title"> step1:输入要查询的MP列表</label>
							<div class="form-group">
								<label class="post-content-row-title"> 查询单个MP：</label>
								<div id="search-wrapper">
									<input type="text" class="form-control" id="singlemp" autofocus
										placeholder="图内搜索">
								</div>
							</div>
							<div class="form-group">
								<label class="post-content-row-title"> 输入批量MP：</label>
								<textarea id="mpList" name="mpList" class="form-control"
									rows="3">MP:0008345
MP:0008347</textarea>
							</div>
						</div>
						<div class="row post-content-row">
							<label class="post-content-row-title"> step2:选择要查询的类型</label> <select
								id="queryType" name="queryType" class="form-control"
								onchange="showAdditionalPanel()">
								<option value="single">查询MP信息</option>
								<option value="postNodes">查询MP及其后继节点信息</option>
								<option value="preNodes">查询该MP及其前驱节点信息</option>
								<option value="nStepNodes">查询以该MP为中心n步可达的节点</option>
								<option value="path">查询节点间的可达路径</option>
							</select>
							<div class="form-group">
								<input type="number" id="step" name="step" class="hidden"
									placeholder="输入节点可达步数" />
							</div>
						</div>

						<div class="row">
							<label class="post-content-row-title"> step3:设置过滤层级</label>
							<div class="form-group">
								<label class="mycheckbox" onchange="showLevelPanel()"> <input
									type="checkbox" id="levelCheck" name="levelCheck">设置节点所在层级
								</label>
							</div>
							<div id="levelPanel" class="hidden">
								<div class="form-group">
									<input type="text" id="levels" name="levels"
										class="form-control" placeholder="文本要过滤的层，分号分隔">
								</div>
							</div>
						</div>

						<div class="row post-content-row">
							<label class="post-content-row-title"> step4:提交查询</label>
							<div class="form-group">
								<button type="button" onclick="submitQuery()"
									class="btn btn-default">绘图</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 services-middle">
					<h3>查询结果</h3>
					<h5  style="float:right"><a id="download" href="#">点击下载</a></h5>
					<div>
						<div id="cy"></div>
					</div>
				</div>

				<div class="col-md-2 services-right">
					<div class="row post-content-row">
						<h3>图内检索</h3>
					</div>
					<div class="row post-content-row ">
						<div id="queryPanel">

							<div id="search-wrapper">
								<input type="text" class="form-control" id="search" autofocus
									placeholder="Search In graph">
							</div>
							<button id="reset" name="reset" class="btn btn-default">重置</button>
							<div id="info"></div>
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
	<jsp:include page="footer.jsp"></jsp:include>
	<!--footer end here-->
</body>

<script src="js/cytoscape/cytoscape.min.js"></script>
<script src="js/cytoscape/jquery.cytoscape.js-panzoom.js"></script>

<script src="js/typeahead.bundle.js"></script>
<script src="js/handlebars.min.js"></script>
<script src="js/cytoscapeDraw.js"></script>
<script src="js/query.js"></script>

<script
	src="https://cdn.rawgit.com/cpettitt/dagre/v0.7.4/dist/dagre.min.js"></script>
<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-dagre/1.1.2/cytoscape-dagre.js"></script>

<script type="text/javascript">
	//展示搜素提示的模板
	var globalQueryTemplate = Handlebars.compile([
	                                       '<p class="autoComplete">{{pheno_id}}-{{pheno_name}}</p>'
	                              		].join(''));
	
	var remote_cities = new Bloodhound({
	    datumTokenizer: Bloodhound.tokenizers.obj.whitespace('name'),
	    queryTokenizer: Bloodhound.tokenizers.whitespace,
	    // 在文本框输入字符时才发起请求
	    remote: 'autoComplete?query=%QUERY'
	});

	remote_cities.initialize();

	$('#singlemp').typeahead(null, {
	    name: 'mp',
	    displayKey: 'pheno_id',
	    source: remote_cities.ttAdapter(),
	    templates: {
		      suggestion: globalQueryTemplate
		}
	});
	</script>
</html>
