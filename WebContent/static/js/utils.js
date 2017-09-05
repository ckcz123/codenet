function parseUrl(url){
	var parser = document.createElement('a');
	parser.href = url;
	var _param = parser.search.substring(1).split('&');
	console.log(_param);
	var param={};
	$.each(_param, function(i, pa){
//		console.log(pa);
		var key = pa.substring(0, pa.indexOf('='));
		var value = pa.substring(pa.indexOf('=')+1);
		param[key]=value;
	});
	parser.param=param;
	return parser;
}