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

function getuser(from, callback) {
    $.get('./list?action=user', function(data){
        console.log(data);
        if(data=='null'){
            $("#top-nav").html('<li id="login"><a href="login.html?from='+((typeof(from)=="number"||typeof(from)=="string")?from:"")+'">登录</a></li>');
        }else{
            var user = JSON.parse(data);
            $("#top-nav").html(
                Mustache.to_html('<li id="username"><a href="my.html">{{username}}</a></li><input id="uid" value="{{id}}" type="hidden"> \
							<li> <a class="btn" onclick="logout()"><span class="glyphicon glyphicon-off" ></span></a></li>', user));
			(callback && typeof(callback)=="function") && callback();
        }
    });
}

function logout() {
    $.get('./list?action=logout', function(data){
        var inputs = $("#discuss textarea");
        inputs.prop("disabled", true);
        $("#top-nav").empty();
        $("#top-nav").append(Mustache.to_html('<li id="login"><a href="login.html">登录</a></li>'));
    });
}