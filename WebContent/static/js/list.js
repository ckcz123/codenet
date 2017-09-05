$(document).ready(function(){
		var template = '<div class="panel panel-default">\
	        <div class="panel-heading">{{name}}</div>\
	        <div class="panel-body">\
	          <p>{{shortdetail}} <a href="./detail.html?id={{id}}">details</a> </p>\
	          <p><span class="glyphicon glyphicon-user" aria-hidden="true"></span> 贡献人: {{contributor}}</p>\
	          <p><span class="glyphicon glyphicon-star" aria-hidden="true"></span> 来源: {{reference}}</p>\
	          <p><span class="glyphicon glyphicon-download" aria-hidden="true"></span> {{downloads}}次下载 </p>\
	          <a type="button" class="btn btn-success" href="list?action=dowload&id={{id}}&filename={{filename}}">点击下载</a>\
	          <a type="button" class="btn btn-info" href="detail.html?id={{id}}#discuss">进入讨论</a>\
	        </div>\
	      </div>';
		getDataset(template);
});



function getDataset(template){
	$.get('./list?action=getAll', function(data){
		var datasets = JSON.parse(data);
		console.log(data);
		var html = $("#dataset");
		$.each(datasets, function(i, da){
			console.log(da);
			html.append(Mustache.to_html(template, da));
		});
	});
}