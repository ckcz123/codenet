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

    $.get('./list?action=user', function(data){
        console.log(data);
        if(data=='null'){
            $("#top-nav").html(Mustache.to_html('<li id="login"><a href="login.html?from={{id}}">登录</a></li>', detail));
        }else{
            var user = JSON.parse(data);
            $("#top-nav").html(
                Mustache.to_html('<li id="username"><a href="#">{{username}}</a></li><input id="uid" value="{{id}}" type="hidden"> \
							<li> <a class="btn" onclick="logout()"><span class="glyphicon glyphicon-off" ></span></a></li>', user));
        }
    });

}




