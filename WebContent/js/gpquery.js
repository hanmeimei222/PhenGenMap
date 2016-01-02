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
		        		'text-valign': 'center',
		        		'text-halign': 'center'
		        	}
		        },
		        {
		        	selector: '$node > node',
		        	css: {
		        		'padding-top': '10px',
		        		'padding-left': '10px',
		        		'padding-bottom': '10px',
		        		'padding-right': '10px',
		        		'text-valign': 'top',
		        		'text-halign': 'center',
		        		'background-color': '#bbb'
		        	}
		        },
		        {
		        	selector: 'edge',
		        	css: {
		        		'target-arrow-shape': 'triangle'
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