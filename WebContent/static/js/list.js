$(document).ready(function(){
		getDataset();
});



function getDataset(){
	var template = $.ajax({url:'./static/templates/list.html',async:false}).responseText;
	$.get('./list?action=getAll', function(data){
		var datasets = JSON.parse(data);
		console.log(data);
		var html = $("#dataset");
		$.each(datasets, function(i, da){
			console.log(da);
			html.append(Mustache.to_html(template, da));
            $('#rate_'+da.id).raty({
                starHalf: "./static/images/star-half.png",
                starOn: "./static/images/star-on.png",
                starOff: "./static/images/star-off.png",
                precision: true,
                half: true,
                score: da.rateval/2,
				readOnly: true
            });
		});
	});

    getuser("list.html");

}




