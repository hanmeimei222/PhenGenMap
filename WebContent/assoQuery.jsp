<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>AssoQuery</title>
<style>

</style>
</head>
<body onload="changeActive('3');">
	<jsp:include page="header.jsp"></jsp:include>
	
	<!--services start here-->
	<div class="services">
		<div class="mycontainer">
			<div class="services-main">
				<div class="col-md-2 services-left">
					<div>
						<h4>查询计划</h4>
						<div class="row post-content-row">
							<label class="post-content-row-title">请选择要输入的节点类型</label>
							<div class="post-content-row">
								<input type="checkbox" id="chkGene" name="chkGene" checked/>Gene<br>
								<input type="checkbox" id="chkMp" name="chkMp" checked />MPO<br> <input
									type="checkbox" id="chkPathway" name="chkPathway" checked />Pathway<br>
								<input type="checkbox" id="chkPPI" name="chkPPI" />PPI<br>

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
									rows="5">VHL
ITGB3
DKC1
EPHA2
APC
MLH1
ACADVL
A
TSC2
STK11
CDKN2A
ERCC6
DGKD
PRKAR1A
MSH2
KIT
EPHA2
MSH2
LEP
ERCC2
NBN
TRP53
APC
ERCC2
STK11
DDB2
CYBB
TSC1
TSC1
CDKN2A
ATP2A2
IL10
BRCA2
APC
PLG
KRAS
POLH
IFNG
NBN
CYBB
BLM
BLM
PTCH1
KRAS
BRCA2
TRP53
BRAF
ERCC2
ATM
ATP2A2
ERCC2
STK11
TRP53
APC
FHIT
TLR4
FHIT
PTEN
NEIL1
APC
BUB1B
MEN1
LEPR
IFNG
PTCH1
CDKN2A</textarea>
							</div>
							<div class="form-group hidden" id="mpInputPanel">
								<label class="post-content-row-title"> 输入批量MP：</label>
								<textarea id="mpList" name="mpList" class="form-control"
									rows="5">MP:0010282
MP:0010283
MP:0020189
MP:0020188
MP:0002037
MP:0002052
MP:0002053
MP:0001273
MP:0002051
MP:0002013
MP:0010308
MP:0001272
MP:0013151
MP:0013152
MP:0010347
MP:0009828
MP:0010273
MP:0002732
MP:0003447
MP:0010274
MP:0009469
MP:0004208
MP:0010322
MP:0010300
MP:0004502
MP:0009704
MP:0003721
MP:0004499
MP:0013488
MP:0002020
MP:0002021
</textarea>
							</div>

							<div class="form-group hidden" id="pathwayInputPanel">
								<label class="post-content-row-title"> 输入批量Pathway：</label>
								<textarea id="pathwayList" name="pathwayList"
									class="form-control" rows="5">mmu05217</textarea>
							</div>
							<div class="form-group hidden" id="ppiInputPanel">
								<label class="post-content-row-title"> 输入批量PPI：</label>
								<textarea id="ppiList" name="ppiList"
									class="form-control" rows="5">4088
22059</textarea>
							</div>
						</div>
						<div class="row post-content-row">
							<label class="post-content-row-title">提交查询</label>
							<div class="form-group">
								<button type="button" onclick="submitGPQuery()"
									class="btn btn-default">绘图</button>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-8 services-middle">
					<div style="float:left">
					<h4>查询结果</h4>
					</div>
					<div id="downloadPanel" name="dowloadPanel" class="hidden" style="float:right">
								<a id="download" href="#"><button type="button"
								class="btn btn-default">数据下载</button></a>
					</div>
					<div id="cy"></div>
					
				</div>
				<div class="col-md-2 services-right">
					<div class=" post-content-row ">
						<div id="filters">
							<h4>节点类型</h4> <input id="MP" type="checkbox"
								checked></input><label for="soft">MP</label><br /> <input
								id="GENE" type="checkbox" checked></input><label for="semi-soft">GENE</label><br />
							<input id="PATHWAY" type="checkbox" checked></input><label
								for="na">PATHWAY</label><br />
								<input id="PPI" type="checkbox" checked></input><label
								for="na">PPI</label><br />
							<hr />
						</div>
					</div>
					<div class=" post-content-row">
						<h4> 图内检索</h4>
						<div id="queryPanel">
							<div id="search-wrapper">
								<input type="text" class="form-control" id="search" autofocus
									placeholder="Search In graph">
									<button id="reset" name="reset" class="btn btn-default">重置</button>
							</div>
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

<script src="js/typeahead.bundle.js"></script>
<script src="js/handlebars.min.js"></script>

<script src="js/cytoscape/cytoscape.min.js"></script>
<script src="js/cytoscapeDraw.js"></script>
<script src="js/gpquery.js"></script>

<script
	src="https://cdn.rawgit.com/cpettitt/dagre/v0.7.4/dist/dagre.min.js"></script>
<script
	src="https://cdn.rawgit.com/cytoscape/cytoscape.js-dagre/1.1.2/cytoscape-dagre.js"></script>

</html>