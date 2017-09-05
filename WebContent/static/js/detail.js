$(document).ready(function(){
	getDetail();
});

function getDetail(){
	var template = $.ajax({url:'./static/templates/detaillist.html',async:false}).responseText;
	var url = parseUrl(window.location.href);
	$.get('./list?action=get', {id: url.param.id}, function(data){
		var detail = JSON.parse(data)[0];
		console.log(data);
		var html = $("#content");
		$('#item_name').text(detail.name);
		$('#content').html(Mustache.to_html(template, detail));
        $('#detail').html($(detail.detail));
		
		var hash = window.location.hash;
		$('.tab-pane').removeClass("active");
		$('.nav-pills li').removeClass("active");
		if (hash=='') {
			$('#intro_li,#intro').addClass("active");
		}
		else {
			hash=hash.substring(1);
			$('#'+hash+"_li,#"+hash).addClass("active");
		}
		
		$('#rate_'+detail.id).raty({
			starHalf: "./static/images/star-half.png",
			starOn: "./static/images/star-on.png",
			starOff: "./static/images/star-off.png",
			precision: true,
			half: true,
			score: detail.rateval/2,
			click: function (score) {
				$.post('./list?action=rate', {id: url.param.id, rate: score}, function (data) {
					alert("评分成功！");
					getDetail();
				})
			}
		});
		
		var discuss = $.ajax({url:'./static/templates/discuss.html',async:false}).responseText;
		$.get('./list?action=getDiscuss', {id: url.param.id}, function(data) {
			var json = JSON.parse(data);
			console.log(json);
			$('#discuss').html(Mustache.to_html(discuss, json));
			
			
			$.get('./list?action=user', function(data){
				console.log(data);
				if(data=='null'){
					var inputs = $("#discuss textarea");
					inputs.prop("disabled", true);
					$('#rate_'+detail.id).raty('set', {readOnly: true});
					$("#top-nav").html(Mustache.to_html('<li id="login"><a href="login.html?from={{id}}">登录</a></li>', detail));
				}else{
					var inputs = $("#discuss textarea");
					inputs.prop("disabled", false);
					var user = JSON.parse(data);
					$("#top-nav").html(
							Mustache.to_html('<li id="username"><a href="#">{{username}}</a></li><input id="uid" value="{{id}}" type="hidden"> \
							<li> <a class="btn" onclick="logout()"><span class="glyphicon glyphicon-off" ></span></a></li>', user));
				}
			});
			
		});
		
		
		
		
	});
	
}

function comment(id) {
	// get uid here
	var uid =$("#uid").val();
	var url = parseUrl(window.location.href);
	var content = $('#content_'+id).val();
	if(content.length==0) return ;
	if(content.startsWith('@')){
		var str = content.substring(content.indexOf(':')+1).trim();
		if(str.length==0) return ;
	}
	$.post('./list?action=post', {
		uid: uid,
		id: id,
		did: url.param.id,
		content: content
	}, function (data) {
		data = JSON.parse(data);
		console.log(data);
		if (data.code == 0) {
			$('#content_'+id).val("");
			if (id != '') {
				$('#comment_'+id).collapse('show');
				var comment = $.ajax({url:'./static/templates/comment.html',async:false}).responseText;
				$.get('./list?action=getComment', {id: id}, function (data) {
					var detail = JSON.parse(data);
					console.log(detail);
					var size = detail.comments.length;
					console.log(size);
					$('#comment_'+id).html(Mustache.to_html(comment, detail));
					$('#cnt_'+id).text(size);
					$('html,body').animate({scrollTop:$('#content_'+id).offset().top-400},1000);
				});
			}
			else {
				var discuss = $.ajax({url:'./static/templates/discuss.html',async:false}).responseText;
				$.get('./list?action=getDiscuss', {id: url.param.id}, function(data) {
					var detail = JSON.parse(data);
					console.log(detail);
					$('#discuss').html(Mustache.to_html(discuss, detail));
				});
			}
		}
		else alert(data.msg);
	});
}

function openReply(id, name) {	
	$('#reply_'+id).collapse('show');
	var reply = $('#content_'+id);
	if (name != '') {
		reply.val("@ "+name+": ");
	}
	else {reply.val("");}
	$('html,body').animate({scrollTop:reply.offset().top-400},1000)
	reply.focus();
}

function toggleReply(id) {
	var reply = $('#reply_'+id);
	if (reply.hasClass('in')) {
		reply.collapse('hide');
	}
	else {
		openReply(id, '');
	}
}

function logout() {
	var uid =$("#uid").val();
	$.get('./list?action=logout', function(data){
		var inputs = $("#discuss textarea");
		inputs.prop("disabled", true);
		$("#top-nav").empty();
		$("#top-nav").append(Mustache.to_html('<li id="login"><a href="login.html">登录</a></li>'));
	});
}

