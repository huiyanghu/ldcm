checkFormat(4);
psdConfirm($('#password'),$('#password2'));
$('#psdBtn').click(function(){
    var newPassword = isEmpty(4);
    if(!newPassword){ return false; }
    var confirmPassword = isEmpty(5);
    if(!confirmPassword){ return false; }
    var verificationCode = isEmpty(6);
    if(!verificationCode){ return false; }
    $.ajax({
        url: "../updatePassword",
        type: "post",
        dataType:"json",
        data:{
            "newPassword":newPassword,
            "veriCode":verificationCode
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
});