/**
 * Created by oc on 2017/9/5.
 */
$(document).ready(function(){
    getuser("my.html");
    $('#detail').markdown({
        resize: "vertical",
        hiddenButtons: "cmdPreview"
    })

});

function upload() {
    $('#file').click();
    return false;
}

function getFileName(path) {
    var pos1 = path.lastIndexOf('/');
    var pos2 = path.lastIndexOf('\\');
    var pos = Math.max(pos1, pos2);
    if (pos < 0) {
        return path;
    }
    else {
        return path.substring(pos + 1);
    }
}

function fileSelected() {
    var file=$('#file').val();
    if (file) {
        var filename=getFileName(file);
        $.post('./list?action=checkUploadFilename', {filename: filename}, function (data) {
            if (data.code!=0) {
                alert(data.msg);
                $('#file').val("");
            }
            else {
                doUpLoad();
            }
        }, "json");
    }
}

function doUpLoad() {
    console.log("Start uploading......");
    var form=new FormData();
    var file=$('#file');
    var fileObj=file[0].files[0];
    form.append("file", fileObj);
    $.ajax({
        type: 'POST',
        url: './list?action=uploadFile',
        xhr: function () {
            var myXhr=$.ajaxSettings.xhr();
            if (myXhr.upload) {
                myXhr.upload.addEventListener("progress", onUploading, false);
            }
            return myXhr;
        },
        data: form,
        beforeSend: function () {
            $('#selectFile').prop("disabled", true);
            $('#selectFile').text("正在上传...");
            $('#filename').hide();
            $('#progressbar').css("width", "0");
            $('#progress').show();
        },
        success: function (data) {
            $('#selectFile').prop("disabled", false);
            $('#selectFile').text("重新选择文件");
            $('#progress').hide();
            if (data.code!=0) {
                $('#file').val("");
                alert(data.msg);
            }
            else {
                var size=data.size, tmpname=data.name;
                alert("上传成功！");
                $('#filename').text(getFileName(file.val())+" ("+size+")");
                $('#filename').show();
                $('#size').val(size);
                $('#tmpname').val(tmpname);
            }
        },
        error: function () {
            $('#selectFile').prop("disabled", false);
            $('#selectFile').text("选择上传文件");
            $('#progress').hide();
            $('#file').val("");
            alert("上传失败！");
        },
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json'
    });
}

function doSubmit() {
    var title=$('#title').val(), tmpname=$('#tmpname').val();
    if (title==="") {
        alert("请输入数据集名称！");
        return false;
    }
    if (tmpname==="") {
        alert("你没有选择文件！");
        return false;
    }
    $.post("./list?action=uploadDataset", {
        id: $('#id').val(),
        title: title,
        filename: getFileName($('#file').val()),
        size: $('#size').val(),
        tmpname: tmpname,
        shortdetail: $('#shortdetail').val(),
        detail: $('#detail').val(),
        reference: $('#reference').val()
    }, function (data) {
        if (data.code!=0) {
            alert(data.msg);
        }
        else {
            alert("保存成功！");
            window.location="my.html";
        }
    }, "json");
    return false;
}

function onUploading(e) {
    $('#progressbar').css("width", (e.loaded/e.total*100).toFixed(1)+"%");
}

function pre() {
    var title=$('#title').val(), content=$('#detail').val();
    if (title==="") {
        alert("请输入数据集名称！");
        return false;
    }
    $.post("./list?action=preview", {
        id: $('#id').val(),
        title: title,
        content: content
    }, function(data) {
        console.log(data);
        if (data.code!=0) {
            alert(data.msg);
        }
        else {
            $('#id').val(data.msg);
            $('#preview').popover({
                content: "<a href='preview.html?id="+data.msg+"' target='_blank' onclick=\"$('#preview').popover('destroy')\">预览页面生成成功，点此查看</a>",
                html: true
            });
            $('#preview').popover("show");
        }
    }, "json");
    return false;
}
