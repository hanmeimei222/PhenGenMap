
var layoutPadding = 5;
var layoutDuration = 500;

//展示搜素提示的模板
var infoTemplate = Handlebars.compile([
                                       '<p class="ac-id">{{id}}</p>',
                                       '<p class="ac-name">{{name}}</p>',
                                       '<p class="ac-more"><i class="fa fa-external-link"></i> <a target="_blank" href="http://www.informatics.jax.org/searches/Phat.cgi?id={{id}}">More information</a></p>'
                                       ].join(''));

function cytoscapeDraw(data)
{
	var cy = window.cy = cytoscape({
		container: document.getElementById('cy'),
		layout: { 
			name: 'dagre',
			directed:true,
			padding:layoutPadding
		},
		style: cytoscape.stylesheet()
		.selector('node')
		.css({
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
		})
		.selector('edge')
		.css({
			'target-arrow-shape': 'triangle',
			'target-arrow-color':'#9dbaea',
			'width':3,
			'line-color':'#9dbaea'
		})
		.selector('.highlighted')
		.css({
			'min-zoomed-font-size': 1,
			'font-size': 20,
			'z-index': 9999
		})
		.selector('.faded')
		.css({
			'opacity': 0.5,
			'text-opacity': 0
		}),

		elements: data,
		motionBlur: true,
		selectionType: 'single',
		boxSelectionEnabled: true
	});

	cy.on('select', 'node', function(e){
		var node = this;
		highlight( node );
		showNodeInfo( node );
	});

	cy.on('unselect', 'node', function(e){
		clear();
		 hideNodeInfo();
	});
	$('#reset').on('click', function(){
		cy.animate({
			fit: {
				eles: cy.elements(),
				padding: layoutPadding
			},
			duration: layoutDuration
		});
	});
	
	$('#search').typeahead(
	{
	    minLength: 2,
	    highlight: true,
	  },
	  {
	    name: 'search-dataset',
	    source: function( query, cb ){
	      function matches( str, q ){
	        str = (str || '').toLowerCase();
	        q = (q || '').toLowerCase();
	        
	        return str.match( q );
	      }
	      
	      var fields = ['id', 'name'];

	      function anyFieldMatches( n ){
	        for( var i = 0; i < fields.length; i++ ){
	          var f = fields[i];
	          
	          if( matches( n.data(f), query ) ){
	            return true;
	          }
	        }
	        return false;
	      }
	      
	      function getData(n){
	        var data = n.data();
	        
	        return data;
	      }
	      
	      function sortByName(n1, n2){
	        if( n1.data('id') < n2.data('id') ){
	          return -1;
	        } else if( n1.data('id') > n2.data('id') ){
	          return 1;
	        }
	        return 0;
	      }
	      var res =  cy.nodes().stdFilter( anyFieldMatches ).sort( sortByName ).map( getData );
	      cb( res );
	    },
	    templates: {
	      suggestion: infoTemplate
	    }
	  }).on('typeahead:selected', function(e, entry, dataset){
	    var n = cy.getElementById(entry.id);
	    n.select();
	    showNodeInfo( n );
	  });
}

function highlight( node ){

	var nhood = node.closedNeighborhood();


	cy.batch(function(){
		cy.elements().not( nhood ).removeClass('highlighted').addClass('faded');
		nhood.removeClass('faded').addClass('highlighted');

		var npos = node.position();
		var w = 300;
		var h = 300;

		cy.stop().animate({
			fit: {
				eles: cy.elements(),
				padding: layoutPadding
			}
		}, {
			duration: layoutDuration
		}).delay( layoutDuration, function(){
			nhood.layout({
				name: 'dagre',
				directed:true,
				padding: layoutPadding,          
				animate: true,
				animationDuration: layoutDuration,
				boundingBox: {
					x1: npos.x - w/2,
					x2: npos.x + w/2,
					y1: npos.y - h/2,
					y2: npos.y + h/2
				},
				fit: true,
			});
		} );

	});
	
}



function showNodeInfo( node ){
	$('#info').html( infoTemplate( node.data() ) ).show();
}

function hideNodeInfo(){
	$('#info').hide();
}

/*
 * 当点击空白处时，恢复节点原始状态
 * */
function clear(){
	cy.batch(function(){
		cy.elements().removeClass('highlighted').removeClass('faded');
	});
}

