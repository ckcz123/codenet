$(document).ready(function(){
	var template = '<div class="tab-pane active" id="intro">\
                    <p>{{detail}}</p>\
                </div>\
                <div class="tab-pane" id="discuss"> </div>\
                <div class="tab-pane" id="download">\
				<a type="button" class="btn btn-success" href="list?action=dowload&id={{id}}&filename={{filename}}">点击下载</a>\
                </div>';
	getDetail(template);
});

function getDetail(template){
	var url = parseUrl(window.location.href);
	$.get('./list?action=get', {id: url.param.id}, function(data){
		var detail = JSON.parse(data)[0];
		console.log(data);
		var html = $("#content");
		html.append(Mustache.to_html(template, detail));
	});
}