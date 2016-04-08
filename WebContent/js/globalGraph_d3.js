//选择显示的边的种类
function selectAllEdge()
{
	//边的类型全选
	var chk = document.getElementById("chkEdge");
	var subchk = document.getElementsByName("chkEdgeType");
	if(chk.checked)
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = true;
		}
	}
	else
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = false;
		}
	}
}

function getselectType()
{
	var type = [];

	var subchk = document.getElementsByName("chkEdgeType");

	for(var i=0;i<subchk.length;i++)
	{
		if(subchk[i].checked)
		{
			type.push(1);
		}
		else
		{
			type.push(0);
		}
	}
	return type.join("");
}

//Pathway-Level1的全选
function selectAllLevel1Pathway()
{
	var chk = document.getElementById("chkLevel1Pathway");
	var subchk = document.getElementsByName("1_pathway_chk");
	if(chk.checked)
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = true;
		}
	}
	else
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = false;
		}
	}
}

//Pathway-Level2的全选
function selectAllLevel2Pathway()
{
	var chk = document.getElementById("chkLevel2Pathway");
	var subchk = document.getElementsByName("2_pathway_chk");
	if(chk.checked)
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = true;
		}
	}
	else
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = false;
		}
	}
}

//Pathway-Level3的全选
function selectAllLevel3Pathway()
{
	var chk = document.getElementById("chkLevel3Pathway");
	var subchk = document.getElementsByName("3_pathway_chk");
	if(chk.checked)
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = true;
		}
	}
	else
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = false;
		}
	}
}

//Phenotype-Level的全选
function selectAllLevelPhenotype(level)
{
	//根据不同层动态拼接每一层的全选id以及下属各checkbox的Name
	var chkallbylevel = "chkLevel"+level+"Phenotype";
	var subchkbylevel = "level"+level+"phen_chk";

	var chk = document.getElementById(chkallbylevel);
	var subchk = document.getElementsByName(subchkbylevel);

	if(chk.checked)
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = true;
		}
	}
	else
	{
		for(var i=0;i<subchk.length;i++)
		{
			subchk[i].checked = false;
		}
	}
}
var curShowLevel=1;
function drawGraph()
{
	//获取选中的类别
	var pheList = new Array();
	var phe_chks = document.getElementsByName("level"+curShowLevel+"phen_chk");
	for(var i=0;i<phe_chks.length;i++)
	{
		if(phe_chks[i].checked)
		{
			pheList.push(phe_chks[i].id);
		}
	}

	selected_pathway = getSelectedPathway();

	if(pheList.length==0 && selected_pathway.level==-1)
	{
		alert("请勾选Phenotype或者pathway");
		return -1;
	}

	selected_type = getselectType();
	data = {"phenList":pheList.join("\t"),"pathwayList":selected_pathway.pathwayList,"level":selected_pathway.level,"selected_type":selected_type};
	$.ajax({
		type : "post",
		data : data,
		url : "queryGlobalAsso.do",
		dataType : "json",
		success : function(msg) {
		if(msg!=""){
			$("#downloadPanel").attr('class','show');
		}else
		{
			$("#downloadPanel").attr('class','hidden');
		}
		drawGlobalGraph(msg.data);

		if(msg.path!="")
		{
			$("#download").attr("href",msg.path);
		}
	}
	});
}
var curPathwayLevel=1;
function getSelectedPathway()
{
	//从最底层开始找，选择最精确的勾选，作为查询输入
	selected_list= new Array();

	var pathway_chks = document.getElementsByName(curPathwayLevel+"_pathway_chk");
	for(var j=0;j<pathway_chks.length;j++)
	{
		if(pathway_chks[j].checked)
		{
			selected_list.push(pathway_chks[j].value);
		}
	}

	if(selected_list.length==0)
	{
		i=-1;
	}
	else
	{
		i = curPathwayLevel;
	}
	return {"pathwayList":selected_list.join("\t"),"level":i};

}

function initClassInfo()
{
	$.ajax({
		type : "get",
		url : "initPathwayClassInfo.do",
		dataType : "json",
		success : function(msg){
//		initPathwayInfo(msg);
		initPathwayTree(msg);
	}
	});

	getPhenInfo(0,"MP:0000001");
	changeActive('2');
} 

