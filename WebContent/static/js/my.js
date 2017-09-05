/**
 * Created by oc on 2017/9/5.
 */

$(document).ready(function(){
    $("#upload").on('click', upload);
});

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