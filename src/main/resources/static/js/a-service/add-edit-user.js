$('.ui-select').ui_select();
var Request=new UrlSearch();
var flag = Request.flag;
if(flag == 'add'){
    $('.current-str').html('增加用户');
    psdConfirm($('#password'),$('#password2'));
}else if(flag == 'edit'){
    var baseLdUserId = Request.dataJson;
    $('.current-str').html('修改用户');
    $('.psd-form-group').hide();
    getUserInfo();
}
checkFormat(0);
checkFormat(1);
checkFormat(2);
checkFormat(3);
checkFormat(4);
$('#myCanvas').click(function(){
    picCode();
});
$('#picCode').blur(function(){
    checkPicCodeEmpty2();
});
function getUserInfo(){
    $.ajax({
        url: "../baseLdUser/getUserDetail",
        type: "get",
        dataType:"json",
        data:{
            "accountId":baseLdUserId
        },
        success: function (data) {
            console.log(data)
            if (data.isSuccess == 1) {
                var name = data.result.userDetail.name;
                var mobile = data.result.userDetail.mobile;
                var account = data.result.userDetail.account;
                var companyName = data.result.userDetail.companyName;
                var userType = data.result.userDetail.roleFlag;
                $('#email').val(account).attr('disabled','disabled');
                $('#company').val(companyName).attr('disabled','disabled');
                $('#name').val(name);
                $('#phone').val(mobile);
                $('#userType').ui_select().val(userType);
            } else {
                noticeAlert('信息获取失败，请重新进入。','失败','','');
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。', '错误！', '', '');
        }
    });
}
$('#addEditBtn').click(function(){
    var email = isEmpty(0);
    if(!email){ return false; }
    var companyName = isEmpty(1);
    if(!companyName){ return false; }
    var name = isEmpty(2);
    if(!name){ return false; }
    var mobile = isEmpty(3);
    if(!mobile){ return false; }
    var verificationCode = isEmpty(6);
    if(!verificationCode){ return false; }
    var roleFlag = $('#userType').val();
    if(roleFlag == ''){ return false; }
    if(flag == 'add'){
        var password = isEmpty(4);
        if(!password){ return false; }
        var confirmPassword = isEmpty(5);
        if(!confirmPassword){ return false; }
        var url = '../baseLdUser/addUser';
        var dataJson = {
            "email":email,
            "companyName":companyName,
            "name":name,
            "mobile":mobile,
            "roleFlag":roleFlag,
            "password":password,
            "confirmPassword":confirmPassword,
            "verificationCode":verificationCode
        };
        var successMsg = '注册成功。';
        var msg = '注册失败，请重新填写注册信息。';
    }else if(flag == 'edit'){
        $('.current-str').html('修改用户');
        $('.psd-form-group').hide();
        var url = '../baseLdUser/updateUser';
        var dataJson = {
            "accountId":baseLdUserId,
            "name":name,
            "mobile":mobile,
            "roleFlag":roleFlag,
            "verificationCode":verificationCode
        };
        var successMsg = '修改成功。';
        var msg = '修改失败，请重新填写修改信息。';
    }
    loadMask.loadStart($('.register'));
    $.ajax({
        url: url,
        type: "post",
        dataType:"json",
        data:dataJson,
        success: function (data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('.register'));
                //isSuccess  # 0 失败 # 1 成功
                //code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在
                if(data.result.code == 2){
                    codeIsWrong();
                }else if(data.result.code == -1){
                    noticeAlert(successMsg,'成功 ',returnUserManagement,'');
                }else{
                    registerError(data.result.code);
                }
            }else{
                noticeAlert(msg,'失败',loadMaskAndPic,$('.register'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('.register'));
        }
    });
});
function returnUserManagement(){
    window.location.href = '../baseLdUser/userManagement';
}