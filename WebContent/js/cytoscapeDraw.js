function cytoscapeDraw(data)
{
	$('#cy').cytoscape({
		layout:{
			name:'dagre'
		},
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

		        elements:data,
		        
	});
	var layout = cy.makeLayout({
		  name: 'random'
		});

		layout.run();

}
