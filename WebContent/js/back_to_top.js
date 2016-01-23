	$("#top_arrow").hide();
	$(function () {
		$(window).scroll(function(){
			if ($(window).scrollTop()>100){
				$("#top_arrow").fadeIn(400);
			}
			else
			{
				$("#top_arrow").fadeOut(400);
			}
		});

		$("#top_arrow").click(function(){
			$('body,html').animate({scrollTop:0},400);
			return false;
		});

		$("#top_arrow").mouseover(function()
		{
			$("#top_arrow").css('opacity', 1);  
		});


		$("#top_arrow").mouseout(function()
		{
			$("#top_arrow").css('opacity', 0.4);   
		});
	});
