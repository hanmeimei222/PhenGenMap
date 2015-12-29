function draw(action)
{

	var data={};
	if(action == 'getNStepNeighbor')
	{
		id = $("#mp_id").val();
		step = $("#step_num").val();
		data = {"mpoId":id,"step":step};

		
	}
	if(action == 'getPostInLevels')
	{
		id = $("#root_id").val();
		levels = $("#levels").val();
		data = {"mpoId":id,"levels":levels};
	}
	$.ajax({
		type : "post",
		data : data,
		url : action+".do",
		dataType : "json",
		success : function(msg) {
			cytoscapeDraw(msg);
		}
	});
}	
	
function cytoscapeDraw(data)
{
	
	$('#cy').cytoscape({
		style: [
		        {
		        	selector: 'node',
		        	css: {
		        		'content': 'data(id)'
		        	}
		        },

		        {
		        	selector: 'edge',
		        	css: {
		        		'target-arrow-shape': 'triangle'
		        	}
		        }
		        ],

		        elements:data
	});
	$('#cy').cytoscapePanzoom({
		// options go here
	});

}
