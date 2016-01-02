function submitGPQuery()
{
	//1.获取输入的MP列表
	var mpList = $("#mpList").val();
	var geneList = $("#geneList").val();

	data = {"mpList":mpList,"geneList":geneList};
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
		        	name: 'breadthfirst',
		        	padding: 5
		        }
	});

}