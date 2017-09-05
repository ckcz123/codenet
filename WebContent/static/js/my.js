/**
 * Created by oc on 2017/9/5.
 */

$(document).ready(function(){
    $("#upload").on('click', upload);
});

function upload(){
    console.log("upload")
    $.post('./list?action=upload&username=oc', function(data){
        console.log(data);
    })
}