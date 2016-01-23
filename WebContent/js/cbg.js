function clickData(phen_id,type)
{
	var data ={"id":phen_id,"type":type};
	$.ajax
	({
		url:"detail.do",
		data:data,
		dataType:"json",
		type:"post",
		success:function(result)
		{
			drwcytoCBG(result);
//			$.each(result,function(i,data){
//			drwcytoCBG(data);
//			});
		}
	});
}


var layoutPadding = 5;
var layoutDuration = 500;
function drwcytoCBG(data)
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
			'line-color': function(ele){
				var d = ele.data();
				if(ele.data().edgeType == 'gplink')
				{
					return '#9dbaea';
				}else if(ele.data().edgeType == 'pplink')
				{
					return '#1dbaea';
				}
				
				else if(ele.data().edgeType == 'gpathwaylink')
				{
					return '#fdbaea';
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
			'font-size': 20,
			'text-valign': 'top',
			'text-halign': 'center',
			'background-color':function(ele)
			{
				//gene 的父层节点
				if(ele.data().id == 'gene')
				{
					return '#F7E7E2';
				}
				//phen的父层节点
				if(ele.data().id == 'phen')
				{
					return '#D3D7DD';
				}
				if(ele.data().id == 'pathway')
				{
					return '#90B2DF';
				}
				if(ele.data().id == 'ppi')
				{
					return '#B290fD';
				}
				if (patrn.test(ele.data().id)){
					return colormap[ele.data().id];
					
				}
				else
				{
					return '#00ff00';
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

}
