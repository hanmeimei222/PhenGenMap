function showInputPanel(){
	
	var queryType = $("#queryType").val();
	
	if(queryType == 'inputMP')
	{
		$("#mpInputPanel").attr('class','show');
		$("#geneInputPanel").attr('class','hidden');
	}
	if(queryType == 'inputGene')
	{
		$("#mpInputPanel").attr('class','hidden');
		$("#geneInputPanel").attr('class','show');
	}
	if(queryType == 'inputBoth')
	{
		$("#mpInputPanel").attr('class','show');
		$("#geneInputPanel").attr('class','show');	
	}
}

function submitGPQuery()
{
	var showMPA = $("#showMPA").is(":checked");
	var queryType = $("#queryType").val();
	var mpList="";
	var geneList="";
	if(queryType == 'inputBoth'||queryType == 'inputMP' )
	{
		mpList = $("#mpList").val();
	}
	if(queryType == 'inputBoth'|| queryType == 'inputGene')
	{
		geneList= $("#geneList").val();
	}

	data = {"mpList":mpList,"geneList":geneList,"showMPA":showMPA};
	$.ajax({
		type : "post",
		data : data,
		url : "queryGPA.do",
		dataType : "json",
		success : function(msg) {
			drawGPA(msg);
		}
	});

}
//数字
var patrn=/^\d*$/; 

function drawGPA(data){

	var cy = cytoscape({
		container: document.getElementById('gpcy'),

		boxSelectionEnabled: false,
		autounselectify: true,

		style: [
		        {
		        	selector: 'node',
		        	css: {
		        		'content': 'data(id)',
		        		'font-size': 20,
		        		'text-valign': 'center',
		        		'text-halign': 'center',
		        		'background-color': function(ele){
		        			
		    				if(ele.data().queryInput)
		    				{
		    					return '#771';
		    				}
		    				else
		    				{
		    					return '#888';
		    				}
		    			}
		        	}
		        },
		        {
		        	selector: '$node > node',
		        	css: {
		        		'padding-top': '10px',
		        		'padding-left': '20px',
		        		'padding-bottom': '20px',
		        		'padding-right': '20px',
		        		'font-size': 20,
		        		'text-valign': 'top',
		        		'text-halign': 'center',
		        		'background-color':function(ele)
		        		{
		        			//gene 的父层节点
		        			if(ele.data().id == 'gene')
		        			{
		        				return '#8FB8E6';
		        			}
		        			//phen的父层节点
		        			if(ele.data().id == 'phen')
		        			{
		        				return '#7C8389';
		        			}
		        			if (patrn.test(ele.data().id)){
		        				return '#EAC922';
		        			}
		        		}
		        	}
		        },
		        {
		        	selector: 'edge',
		        	css: {
		        		'target-arrow-shape': 'triangle',
		    			'target-arrow-color':'#9dbaea',
		    			'width':3,
		    			'line-color':'#9dbaea'
		        	}
		        },
		        {
		        	selector: ':selected',
		        	css: {
		        		'background-color': 'black',
		        		'line-color': 'black',
		        		'target-arrow-color': 'black',
		        		'source-arrow-color': 'black'
		        	}
		        }
		        ],

		        elements: data,

		        layout: {
		        	name: 'dagre',
		        	padding: 5
		        }
	});

}