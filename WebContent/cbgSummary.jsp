<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>GlobalGraph</title>

</head>
<body onload='getPhenInfo("MP:0000001",0)'>
	<jsp:include page="header.jsp"></jsp:include>

	<!--services start here-->
	<div class="services">
		<div class="mycontainer">
			<div class="services-main ">

				<div class="row">
					<div class="col-md-10 services-middle">
						<div class="post-content-row">

							<table class="cbgsumtable" border="1">

								<tr style="text-align: center;">
									<th rowspan="2">CBG Patterns</th>
									<th colspan="3">Type 1</th>
									<th colspan="5">Type 2</th>
									<th colspan="7">Type 3</th>
									<th rowspan="2">association</th>
									<th rowspan="2">pheno</th>
								</tr>

								<tr>
									<th>CBG 0:1</th>
									<th>CBG 1:1</th>
									<th>CBG 1:0</th>
									<th>CBG 0:2</th>
									<th>CBG 1:2</th>
									<th>CBG 2:2</th>
									<th>CBG 2:1</th>
									<th>CBG 2:0</th>
									<th>CBG 0:3</th>
									<th>CBG 1:3</th>
									<th>CBG 2:3</th>
									<th>CBG 3:3</th>
									<th>CBG 3:2</th>
									<th>CBG 3:1</th>
									<th>CBG 3:0</th>
								</tr>

								<c:forEach items="${result}" var="res" varStatus="stat">
									<tr style="height: 10px">
										<td style="text-align: center;">${res.node.pheno_name}<br />
											${res.node.pheno_id}
										</td>

										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','0_1')">${res.step1[0]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','1_1')">${res.step1[0]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','1_1')">${res.step1[0]}</a>
										</td>

										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','0_2')">${res.step2[0]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','1_2')">${res.step2[1]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','2_2')">${res.step2[2]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','2_1')">${res.step2[3]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','2_0')">${res.step2[4]}</a>
										</td>

										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','0_3')">${res.step3[0]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','1_3')">${res.step3[1]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','2_3')">${res.step3[2]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','3_3')">${res.step3[3]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','3_2')">${res.step3[4]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','3_1')">${res.step3[5]}</a>
										</td>
										<td><a href="javascript:void(0)"
											onclick="clickData('${res.node.pheno_id }','3_0')">${res.step3[6]}</a>
										</td>
										<td>${res.association }</td>
										<td>${res.pheno }</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</div>

					<div class="col-md-2 services-right">
						<div>
							<div>
								<span style="float: right">Phenotype过滤</span>
							</div>
							<div class="row post-content-row">
								<div class="col-md-12 services-left" id="phen_1_class"></div>
							</div>
						</div>
					</div>
				</div>
				<div id="cy"></div>
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
<script src="js/cbg.js"></script>
<script src="js/cytoscape/cytoscape.min.js"></script>
<script src="js/cytoscape/jquery.cytoscape.js-panzoom.js"></script>

<script
	src="https://cdn.rawgit.com/cpettitt/dagre/v0.7.4/dist/dagre.min.js"></script>
<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-dagre/1.1.2/cytoscape-dagre.js"></script>

</html>