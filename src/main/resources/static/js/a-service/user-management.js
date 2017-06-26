getDataList(1);
var currentPaging=1;
var currentPageSize=10;
function getDataList(page){
    loadMask.loadStart($('#dataTable'));
    $.ajax({
        url: "../baseLdUser/list",
        type: "post",
        dataType:"json",
        data:{
            "page":page,
            "pageSize":currentPageSize
        },
        success: function (data) {
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#dataTable'));
                var result = data.result;
                var paginationJson = {
                    currentPage: result.currentPage,
                    pageSize: result.pageSize,
                    pageCount: result.pageCount,
                    total: result.total
                };
                creat_pagination(paginationJson);
                var dataList = result.content;
                if(dataList.length == 0){
                    $('.noData').show().siblings('table').hide();
                }else{
                    var tHeadHtml = '<tr>'
                                        +'<th>账号</th>'
                                        +'<th>姓名</th>'
                                        +'<th>密码</th>'
                                        +'<th>手机</th>'
                                        +'<th>角色类型</th>'
                                        +'<th>操作</th>'
                                    +'</tr>';
                    $('#datatHead').html(tHeadHtml);
                    $('#datatBody').html('');
                    $.each(dataList,function(i,item){
                        var account = item.account;
                        var name = item.name;
                        var mobile = item.mobile;
                        var roleFlag = item.roleFlag;
                        var roleFlagStr = item.roleFlagStr;
                        var baseLdUserId = item.baseLdUserId;
                        var tBodyHtml = '<tr>'
                                            +'<td title="heyitan@sina.com">'+account+'</td>'
                                            +'<td>'+name+'</td>'
                                            +'<td>******</td>'
                                            +'<td>'+mobile+'</td>'
                                            +'<td>'+roleFlagStr+'</td>'
                                            +'<td>'
                                                +'<a href="../baseLdUser/addEditUser?flag=edit&amp;dataJson='+baseLdUserId+'" class="color-9">'
                                                    +'<i title="修改用户信息" class="fa fa-pencil"></i>'
                                                +'</a>'
                                                +'<a href="javascript:void(0);" class="color-9 refresh-paddword" userid="'+baseLdUserId+'">'
                                                    +'<i title="重置用户密码" class="fa fa-refresh"></i>'
                                                +'</a>'
                                                +'<a href="javascript:void(0);" class="color-9 del-user" userid="'+baseLdUserId+'">'
                                                    +'<i title="删除用户" class="fa fa-trash-o"></i>'
                                                +'</a>'
                                            +'</td>'
                                        +'</tr>';
                        $('#datatBody').append(tBodyHtml);
                    });
                    $('.refresh-paddword').click(function(){
                        var data = $(this).attr('userid');
                        editPsd(data);
                    });
                    $('.del-user').click(function(){
                        var msg = '确定要删除该用户吗?';
                        var title = '删除';
                        var data = $(this).attr('userid');
                        confirmAlert(msg,title,deleteUser,data);
                    });
                }
            }else{
                noticeAlert('数据获取失败，请重新搜索。','失败',loadMaskHide,$('#dataTable'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#dataTable'));
        }
    });
}
function editPsd(data){
    var formHtml = '<form action="" class="form-horizontal fill-user-info">'
                        +'<div class="form-group">'
                            +'<label class="control-label col-md-3 no-padding-right">新密码：</label>'
                            +'<div class="col-md-4 no-padding-left left-input">'
                                +'<input type="password" class="form-control" id="password" name="password" placeholder="请设置对方账号新密码" onblur="checkPsd()"/>'
                                +'<span class="error-span"></span>'
                            +'</div>'
                        +'</div>'
                        +'<div class="form-group" style="margin-bottom: 0;">'
                            +'<label class="control-label col-md-3 no-padding-right">确认新密码：</label>'
                            +'<div class="col-md-4 no-padding-left left-input">'
                                +'<input type="password" class="form-control" id="password2" name="password2" placeholder="请确认对方账号新密码" onfocus="checkPsd1()" onblur="checkPsd2()"/>'
                                +'<span class="error-span"></span>'
                            +'</div>'
                        +'</div>'
                    +'</form>';
    var title = '修改密码';
    confirmAlert(formHtml,title,editUserPsd,data);
}
function editUserPsd(data){
    var newPassword = isEmpty(4);
    if(!newPassword){ return false; }
    var confirmPassword = isEmpty(5);
    if(!confirmPassword){ return false; }
    $.ajax({
        url: "../updatePassword",
        type: "post",
        dataType:"json",
        data:{
            "id":data,
            "newPassword":newPassword
        },
        success: function (data) {
            //code # 5 密码不为空  # 6 两次密码不一致
            if(data.isSuccess == 1){
                if(data.result.code == -1){
                    noticeAlert('修改成功。','成功','','');
                }else{
                    noticeAlert('修改失败，'+codeStr[num]+'。','失败','','');
                }
            }else{
                noticeAlert('修改失败，请重新设置对方密码。','失败','','');
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！','','');
        }
    });
}
function checkPsd(){
    if($('#password').val() == ''){
        $('#password').siblings('.error-span').html("密码不能为空");
        return false;
    }else{
        if(passwordCheck($('#password').val())){
            $('#password').parents('.form-group').removeClass('has-error').addClass('has-success');
            $('#password').siblings('.error-span').html('');
        }else{
            $('#password').parents('.form-group').removeClass('has-success').addClass('has-error');
            $('#password').siblings('.error-span').html("请输入6-16位的数字和字母组合");
        }
    }
}
function checkPsd1(){
    if($('#password').val() == ''){
        $('#password').focus().siblings('.error-span').html('请输入密码');
    }else{
        return false;
    }
}
function deleteUser(data){
    $.ajax({
        url: "../baseLdUser/deleteUser",
        type: "get",
        dataType:"json",
        data:{
            "baseLdUserId":data
        },
        success: function (data) {
            if(data.isSuccess == 1){
                noticeAlert('删除成功。','成功','','');
            }else{
                noticeAlert('修改失败，请重新设置对方密码。','失败','','');
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！','','');
        }
    });
}
