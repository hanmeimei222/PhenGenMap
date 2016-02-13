<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Home</title>
</head>
<body onload="initDataVersion()">
	<jsp:include page="header.jsp"></jsp:include>

	
	
	<div class="content-welcome">
			
				<div class="col-md-8 come">
					<div class=" welcome">
						<h3>Welcome </h3>
						<h5 class="for">BioData Miner是一个对生物信息进行收集、更新、检索、加工、可视化的用于启发验证实验的数据分析工具。
						它能够对多种类数据从不同粒度、不同层次进行检索并在不同范围进行组合查询检索，方便用户进行灵活的交互查询。
				它将多种类型的原始数据进行收集和更新并进行适当的数据预处理，通过相应的逻辑处理，
				将重复的、大批量的、不易于理解的数据、中间结果、最终结果以恰当的方式可视化出来，
				提升实验的分析能力、降低重复实验中的可能错误。</h5>
				
					</div>
				</div>
				<div class="col-md-4 red">
				<h3>DataResource</h3>
				<div id="dataVersionDiv">
					<label>选择数据源版本--</label>
					<label>当前版本:</label><label id="curVersion"></label>
					<div style="width:270px;float:left;margin:0 20px 0 0">
					<select id="dataVersion" name="dataVersion" class="form-control"></select>
					</div>
					<div style="float:left">
					<button class="btn btn-default" type="button" onclick="cutVersion()">切换</button></div>
				</div>
				
				<label>数据源下载</label>
				<ul id="downloadPanel">
					
				</ul>
				</div>
				<div class="clearfix"> </div>
	</div>
	
	
	
	<div class="content-top">
			<div class="top-content">
				<h1>Services</h1>
				<p>A list for services which BioMiner could supply</p>
			</div>
			<div class="content-top1">
			
			 <div class=" col-md-4 grid-top">
				
				<a href="index.jsp"> <i class="glyphicon glyphicon-download-alt"></i>
				  <div class="top-grid">
					<h3>DataResource Collection</h3>
					<p>多种原始数据收集</p>
					<p>自动更新系统数据</p>
					<p>数据源的多历史版本选择</p>
					<p>多种原始数据下载</p>	
					
				  </div>
				</a>
			</div>
			<div class=" col-md-4 grid-top">
				
				  <a href="globalGraph.jsp"><i class="glyphicon glyphicon-globe"></i>
				  <div class="top-grid">
					<h3>Global Association</h3>
					<p>生物信息全局异构网络概览</p>
					<p>表型和基因多维度综合网络研究</p>
					<p>数据类型筛选及过滤</p>
					<p>分析结果下载</p>	
				 </div>
				</a>
			</div>
			
			 <div class=" col-md-4 grid-top">
				
				  <a href="assoQuery.jsp"><i class="glyphicon glyphicon-zoom-in"></i>
				  <div class="top-grid">
					<h3>MutiAssociation Research</h3>
					<p>基于用户输入的多数据集融合节点及关联可达性分析</p>
					<p>数据类型筛选及过滤</p>
					<p>分析结果下载</p>	
				 </div>
				</a>
				
			</div>
			
			
			
			<div class="clearfix"> </div>
		</div>
		<div class="content-top1">
			
			<div class=" col-md-4 grid-top">
				<a href="phenotype.jsp"> <i class="glyphicon glyphicon-sort-by-attributes "></i>
				  <div class="top-grid">
					<h3>Phenotype Research</h3>
					<p>老鼠表型结构概览</p>
					<p>表型数据层次研究</p>
					<p>表型数据可达性分析</p>
					<p>分析结果下载</p>	
					 </div>
				</a>
			</div>
			<div class=" col-md-4 grid-top">
				
				 <a href="pathway.jsp"><i class="glyphicon glyphicon-tasks"></i>
				  <div class="top-grid">
					<h3>Pathway Research</h3>
					<p>Pathway整体关系概览</p>
					<p>Pathway与Gene关系研究</p>
					<p>Pathway相似性分析</p>
					<p>分析结果下载</p>	
				 </div>
				</a>
				
			</div>
			
			<div class=" col-md-4 grid-top">
				
				 <a href="summary.do"><i class="glyphicon glyphicon-list-alt"></i>
				  <div class="top-grid">
					<h3>CBG Analysis</h3>
					<p>生物信息网络中CBG模式分类及统计</p>
					<p>CBG模式详情可视化展示及分析</p>
				
				 </div>
				</a>
			</div>
		
			<div class="clearfix"> </div>

		</div>
	</div>
	
		
	<jsp:include page="footer.jsp"></jsp:include>
	
	
</body>

<script type="text/javascript" src="js/index.js"></script>

</html>