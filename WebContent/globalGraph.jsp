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
						<div class="row post-content-row">
							<div class="col-md-10 services-left">
								<h4 >Pathway过滤 </h4>

							</div>
							<div class="col-md-2 services-left">
								<button type="button" class="btn btn-default"
									style="float: right;margin:10px 0 0 0" onclick="drawGraph()">画图</button>
							</div>
						</div>
						
						<div class="row post-content-row">
								<div class="col-md-12 services-left-pathway" id="pathway_first_class"></div>
								<div class=" services-left-pathway" id="pathway_second_class"></div>
								<div class="services-left-pathway" id="pathway_name"></div>
						</div>

					</div>
					

					<div class="col-md-8 services-middle">
						<div class="middle_chk_panel">
							
							<h4>显示边的类型</h4>
							<div class="row">
							<div class="col-md-1">
							<input type="checkbox" id="chkEdge" name="chkAll"
									onchange="selectAllEdge()" />全选
									</div>
							<div class="col-md-2">							
							<input type="checkbox" id="e1" name="chkEdgeType" />Gene-PPI
									</div>
							<div class="col-md-2">					
							
							<input type="checkbox" id="e2" name="chkEdgeType" checked />Gene-MP
							
									</div>
							<div class="col-md-3">
							<input type="checkbox" id="e3" name="chkEdgeType" />Gene-Pathway
									</div>
							<div class="col-md-2">
							
							<input type="checkbox" id="e4" name="chkEdgeType" />MP-MP
									</div>
							<div class="col-md-2">
							
							<input type="checkbox" id="e5" name="chkEdgeType" />PPI-PPI
							</div>
							</div>
						</div>
						<div class="graphHeader">
							<div style="float: left">
								<h4>查询结果</h4>

							</div>
							<div id="downloadPanel" name="dowloadPanel" class="hidden"
								style="float: right">
								<h5>
									<a id="download" href="#"><button type="button"
											class="btn btn-default">数据下载</button></a>
								</h5>
							</div>
						</div>
						<div>
							<div id="cy"></div>
						</div>
					</div>

					<div class="col-md-2 services-right">
						
						<div class="row post-content-row">
							<div style="float:left;margin:10px 0 0 15px"><button type="button" class="btn btn-default" 
									onclick="drawGraph()">画图</button></div>
						
							<div style="width:150px;float:left"><h4>Phenotype过滤 </h4></div>
							
						</div>
							
						<div id="phenoPanel" class="row post-content-row">
							
						</div>
				<div>
						<img alt="Top_arrow" class="top_arrow" id="top_arrow"
						src="images/top.png">
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
<script type="text/javascript" src="js/back_to_top.js"></script>
<script src="js/globalGraph_d3.js"></script>
<script src="js/d3/d3.v3.min.js"></script>
<script src="js/d3/DrawGlobalGraph.js"></script>

</html>