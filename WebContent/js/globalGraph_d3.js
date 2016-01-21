function drawGraph()
{
	$.ajax({
		type : "post",
		data : data,
		url : "queryGlobalAsso.do",
		dataType : "json",
		success : function(msg) {
			
		}
	});
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
	getGlobalAsso();
} 

function getGlobalAsso()
{
	$.ajax({
		type : "get",
		url : "	queryGlobalAsso.do",
		dataType : "json",
		success : function(msg){
			//用d3来画
			drawGlobalGraph(msg);
		}
	});

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
			+info+'\' name="chkGene" value="'+
			val.name+'" />'
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
					+ info+'\' name="chkGene" value="'
					+ child.name+'" onclick="showPathwayLevel(\''
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
				var chkInfo = '<input type="checkbox" name="3_pathway_chk" id='
					+ child.name+' name="chkGene"/>'
					+ child.name+' <br>';
				$("#pathway_name").append(chkInfo);
					});
		}
	}
}