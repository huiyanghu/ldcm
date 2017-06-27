$('.ui-select').ui_select();
$('#myCanvas').click(function(){
    picCode();
});
$('#picCode').blur(function(){
    checkPicCodeEmpty2();
});
checkFormat(2);
checkFormat(3);
checkFormat(4);
psdConfirm($('#password'),$('#password2'));
function getUserInfo(){
    $.ajax({
        url: "../baseLdUser/getUserDetail",
        type: "get",
        success: function (data) {
            if (data.isSuccess == 1) {
                var name = data.result.userDetail.name;
                var mobile = data.result.userDetail.mobile;
                var account = data.result.userDetail.account;
                var companyName = data.result.userDetail.companyName;
                $('#email').val(account).attr('disabled','disabled');
                $('#company').val(companyName).attr('disabled','disabled');
                $('#name').val(name);
                $('#phone').val(mobile);
            } else {
                noticeAlert('信息获取失败，请重新进入。','失败','','');
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。', '错误！', '', '');
        }
    });
}
getUserInfo();
$('.btn-primary').click(function(){
    var name = isEmpty(2);
    if(!name){ return false; }
    var mobile = isEmpty(3);
    if(!mobile){ return false; }
    var newPassword = $('#password').val();
    var verificationCode = isEmpty(6);
    if(!verificationCode){ return false; }
    $.ajax({
        url: "../baseLdUser/updateUser",
        type: "post",
        dataType:"json",
        data:{
            "accountId":"",
            "name":name,
            "mobile":mobile,
            "roleFlag":"",
            "verificationCode":verificationCode,
            "newPassword":newPassword
        },
        success: function (data) {
            if(data.isSuccess == 1){
                loadMask.loadEnd($('.register'));
                //isSuccess  # 0 失败 # 1 成功
                //code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在
                if(data.result.code == 2){
                    codeIsWrong();
                }else if(data.result.code == -1){
                    noticeAlert('修改成功。','成功 ','','');
                }else{
                    registerError(data.result.code);
                }
            }else{
                noticeAlert('修改失败，请重新填写修改信息。','失败',loadMaskAndPic,$('.register'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。', '错误！',loadMaskHide,$('.register'));
        }
    });
});