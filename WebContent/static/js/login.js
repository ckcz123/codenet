$(document).ready(function() {
    $('#username').keypress(function(e) {
        if (e.keyCode==13)
            $('#password').focus();
    });
    $('#password').keypress(function(e) {
        if (e.keyCode==13)
            login();
    });
});

function loginGithub(){
	var uri = 'login/github/oauth';
	var from = parseUrl(window.location.href).param.from;
	window.location.href='login/github/oauth?from='+from;
}


function login() {
	
	var err = $('#alert');
	err.hide();
	
	var user = $('#username').val();
	var pass = $('#password').val();
	
	if (pass == '') {
		err.text("密码为空！");
		err.show();
		$('#password').focus();
		return;
	}
	if (user == '') {
		err.text("账号为空！");
		err.show();
		$('#username').focus();
		return;
	}
	pass = md5(pass);
	$.post('./list?action=login', {
		user: user, pass: pass
	}, function (data) {
		var detail=JSON.parse(data);
		console.log(detail);
		
		if (detail.code == 0) {
			alert("登录成功！");
			// window.location="index.html";
			var url = parseUrl(window.location.href);
			var from=url.param.from;
			if (typeof(from)=="number") {
				window.location="detail.html?id="+from;
			}
			else if (typeof(from)=="string") {
				window.location=from;
			}
			else {
				window.location="index.html";
			}
		}
		else {
			err.text(detail.msg);
			err.show();
		}
	});
	
	
	
	
}

