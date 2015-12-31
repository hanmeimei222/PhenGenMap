function cytoscapeDraw(data)
{

	var cy = window.cy = cytoscape({
		container: document.getElementById('cy'),
		layout: { name: 'dagre' },
		style:[
		       {
		    	   selector: 'node',
		    	   css: {
		    		   'content': 'data(id)',
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
		    	   selector: 'edge',
		    	   css: {

		    		   'target-arrow-shape': 'triangle',
		    		   'target-arrow-color':'#9dbaea',
		    		   'width':3,
		    		   'line-color':'#9dbaea'
		    	   }
		       }
		       ],
		       elements: data,
		       motionBlur: true,
		       selectionType: 'single',
		       boxSelectionEnabled: true
	});
	
	 cy.on('select', 'node', function(e){
	      var node = this;
	      highlight( node );
	    });

	 cy.on('unselect', 'node', function(e){
	    });
	
//
//	$('#cy').cytoscape({
//		layout:{
//			name:'dagre'
//		},
//		style: [
//		        {
//		        	selector: 'node',
//		        	css: {
//		        		'content': 'data(id)',
//		        		'background-color': function(ele){
//		        			if(ele.data().queryInput)
//		        			{
//		        				return '#771';
//		        			}
//		        			else
//		        			{
//		        				return '#888';
//		        			}
//		        		}
//		        	}
//		        },
//		        {
//		        	selector: 'edge',
//		        	css: {
//
//		        		'target-arrow-shape': 'triangle',
//		        		'target-arrow-color':'#9dbaea',
//		        		'width':3,
//		        		'line-color':'#9dbaea'
//		        	}
//		        }
//		        ],
//
//		        elements:data,
//
//	});

}

var layoutPadding = 50;
var layoutDuration = 500;
function highlight( node ){
    var nhood = node.closedNeighborhood();

    cy.batch(function(){
      cy.elements().not( nhood ).removeClass('highlighted').addClass('faded');
      nhood.removeClass('faded').addClass('highlighted');
      
      var npos = node.position();
      var w = window.innerWidth;
      var h = window.innerHeight;
      
      cy.stop().animate({
        fit: {
          eles: cy.elements(),
          padding: layoutPadding
        }
      }, {
        duration: layoutDuration
      }).delay( layoutDuration, function(){
        nhood.layout({
          name: 'concentric',
          padding: layoutPadding,
          animate: true,
          animationDuration: layoutDuration,
          boundingBox: {
            x1: npos.x - w/2,
            x2: npos.x + w/2,
            y1: npos.y - w/2,
            y2: npos.y + w/2
          },
          fit: true,
          concentric: function( n ){
            if( node.id() === n.id() ){
              return 2;
            } else {
              return 1;
            }
          },
          levelWidth: function(){
            return 1;
          }
        });
      } );
      
    });
  }