function getPhenInfo(level,mpId)
{
	var chks = document.getElementsByName("level"+level+"phen_chk");
	if(level>0)
	{
		mpId="";
		for(var i=0;i<chks.length;i++)
		{
			if(chks[i].checked)
			{
				mpId+=chks[i].id;
				mpId+='\t';
			}
		}
	}
	if(mpId=="")
	{
		alert("请勾选当前层phenotype");
		return -1;
	}
	$.ajax({
		type : "post",
		data:{"fatherId":mpId},
		url : "initPhenClassInfo.do",
		dataType : "json",
		success : function(msg) 
		{
			if(msg!="" && msg!=null)
			{
				showPhenInfo(msg,level);
			}
		}
	});	
}

//参数的level是父亲level
function showPhenInfo(msg,level)
{
	level = level + 1;
	//创建一个新的div
	var curLevelId = 'phen_'+level+'_class';
	var levelDiv='<div class="col-md-10 services-right" id="'+curLevelId+'">';
	$("#phenoPanel").append(levelDiv);
	//把上一层div要隐藏
	$("#phen_"+(level-1)+"_class").removeClass().addClass('col-md-1 services-right opacity25');
	if(level>2)
	{
		$("#phen_"+(level-2)+"_class").hide();
	}

	var name="level"+level+"phen_chk";	

	var next = '<button type="button" class="btn btn-default" onclick="getPhenInfo('
		+level+')">下一层</button>';
	var back = '<button type="button" class="btn btn-default" onclick=" showBackLevel('
		+level+')">上一层</button>';

	$("#"+curLevelId).append("<div>");
	if(level==1){
		$("#"+curLevelId).append(next);
	}else{
		$("#"+curLevelId).append(back).append(next);
	}
	$("#"+curLevelId).append("</div>");
	$("#"+curLevelId).append('<div>当前所在层数：'+level+'</div>');
	//全选
	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel'
		+level+'Phenotype" name="chkLevel'
		+level+'Phenotype" onchange="selectAllLevelPhenotype('
		+level+')" />全选';
	$("#"+curLevelId).append(selectAll);


	$.each(msg,function(key,node)
			{
		if(level>1){
			$("#"+curLevelId).append('<h5>'+key+'</h5>');
		}
		$.each(node,function(idx,val){
			var chkInfo="";
			if(idx==26){
				chkInfo = '<input type="checkbox" id=\''
					+val.pheno_id+'\' name='
					+name+' value="'+
					val.pheno_id+'"checked/>'
					+ val.pheno_name+' <br>';
			}else{
				chkInfo = '<input type="checkbox" id=\''
					+val.pheno_id+'\' name='
					+name+' value="'+
					val.pheno_id+'"/>'
					+ val.pheno_name+' <br>';
			}

			$("#"+curLevelId).append(chkInfo);
		});
		$("#"+curLevelId).append('<hr>');
			});

	curShowLevel = level;
}
function showBackLevel(level){
	$("#phen_"+level+"_class").remove();

	$("#phen_"+(level-1)+"_class").removeClass().addClass('col-md-10 services-right');
	if(level>2)
	{
		$("#phen_"+(level-2)+"_class").show();
	}
	curShowLevel = level-1;
}


function initPathwayTree(pathwayInfo){
	var o = { showcheck: true
			//onnodeclick:function(item){alert(item.text);},        
	};
//	o.data = treedata; 
	o.data = pathwayInfo;
// console.log(JSON.stringify(pathwayInfo)); 
	
	$("#tree").treeview(o);            
	$("#showchecked").click(function(e){
		var s=$("#tree").getCheckedNodes();
		if(s !=null&&s.length>0)
			alert(s.join(","));
		else
			alert("NULL");
	});


}


