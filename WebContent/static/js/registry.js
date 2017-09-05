
function registry() {
	
	var err = $('#alert');
	err.hide();
	
	var user = $('#username').val();
	var pass = $('#password').val();
	var pass2 = $('#password2').val();
	var email = $('#email').val();
	console.log("user="+user+", pass="+pass+", pass2="+pass2+", email="+email);
	if (pass == '' || pass != pass2) {
		err.text("密码为空或两次密码不一致！");
		err.show();
		$('#pass').focus();
		return;
	}
	if (pass.length<6) {
		err.text("密码太短！");
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
	if (email == '') {
		err.text("邮箱为空！");
		err.show();
		$('#email').focus();
		return;
	}
	
	pass = md5(pass);
	
	$.post('./list?action=registry', {
		user: user, pass: pass, email: email
	}, function (data) {
		var detail=JSON.parse(data);
		console.log(detail);
		
		if (detail.code == 0) {
			alert("注册成功！");
			window.location="login.html";
		}
		else {
			err.text(detail.msg);
			err.show();
		}
	});
	
	
	
}
