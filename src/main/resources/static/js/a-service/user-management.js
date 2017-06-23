// var paginationJson = {
//     currentPage: 1,
//     pageSize: 10,
//     pageCount: 1,
//     total: 4,
// }
// creat_pagination(paginationJson);
function editPsd(){
    var formHtml = '<form action="" class="form-horizontal fill-user-info">'
                        +'<div class="form-group">'
                            +'<label class="control-label col-md-3 no-padding-right">新密码：</label>'
                            +'<div class="col-md-4 no-padding-left left-input">'
                                +'<input type="password" class="form-control" id="password" name="password" placeholder="请设置对方账号新密码"/>'
                                +'<span class="error-span"></span>'
                            +'</div>'
                        +'</div>'
                        +'<div class="form-group" style="margin-bottom: 0;">'
                            +'<label class="control-label col-md-3 no-padding-right">确认新密码：</label>'
                            +'<div class="col-md-4 no-padding-left left-input">'
                                +'<input type="password" class="form-control" id="password2" name="password2" placeholder="请确认对方账号新密码"/>'
                                +'<span class="error-span"></span>'
                            +'</div>'
                        +'</div>'
                    +'</form>';
    var title = '修改密码';
    confirmAlert(formHtml,title,editUserPsd,checkPsd,'');
}
function checkPsd(){
    checkFormat(4);
    psdConfirm($('#password'),$('#password2'));
    if(checkFormat(4) && psdConfirm($('#password'),$('#password2'))){
        return true;
    }else{
        return false;
    }
}
function editUserPsd(){
    if(checkPsd()){
        console.log('false');
        return false;
    }else{
        console.log('true');
    }
}
$('.refresh-paddword').click(function(){
    editPsd();
});
function deleteUser(alertBox,data){
    alertBox.hide();
}
$('.del-user').click(function(){
    var msg = '确定要删除吗?';
    var title = '删除';
    var data = '1111';
    confirmAlert(msg,title,deleteUser,'',data);
});
