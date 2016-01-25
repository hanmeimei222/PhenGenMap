<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>GlobalAsso</title>



</head>
<body onload="initClassInfo()">
	
	<jsp:include page="header.jsp"></jsp:include>
	
	<!--services start here-->
	<div class="services">
		<div class="mycontainer">
			<div class="services-main ">

				<div class="row">
					<div class="col-md-2 services-left">
						<div>
							<div>
								<span>Pathway过滤 </span>
								
								
								
								<button type="button" class="btn btn-default" style="float: right" onclick="drawGraph()">画图</button>
							
							
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
						<div class="row middle_chk_panel">
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
						<div style="float:left">
							<h3>查询结果</h3>
							</div>
							<div id="downloadPanel" name="dowloadPanel" class="hidden" style="float:right">
							<h5 ><a id="download" href="#"><button type="button" class="btn btn-default">数据下载</button></a></h5>
							</div>
							
							<div class="row post-content-row">
								<div id="cy"></div>
							</div>
						</div>
					</div>

					<div class="col-md-2 services-right">
						<div>
							<div>
								<span style="float: right">Phenotype过滤</span>
								<button type="button" class="btn btn-default" onclick="drawGraph()">画图</button>
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
	<jsp:include page="footer.jsp"></jsp:include>
	<!--footer end here-->
</body>
<script src="js/globalGraph_d3.js"></script>
<script src="js/d3/d3.v3.min.js"></script>
<script src="js/d3/DrawGlobalGraph.js"></script>
</html>