function initPathwayInfo(pathwayInfo)
{
	var next = '<div style="float:right" ><button type="button" class="btn btn-default" onclick="showSecondLevel()">下一层</button></div><br/>';
	$("#pathway_first_class").append(next);
	$("#pathway_first_class").append('<div>当前所在层数：1</div>');
	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel1Pathway" name="chkLevel1Pathway" onchange="selectAllLevel1Pathway()" />全选';
	$("#pathway_first_class").append(selectAll);

	$(pathwayInfo.children).each(function(i,val) 
			{
		var info = JSON.stringify(val);
		var chkInfo="";
		if(i==0||i==1){
			chkInfo = '<input type="checkbox" name="1_pathway_chk" id=\''
				+info+'\' value="'+
				val.id+'"checked />'
				+ val.name+' <br>';
		}else{
			chkInfo = '<input type="checkbox" name="1_pathway_chk" id=\''
				+info+'\' value="'+
				val.id+'" />'
				+ val.name+' <br>';
		}

		$("#pathway_first_class").append(chkInfo);
			});
	curPathwayLevel =1;

}

function showSecondLevel()
{

	var chks = document.getElementsByName("1_pathway_chk");
	var flag = false;
	for(var i=0;i<chks.length;i++)
	{
		flag |=chks[i].checked;
	}
	if(!flag)
	{
		alert("请勾选当前层的pathway");
		return;
	}

	curPathwayLevel = 2;
	$("#pathway_first_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_second_class").show();
	$("#pathway_second_class").removeClass().addClass('col-md-10 services-left');

	$("#pathway_second_class").empty();

	var back = '<button type="button" class="btn btn-default" onclick=" showFirstLevelBack()">上一层</button>';
	var next = '<button type="button" class="btn btn-default" onclick="showPathwayLevel()">下一层</button>';
	$("#pathway_second_class").append(back).append(next);
	$("#pathway_second_class").append('<div>当前所在层数：2</div>');

	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel2Pathway" name="chkLevel2Pathway" onchange="selectAllLevel2Pathway()" />全选';
	$("#pathway_second_class").append(selectAll);

	for(var i=0;i<chks.length;i++)
	{
		if(chks[i].checked)
		{
			data = JSON.parse(chks[i].id);
			$("#pathway_second_class").append('<h5>'+chks[i].value +'</h5>');
			$(data.children).each(function(i,child)
					{
				var info = JSON.stringify(child);
				var chkInfo = '<input type="checkbox" name="2_pathway_chk"  id=\''
					+ info+'\' value="'
					+ child.id+'" />'
					+ child.name+' <br>';
				$("#pathway_second_class").append(chkInfo);
					});
			$("#pathway_second_class").append('<hr>');
		}

	}


}


function showPathwayLevel()
{
	var chks = document.getElementsByName("2_pathway_chk");
	var flag = false;
	for(var i=0;i<chks.length;i++)
	{
		flag |=chks[i].checked;
	}
	if(!flag)
	{
		alert("请勾选当前层的pathway");
		return;
	}


	curPathwayLevel = 3;
	$("#pathway_first_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_second_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_name").show();
	$("#pathway_name").removeClass().addClass('col-md-8 services-left');

	$("#pathway_name").empty();

	var back = '<button type="button" class="btn btn-default" onclick=" showSecondLevelBack()">上一层</button>';
	$("#pathway_name").append(back);
	$("#pathway_name").append('<div>当前所在层数：3</div>');

	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel3Pathway" name="chkLevel3Pathway" onchange="selectAllLevel3Pathway()" />全选';
	$("#pathway_name").append(selectAll);

	for(var i=0;i<chks.length;i++)
	{
		if(chks[i].checked)
		{
			data = JSON.parse(chks[i].id);
			$("#pathway_name").append('<h5>'+chks[i].value +'</h5>');
			$(data.children).each(function(i,child)
					{
				var chkInfo = '<input type="checkbox" name="3_pathway_chk" value='
					+ child.id+' />'
					+ child.name+' <br>';
				$("#pathway_name").append(chkInfo);
					});
			$("#pathway_name").append('<hr>');
		}
	}

}

function showFirstLevelBack(){
	curPathwayLevel =1;
	$("#pathway_second_class").hide();
	$("#pathway_first_class").removeClass().addClass('col-md-12 services-left-pathway');
}

function showSecondLevelBack(){
	curPathwayLevel =2;
	$("#pathway_name").hide();
	$("#pathway_second_class").removeClass().addClass('col-md-10 services-left-pathway');
}