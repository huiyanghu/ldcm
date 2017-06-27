checkFormat(0);
checkFormat(3);
checkFormat(4);
psdConfirm($('#password'),$('#password2'));
$('#noteBtn').click(function(){
    var email = isEmpty(0);
    if(!email){ return false; }
    var phone = isEmpty(3);
    if(!phone){ return false; }
    noticeAlert('验证正确，请点击确认跳转至修改密码页面','成功',toModifyPsd,email);
});
function toModifyPsd(){
    window.location.href="../modifyPassword";
}
$('#emailBtn').click(function(){
    var email = isEmpty(0);
    if(!email){ return false; }
    var verificationCode = isEmpty(6);
    if(!verificationCode){ return false; }
    loadMask.loadStart($('.register'));
    $.ajax({
        url: "../sendEmail",
        type: "post",
        dataType:"json",
        data:{
            "email":email,
            "veriCode":verificationCode
        },
        success: function (data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('.register'));
                noticeAlert('邮件已发送，请注意查收邮件','成功','','');
            }else{
                noticeAlert('邮件发送失败，请重新发送邮件。','失败',loadMaskAndPic,$('.register'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','失败',loadMaskAndPic,$('.register'));
        }
    });

});