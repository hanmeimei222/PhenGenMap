
var layoutPadding = 5;
var layoutDuration = 500;

//展示搜素提示的模板
var infoTemplate = Handlebars.compile([
                                       '<p class="ac-id">{{id}}</p>',
                                       '<p class="ac-name">{{name}}</p>',
                                       '<p class="ac-more"><i class="fa fa-external-link"></i> <a target="_blank" href="http://www.informatics.jax.org/searches/Phat.cgi?id={{id}}">More information</a></p>'
                                       ].join(''));

var patrn=/^\d*$/;
//生成一组随机颜色
var colormap=["#4fc5c7","#97ec71","#dbf977","#ed9dd6","#fa6e86","#dbf977","#4fc5c7","#fa6e86","#dbf977","#97ec71","#ed9dd6","#fa6e86","#dbf977","#4fc5c7","#97ec71","#dbf977","#ed9dd6","#fa6e86"];

//for(var i=0;i<20;i++)
//{
//	//colormap[i]=getRandomColor();
//}
function getRandomColor() 
{ 
	var c = '#'; 
	var cArray = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F']; 
	for(var i = 0; i < 6;i++) 
	{ 
		var cIndex = Math.round(Math.random()*15); 
		c += cArray[cIndex]; 
	} 
	return c; 
} 

function cytoscapeDraw(data)
{
	var cy = window.cy = cytoscape({
		container: document.getElementById('cy'),
		layout: { 
			name: 'dagre',
//			name:'grid',
			directed:true,
			padding:layoutPadding
		},
		style: cytoscape.stylesheet()
		.selector('node')
		.css({
			'content': 'data(id)',
			'font-size':80,
			'width':80,
			'height':80,
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
			'width':6,
			'line-color': function(ele){
				var d = ele.data();
				if(ele.data().edgeType == 'gplink')
				{
					return '#800080';
				}else if(ele.data().edgeType == 'pplink')
				{
					return '#1dbaea';
				}
				else if(ele.data().edgeType == 'gpathwaylink')
				{
					return '#800';
				}
				else if(ele.data().edgeType == 'gppilink')
				{
					return '#aebd9c';
				}
				else if(ele.data().edgeType == 'ppi2ppilink')
				{
					return '#baaedf';
				}
				else
				{
					return "#00ff00";
				}
			}
		})
		.selector('$node > node')
		.css({
			'padding-top': '10px',
			'padding-left': '20px',
			'padding-bottom': '20px',
			'padding-right': '20px',
			'font-size': 80,
			'text-valign': 'top',
			'text-halign': 'center',
			'background-color':function(ele)
			{
				//gene 的父层节点
				if(ele.data().id == 'gene')
				{
					return '#f7a6b5';
				}
				//phen的父层节点
				if(ele.data().id == 'phen')
				{
					return '#dee5f3';
				}
				if(ele.data().id == 'pathway')
				{
					return '#fff586';
				}
				if(ele.data().id == 'ppi')
				{
					return '#e8c698';
				}
				if (patrn.test(ele.data().id)){
					return colormap[ele.data().id];
					
				}
				else
				{
					return '#93e887';
				}
			}
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
		})
		.selector('.filtered')
		.css({
			'display': 'none'
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

	//filter
	$('#filters').on('click', 'input', function(){

		var mp = $('#MP').is(':checked');
		var gene = $('#GENE').is(':checked');
		var pathway = $('#PATHWAY').is(':checked');
		var ppi = $('#PPI').is(':checked');

		cy.batch(function(){

			cy.nodes().forEach(function( n ){
				var cType = n.data('nodeType');

				n.removeClass('filtered');

				var filter = function(){
					n.addClass('filtered');
				};

				if( 
						(cType === 'mp' && !mp)
						|| (cType === 'gene' && !gene)
						|| (cType === 'pathway' && !pathway)
						|| (cType === 'ppi' && !ppi)
				){
					filter();
				}
			});

		});

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

