/**
 * Created by oc on 2017/9/5.
 */

$(document).ready(function(){
    getuser("my.html", getMyList());
});

function getMyList() {
    $.get('./list?action=mylist', function (data) {
        console.log(data);
        if (data.code!=0) {
            console.log(data);
            return;
        }
        var datasets=data.data;
        for (var i in datasets) {
            $('#mylist').append(Mustache.to_html(
                "<tr><td>{{id}}</td><td><a href='./list?action=download&id={{id}}&filename={{filename}}' target='_blank'>{{filename}}</a></td>" +
                "<td>{{size}}</td><td>{{downloads}}</td><td>" +
                "<a class='btn btn-default' href='detail.html?id={{id}}' target='_blank'>详细信息</a><button class='btn btn-danger' style='margin-left: 10px' onclick='del({{id}})'>删除</button>" +
                "</td></tr>",
                datasets[i]
            ));
        }
    }, "json");
}

function del(id){
    $.post('./list?action=delete', {id: id}, function (data) {
        if (data.code!=0) {
            alert(data.msg);
        }
        else {
            alert("删除成功！");
            window.location.reload();
        }
    }, "json");
}

function upload(){
    console.log("upload")
    $.ajax({
        url: '/list?action=upload',
        type: 'POST',
        cache: false,
        data: new FormData($('#upload-file-form')[0]),
        processData: false,
        contentType: false
    }).done(function(res) {
        console.log("success");
    }).fail(function(res) {
        console.log("fail");
    });
}

