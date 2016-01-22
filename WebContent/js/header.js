function changeActive(tab)
{
	for(var i=1;i<7;i++)
	{
		$("#"+i).removeClass();
	}
	$("#"+tab).addClass("active no-bar");
	return true;
}