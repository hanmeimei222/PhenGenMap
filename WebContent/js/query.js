function showAdditionalPanel()
{
	var queryType=$("#queryType").val();
	if(queryType == 'nStepNodes')
	{
		$("#step").attr('class','show');
	}
	else
	{
		$("#step").attr('class','hidden');
	}
}
function showLevelPanel()
{
	if($("#levelCheck").is(':checked'))
	{
		$("#levelPanel").attr('class','show');
	}
	else
	{
		$("#levelPanel").attr('class','hidden');
	}
}

function showQueryPanel()
{
	$("#queryPanel").toggle();
}

function submitQuery()
{
	//1.获取输入的MP列表
	var mpList = $("#mpList").val();
	//2.获取查询条件
	var queryType=$("#queryType").val();
	var step = 0;
	if(queryType == 'nStepNodes')
	{
		step = $("#step").val();
	}
	//3.判断是否过滤层
	var levelFilter = $("#levelCheck").is(':checked');
	var levels ="";
	if(levelFilter)
	{
		levels = $("#levels").val();
	}
	data = {"mpList":mpList,"queryType":queryType,"levels":levels,"step":step};
	$.ajax({
		type : "post",
		data : data,
		url : "pheQuery.do",
		dataType : "json",
		success : function(msg) {
			cytoscapeDraw(msg);
		}
	});
}

$('#singlemp').typeahead(
		{
		    minLength: 2,
		    highlight: true,
		  },
		  {
		    name: 'search-dataset',
		    source: function( query, cb ){
		    	alert(query);
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



