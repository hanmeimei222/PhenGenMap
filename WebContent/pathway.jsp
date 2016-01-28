<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">
#mainBubble {
	background: #fff;
	border: solid 1px #ddd;
	box-shadow: 0 0 4px rgba(0, 0, 0, 0);
	font: 10px sans-serif;
	height: 800px;
	position: relative;
	width: 80%;
	
}

#mainBubble svg {
	
	left: 0;
	position: absolute;
	top: 0;
	
}

#mainBubble circle.topBubble {
	fill: #aaa;
	stroke: #666;
	stroke-width: 1.5px;
}

#cy {
	width: 90%;
	height: 400px;
}

.node {
	cursor: pointer;
}

.overlay {
	background-color: #EEE;
}

.node circle {
	fill: #fff;
	stroke: steelblue;
	stroke-width: 1.5px;
}

.node text {
	font-size: 10px;
	font-family: sans-serif;
}

.link {
	fill: none;
	stroke: #06c;
	stroke-width: 1.5px;
}

.templink {
	fill: none;
	stroke: red;
	stroke-width: 3px;
}

.ghostCircle.show {
	display: block;
}

.ghostCircle,.activeDrag .ghostCircle {
	display: none;
}
</style>

<title>Pathway</title>



</head>
<body>

	<jsp:include page="header.jsp"></jsp:include>
	<!--services start here-->
	<div class="services">
		<div class="mycontainer">
			<div class="services-main" id="services-main">

				<div class="col-md-2 services-left">
					<div>
						<h4>查询计划</h4>

						<div class="post-content-row">
							<label class="row post-content-row-title">step1：查看全体pathway信息</label>
							
								<button type="button" onclick="showAllPathway()"
								class="btn btn-default">查看</button>
							
							
							
						</div>

						<div class="post-content-row">
							<label class="row post-content-row-title">step2:请选择查询类型</label>

							<div class="row post-content-row">
								<input type="checkbox" id="chkGene" name="chkGene" />根据Gene查询相关pathway<br>
								<input type="checkbox" id="chkPathway" name="chkPathway" checked/>根据Pathway相关gene<br>
							</div>


							<div class="form-group">
								<button type="button" onclick="showInputPanel()"
									class="btn btn-default">下一步</button>
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
							<div class="form-group hidden" id="pathwayInputPanel">
								<label class="post-content-row-title"> 输入批量Pathway：</label>
								<textarea id="pathwayList" name="pathwayList"
									class="form-control" rows="5">mmu00601
mmu00603</textarea>
							</div>
						</div>



						<div class="post-content-row">
							<label class="row post-content-row-title">step3:提交查询</label>
							<div class="form-group">
								
								<button type="button" onclick="submitPathwayQuery()"
									class="btn btn-default">绘图</button>
								
								
							</div>
						</div>


					</div>
				</div>

				<div class="col-md-8 services-middle">
					<div style="float:left"><h4>查询结果</h4></div>
					
					<div id="downloadPanel1" name="dowloadPanel1" class="hidden" style="float:right">
								<a id="downloadAllPathway" href="#"><button type="button"
								class="btn btn-default">Pathway全体数据下载</button></a>
					</div>
					
					
					
					<div id="tree-container"></div>
					</br>
					<div id="downloadPanel2" name="dowloadPanel2" class="hidden" style="float:right">
								<a id="download" href="#"><button type="button"
								class="btn btn-default">数据下载</button></a>
					</div>
					<div id="cy"></div>
					</br>
					<div id="downloadPanel3" name="dowloadPanel3" class="hidden" style="float:right">
								<a id="downloadSimData" href="#"><button type="button"
								class="btn btn-default">数据下载</button></a>
					</div>
					<div id="bubble"></div>

				</div>
				
				<div class="col-md-2 services-right">
					<div class="row post-content-row">
						<h4>图内检索</h4>
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

<script src="js/d3/d3.v3.min.js"></script>

<script src="js/typeahead.bundle.js"></script>
<script src="js/handlebars.min.js"></script>

<script src="js/cytoscape/cytoscape.min.js"></script>

<script src="js/pathwayquery.js"></script>
<script src="js/cytoscapeDraw.js"></script>
<script src="js/d3/DrawPathway.js"></script>
<script src="js/d3/bubble.js"></script>
<script
	src="https://cdn.rawgit.com/cpettitt/dagre/v0.7.4/dist/dagre.min.js"></script>
<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-dagre/1.1.2/cytoscape-dagre.js"></script>

</html>