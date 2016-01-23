function selectAllEdge()
{
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

	getPhenInfo("MP:0000001",0);
} 

function getPhenInfo(mpId,level)
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
	$("#phen_"+level+"_class").empty();
	var name="level"+level+"phen_chk";	
	var divId ="phen_"+level+"_class";
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
}

function initPathwayInfo(pathwayInfo)
{
	$(pathwayInfo.children).each(function(i,val) 
			{
		var info = JSON.stringify(val);
		var chkInfo = '<input type="checkbox" onclick="showSecondLevel()" name="1_pathway_chk" id=\''
			+info+'\' value="'+
			val.id+'" />'
			+ val.name+' <br>';
		$("#pathway_first_class").append(chkInfo);
			});
}

function showSecondLevel()
{
	$("#pathway_first_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_second_class").removeClass().addClass('col-md-10 services-left');
	var chks = document.getElementsByName("1_pathway_chk");
	$("#pathway_second_class").empty();
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
					+ child.id+'" onclick="showPathwayLevel(\''
					+child.name+'\')"/>'
					+ child.name+' <br>';
				$("#pathway_second_class").append(chkInfo);
					});
			$("#pathway_second_class").append('<hr>');
		}

	}

}

function showPathwayLevel()
{
	$("#pathway_first_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_second_class").removeClass().addClass('col-md-1 services-left opacity25');
	$("#pathway_name").removeClass().addClass('col-md-8 services-left');

	$("#pathway_name").empty();
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
		}
	}
}