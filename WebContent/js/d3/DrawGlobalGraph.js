function drawGlobalGraph(str)
{
	//绘制图例
	$("#cy").empty();
	$("#cy").append('<global_graph></global_graph>');
	width=880;
	height = 800;
	var color = d3.scale.category10();
	var force0 = d3.layout.force().charge(-20).linkDistance(40).size([ width, height ]);

	var svg0 = d3.select("global_graph").append("svg").attr("width", width).attr("height", height);

	graph = str;
	force0.nodes(graph.nodes).start();
	force0.links(graph.links).start();
	var link = svg0.selectAll(".link").data(graph.links).enter().append("line")
	.attr("class", "link").style("stroke-width",2).style("stroke",
		function(d)
		{ 
			return color(d.class_id);
		}
	);

	var node = svg0.selectAll(".node").data(graph.nodes).enter().append("circle").attr("class", "node").attr("r",
			function(d){

		switch(d.type)
		{
		case "mp":
			return"4";
		case "gene":
			return"3";
		case "pathway":
			return "5";
		case "ppi":
			return "6";
		}
	}
	).style("fill",
			function(d)
			{
		
		return color(d.group);

			}).call(force0.drag);
	node.append("title").text(function(d) 
			{
		return d.type+":"+d.name ;
			});
	force0.on("tick", function() 
			{
		link.attr("x1", function(d) {
			return d.source.x;
		}).attr("y1", function(d) {
			return d.source.y;
		}).attr("x2", function(d) {
			return d.target.x;
		}).attr("y2", function(d) {
			return d.target.y;
		});

		node.attr("cx", function(d) {
			return d.x;
		}).attr("cy", function(d) {
			return d.y;
		});
	});
}