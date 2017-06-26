checkFormat(0);
checkFormat(1);
checkFormat(2);
checkFormat(3);
checkFormat(4);
psdConfirm($('#password'),$('#password2'));
//picCode();
$('#myCanvas').click(function(){
    picCode();
});
$('#picCode').blur(function(){
    checkPicCodeEmpty2();
});
$('#registerBtn').click(function(){
    var email = isEmpty(0);
    if(!email){ return false; }
    var companyName = isEmpty(1);
    if(!companyName){ return false; }
    var name = isEmpty(2);
    if(!name){ return false; }
    var mobile = isEmpty(3);
    if(!mobile){ return false; }
    var password = isEmpty(4);
    if(!password){ return false; }
    var confirmPassword = isEmpty(5);
    if(!confirmPassword){ return false; }
    var verificationCode = isEmpty(6);
    if(!verificationCode){ return false; }
    loadMask.loadStart($('.register'));
    $.ajax({
        url: "../registerAjax",
        type: "post",
        dataType:"json",
        data:{
            "email":email,
            "companyName":companyName,
            "name":name,
            "mobile":mobile,
            "password":password,
            "confirmPassword":confirmPassword,
            "verificationCode":verificationCode
        },
        success: function (data) {
            if(data.isSuccess == 1){
                loadMask.loadEnd($('.register'));
                //isSuccess  # 0 失败 # 1 成功
                //code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在
                if(data.result.code == 2){
                    codeIsWrong();
                }else if(data.result.code == 1){
                    noticeAlert('注册成功，请耐心等待管理员审核...','注册',returnLogin,'');
                }else{
                    registerError(data.result.code);
                }
            }else{
                noticeAlert('注册失败，请重新填写注册信息。','失败',loadMaskAndPic,$('.register'));
            }
        },
        error: function (error) {
            noticeAlert('注册失败，请重新填写注册信息。','失败',loadMaskAndPic,$('.register'));
        }
    });
});
