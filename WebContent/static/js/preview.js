$(document).ready(function(){
	getPreview();
});

function getPreview(){
	var template = $.ajax({url:'./static/templates/detaillist.html',async:false}).responseText;
	var url = parseUrl(window.location.href);
	$.get('./list?action=getPreview', {id: url.param.id}, function(data){
		if (data.code!=0) {
			alert(data.msg);
			return;
		}
		var detail = data.data;
        detail.rateval = 6.8;
        detail.rateuser = 37;
		console.log(detail);

		$('#title').text(detail.title);
		$('#content').html(Mustache.to_html(template, detail));
        $('#detail').html(detail.content);
        $('#intro').addClass("active");
		
		$('#rate_'+detail.id).raty({
			starHalf: "./static/images/star-half.png",
			starOn: "./static/images/star-on.png",
			starOff: "./static/images/star-off.png",
			precision: true,
			half: true,
			score: 3.4,
			readOnly: true
		});

	}, "json");
	
}