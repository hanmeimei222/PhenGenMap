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

function drawGraph()
{
	//获取选中的类别
	var pheList = new Array();
	var phe_chks = document.getElementsByName("level1phen_chk");
	for(var i=0;i<phe_chks.length;i++)
	{
		if(phe_chks[i].checked)
		{
			pheList.push(phe_chks[i].id);
		}
	}

	selected_pathway = getSelectedPathway();
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

function getSelectedPathway()
{
	//从最底层开始找，选择最精确的勾选，作为查询输入
	selected_list= new Array();
	var i=3;

	for(;i>0;i--)
	{
		var pathway_chks = document.getElementsByName(i+"_pathway_chk");
		for(var j=0;j<pathway_chks.length;j++)
		{
			if(pathway_chks[j].checked)
			{
				selected_list.push(pathway_chks[j].value);
			}
		}
		if(selected_list.length>0)
		{
			break;
		}
	}
	if(selected_list.length==0)
	{
		i=-1;
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
		initPathwayInfo(msg);
	}
	});

	getPhenInfo(0,"MP:0000001");
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

	$.ajax({
		type : "post",
		data:{"fatherId":mpId},
		url : "initPhenClassInfo.do",
		dataType : "json",
		success : function(msg) 
		{
			showPhenInfo(msg,level);
		}
	});	
}

function showPhenInfo(msg,level)
{
	level = level + 1;
	//当前层及上一层的样式改变的设置
	$("#phen_"+(level-1)+"_class").removeClass().addClass('col-md-1 services-left opacity25');
//	$("#phen_"+(level-1)+"_class").hide();
	$("#phen_"+level+"_class").show();
	$("#phen_"+level+"_class").removeClass().addClass('col-md-10 services-left');

	$("#phen_"+level+"_class").empty();
	var name="level"+level+"phen_chk";	
	var divId ="phen_"+level+"_class";
	//全选
	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel'
		+level+'Phenotype" name="chkLevel'
		+level+'Phenotype" onchange="selectAllLevelPhenotype('
		+level+')" />全选';
	$("#phen_"+level+"_class").append(selectAll);

	$.each(msg,function(key,node)
			{
		$.each(node,function(idx,val){
			var chkInfo = '<input type="checkbox" id=\''
				+val.pheno_id+'\' name='
				+name+' value="'+
				val.pheno_id+'"/>'
				+ val.pheno_name+' <br>';
			$("#"+divId).append(chkInfo);
		});
		$("#"+divId).append('<hr>');
			});
	var next = '<button type="button" class="btn btn-default" onclick="getPhenInfo('
		+level+')">下一层</button>';
	var back = '<button type="button" class="btn btn-default" onclick=" showBackLevel('
		+level+')">返回重选</button>';
	if(level==1){
		$("#phen_1_class").append(next);
	}else{
		$("#phen_"+level+"_class").append(back).append(next);
	}
	
	
	



}
function showBackLevel(level){
	$("#phen_"+level+"_class").hide();
	$("#phen_"+(level-1)+"_class").removeClass().addClass('col-md-12 services-left-pathway');
//	$("#phen_"+(level-1)+"_class").show();
}


function initPathwayInfo(pathwayInfo)
{

	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel1Pathway" name="chkLevel1Pathway" onchange="selectAllLevel1Pathway()" />全选';
	$("#pathway_first_class").append(selectAll);

	$(pathwayInfo.children).each(function(i,val) 
			{
		var info = JSON.stringify(val);
		if(i==0||i==1){
			var chkInfo = '<input type="checkbox" name="1_pathway_chk" id=\''
				+info+'\' value="'+
				val.id+'"checked />'
				+ val.name+' <br>';
		}else{
			var chkInfo = '<input type="checkbox" name="1_pathway_chk" id=\''
				+info+'\' value="'+
				val.id+'" />'
				+ val.name+' <br>';
		}

		$("#pathway_first_class").append(chkInfo);
			});
	var next = '<button type="button" class="btn btn-default" onclick="showSecondLevel()">下一层</button>';
	$("#pathway_first_class").append(next);
}

function showSecondLevel()
{
	$("#pathway_first_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_second_class").show();
	$("#pathway_second_class").removeClass().addClass('col-md-10 services-left');
	var chks = document.getElementsByName("1_pathway_chk");
	$("#pathway_second_class").empty();


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
	var back = '<button type="button" class="btn btn-default" onclick=" showFirstLevelBack()">返回重选</button>';
	var next = '<button type="button" class="btn btn-default" onclick="showPathwayLevel()">下一层</button>';
	$("#pathway_second_class").append(back).append(next);


}


function showPathwayLevel()
{
	$("#pathway_first_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_second_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_name").show();
	$("#pathway_name").removeClass().addClass('col-md-8 services-left');

	$("#pathway_name").empty();
	var selectAll = '<div sytle="float:left"><input type="checkbox" id="chkLevel3Pathway" name="chkLevel3Pathway" onchange="selectAllLevel3Pathway()" />全选';
	$("#pathway_name").append(selectAll);
	var chks = document.getElementsByName("2_pathway_chk");
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
	var back = '<button type="button" class="btn btn-default" onclick=" showSecondLevelBack()">返回重选</button>';
	$("#pathway_name").append(back);
}

function showFirstLevelBack(){
	$("#pathway_second_class").hide();
	$("#pathway_first_class").removeClass().addClass('col-md-12 services-left-pathway');
}

function showSecondLevelBack(){
	$("#pathway_name").hide();
	$("#pathway_second_class").removeClass().addClass('col-md-10 services-left-pathway');
}