checkFormat(0);
checkFormat(3);
checkFormat(4);
psdConfirm($('#password'),$('#password2'));
$('#myCanvas').click(function(){
    picCode();
});
$('#picCode').blur(function(){
    checkPicCodeEmpty2();
});
$('#noteBtn').click(function(){
    //forget-page
    var email = isEmpty(0);
    if(!email){ return false; }
    var phone = isEmpty(3);
    if(!phone){ return false; }
    noticeAlert('验证正确，请点击确认跳转至修改密码页面','成功',toModifyPsd,email);
});
function toModifyPsd(){
    window.location.href="../modifyPassword";
}
$('#account').blur(function(){
    if($(this).val() == ''){
        $(this).siblings('.error-span').html('邮箱不能为空');
    }else{
        if(isEmail($(this).val())){
            $(this).parents('.form-group').removeClass('has-error').addClass('has-success');
            $(this).siblings('.error-span').html('');
        }else{
            $(this).parents('.form-group').removeClass('has-success').addClass('has-error');
            $(this).siblings('.error-span').html('请输入正确的邮箱格式');
        }
    }
});
$('#emailBtn').click(function(){
    if($('#account').val() == '' || $('#account').val() == undefined || $('#account').val() == null){
        $('#account').focus().siblings('.error-span').html('邮箱不能为空');
        return false;
    }else{
        var email = $('#account').val();
    }
    var verificationCode = isEmpty(6);
    if(!verificationCode){ return false; }
    loadMask.loadStart($('.forget-page'));
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
                loadMask.loadEnd($('.forget-page'));
                noticeAlert('邮件已发送，请注意查收邮件','成功','','');
            }else{
                noticeAlert('邮件发送失败，请重新发送邮件。','失败',loadMaskAndPic,$('.forget-page'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','失败',loadMaskAndPic,$('.forget-page'));
        }
    });

});