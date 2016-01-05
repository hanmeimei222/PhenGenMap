<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>hello</title>
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script src="js/d3/d3.v3.min.js"></script>




<script src="js/pathwayquery.js"></script>
<script src="js/cytoscapeDrawPathway.js"></script>

<link href="css/bootstrap.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />

<style type="text/css">
  
	.node {
    cursor: pointer;
  }

  .overlay{
      background-color:#EEE;
  }
   
  .node circle {
    fill: #fff;
    stroke: steelblue;
    stroke-width: 1.5px;
  }
   
  .node text {
    font-size:10px; 
    font-family:sans-serif;
  }
   
  .link {
    fill: none;
    stroke: #ccc;
    stroke-width: 1.5px;
  }

  .templink {
    fill: none;
    stroke: red;
    stroke-width: 3px;
  }

  .ghostCircle.show{
      display:block;
  }

  .ghostCircle, .activeDrag .ghostCircle{
       display: none;
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

<div class="col-md-2 services-left">
<div>
<h3>查询计划</h3>
<label class="post-content-row-title">step1：输入要查询的pathway</label>



<div class="form-group"><textarea id="pwayList" name="pwayList"	class="form-control" rows="3">mmu00601
mmu00603</textarea></div>



<label class="post-content-row-title"> step2:选择要查询的类型</label> <select
								id="queryType" name="queryType" class="form-control"
								onchange="showAdditionalPanel()">
								<option value="allpathways">查询全体pathway信息</option>
								<option value="sglways">查询特定pathway信息</option>
								
							</select>
							
<button type="button" onclick="submitPathwayQuery()"
									class="btn btn-default">查询</button>
</div>
</div>

<div class="col-md-8 services-right">
<h3>查询结果</h3>
<div id="tree-container"></div>
</div>

</body>
</html>