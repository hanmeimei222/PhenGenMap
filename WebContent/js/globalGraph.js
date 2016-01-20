var phenData;
function initClassInfo()
{
	$.ajax({
		type : "get",
		url : "initPathwayClassInfo.do",
		dataType : "json",
		success : function(msg) {
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
		$("#"+divId).append('<h4>'+key+'</h4>');
		$.each(node,function(idx,val){
			var chkInfo = '<input type="checkbox" id=\''
				+val.pheno_id+'\' name='
				+name+' value="'+
				val.pheno_id+'" onclick="getPhenInfo(\''+
				val.pheno_id+'\','
				+level+')"/>'
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
		var chkInfo = '<input type="checkbox" name="first_class_chk" id=\''
			+info+'\' name="chkGene" value="'+
			val.name+'" onclick="showSecondLevel()"/>'
			+ val.name+' <br>';
		$("#pathway_first_class").append(chkInfo);
			});
}

function showSecondLevel()
{
	var chks = document.getElementsByName("first_class_chk");
	$("#pathway_second_class").empty();
	for(var i=0;i<chks.length;i++)
	{
		if(chks[i].checked)
		{
			data = JSON.parse(chks[i].id);
			$("#pathway_second_class").append('<h4>'+chks[i].value +'</h4>');
			$(data.children).each(function(i,child)
					{
				var info = JSON.stringify(child);
				var chkInfo = '<input type="checkbox" name="second_class_chk"  id=\''
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
	$("#pathway_name").empty();
	var chks = document.getElementsByName("second_class_chk");
	for(var i=0;i<chks.length;i++)
	{
		if(chks[i].checked)
		{
			data = JSON.parse(chks[i].id);
			$("#pathway_name").append('<h4>'+chks[i].value +'</h4>');
			$(data.children).each(function(i,child)
					{
				var chkInfo = '<input type="checkbox" name="second_class_chk" id='
					+ child.name+' name="chkGene"/>'
					+ child.name+' <br>';
				$("#pathway_name").append(chkInfo);
					});
		}
	}
}