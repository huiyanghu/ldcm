$(document).ready(function() {
    //粒子背景特效
    $('body').particleground({
        dotColor: '#5cbdaa',
        lineColor: '#5cbdaa'
    });
});
//picCode();
$('#myCanvas').click(function(){
    picCode();
});
$('#J_codetext').blur(function(){
    checkPicCodeEmpty();
});
$('.ver_btn').click(function(){
    picCode();
});
function setLoginInfo(){
    var email = $('#email').val();
    localStorage.setItem("email",email);
    var password = $('#password').val();
    localStorage.setItem("password",password);
    localStorage.setItem("remember",1);
}
function removeLoginInfo(){
    localStorage.removeItem('email');
    localStorage.removeItem('password');
    localStorage.setItem("remember",0);
}
function getLoginInfo(){
    var email = localStorage.getItem("email");
    $('#email').val(email);
    var password = localStorage.getItem("password");
    $('#password').val(password);
    var remember = localStorage.getItem("remember");
    if(remember == '' || remember == null || remember == undefined){
        $('#remember').addClass('fa-square-o').removeClass('fa-check-square-o');
    }
    if(remember == 0 || remember == '0'){
        $('#remember').addClass('fa-square-o').removeClass('fa-check-square-o');
    }else if(remember == 1 || remember == '1'){
        $('#remember').removeClass('fa-square-o').addClass('fa-check-square-o');
    }
}
getLoginInfo();
$('.psd-relevant .left').click(function(){
    $(this).children('.fa').toggleClass('fa-check-square-o').toggleClass('fa-square-o');
});
function loginError(num){
    if(num == 1 || num == 9){
        objArr[0].focus().val('').attr('placeholder',codeStr[num]);
    }else if(num == 5 || num == 10){
        objArr[4].focus().attr('placeholder',codeStr[num]);
    }else if(num == 0){
        objArr[6].focus().attr('placeholder',codeStr[num]);
    }
    picCode();
}
function login(){
    var email = isEmpty(0);
    var password = isEmpty(4);
    var verificationCode = isEmpty(6);
    $.ajax({
        url: "../loginAjax",
        type: "post",
        dataType:"json",
        data:{
            "username":email,
            "password":password,
            "verificationCode":verificationCode
        },
        success: function (data) {
            //code # 0 验证码错误  # 1 账号不为空  # 5 密码不为空  # 9 账号不存在  # 10 密码错误
            console.log(data);
            if(data.isSuccess == 1){
                if(data.result.code == -1){
                    if($('#remember').hasClass('fa-square-o')){
                        removeLoginInfo();
                    }else if($('#remember').hasClass('fa-check-square-o')){
                        setLoginInfo();
                    }
                    window.location.href = '../';
                }else{
                    loginError(data.result.code);
                }
            }else{
                noticeAlert('登录失败，请重新填写登录信息。','失败','','');
            }
        },
        error: function (error) {
            noticeAlert('登录失败，请重新填写登录信息。','失败','','');
        }
    });
}
$('#loginBtn').click(function(){
    login();
});
document.onkeydown = function(event) {
    e = event ? event : (window.event ? window.event : null);
    if (e.keyCode == 13) {
        login();
    }
};
