function cytoDrawPathway(data)
{
	
	var cy = cytoscape({
		  container: document.getElementById('cy'),
		  
		  boxSelectionEnabled: false,
		  autounselectify: true,
		  
		  style: cytoscape.stylesheet()
		    .selector('node')
		      .css({
		        'content': 'data(id)'
		      })
		    .selector('.eating')
		      .css({
		        'border-color': 'red'
		      })
		    .selector('.eater')
		      .css({
		        'border-width': 9
		      })
		    .selector('edge')
		      .css({
		        'target-arrow-shape': 'triangle'
		      }),
		  
		  elements:data,
		  
		  layout: {
		    name: 'breadthfirst',
		    directed: true,
		    padding: 10
		  }
		}); // cy init
		  
		cy.on('tap', 'node', function(){
		  var nodes = this;
		  var tapped = nodes;
		  var food = [];
		  
		  nodes.addClass('eater');
		  
		  for(;;){
		    var connectedEdges = nodes.connectedEdges(function(){
		      return !this.target().anySame( nodes );
		    });
		    
		    var connectedNodes = connectedEdges.targets();
		    
		    Array.prototype.push.apply( food, connectedNodes );
		    
		    nodes = connectedNodes;
		    
		    if( nodes.empty() ){ break; }
		  }
		        
		  var delay = 0;
		  var duration = 500;
		  for( var i = food.length - 1; i >= 0; i-- ){ (function(){
		    var thisFood = food[i];
		    var eater = thisFood.connectedEdges(function(){
		      return this.target().same(thisFood);
		    }).source();
		            
		    thisFood.delay( delay, function(){
		      eater.addClass('eating');
		    } ).animate({
		      position: eater.position(),
		      css: {
		        'width': 10,
		        'height': 10,
		        'border-width': 0,
		        'opacity': 0
		      }
		    }, {
		      duration: duration,
		      complete: function(){
		        thisFood.remove();
		      }
		    });
		    
		    delay += duration;
		  })(); } // for
		  
		}); // on tap


	
	
	
//	$('#cy').cytoscape({
//		style: [
//		        {
//		        	selector: 'node',
//		        	css: {
//		        		'content': 'data(id)'
//		        	}
//		        },
//
//		        {
//		        	selector: 'edge',
//		        	css: {
//		        		'target-arrow-shape': 'triangle'
//		        	}
//		        }
//		        ],
//
//		        elements:data
//	});


}