//url传参
function UrlSearch() {
	var name,value; 
	var str=location.href;
	var num=str.indexOf("?") 
	str=str.substr(num+1);
	var arr=str.split("&");
	for(var i=0;i < arr.length;i++){ 
		num=arr[i].indexOf("="); 
		if(num>0){ 
			name=arr[i].substring(0,num);
			value=arr[i].substr(num+1);
			this[name]=value;
		}
	} 
}
//ajax访问时，刷新区域遮盖蒙板
var loadMask = function (){
	return{
		loadStart:function(obj){
			var loadDiv = $(obj).find('.loadDiv');
			if(loadDiv.length == 0){
				var loadHtml = '<div class="loadDiv">'
									+'<div class="cover"></div>'
									+'<div class="loader-inner ball-clip-rotate-multiple">'
										+'<div></div>'
										+'<div></div>'
									+'</div>'
								+'</div>';
				obj.append(loadHtml);
			}else{
				loadDiv.show();
			}
		},
		loadEnd:function(obj){
			$(obj).find('.loadDiv').hide();
		}
	}
}();
//ajax访问失败，点击弹窗确认件，取消蒙板
function loadMaskHide(data){
	loadMask.loadEnd(data);
}
//返回登录页
function returnLogin(){
	window.location.href = '../toLogin';
}
// 常量数组 0 邮箱  1 公司名称  2 姓名  3 手机  4 密码  5 密码确认  6 图片验证码
//code # -1 没有错误 # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在  # 9 账号不存在  # 10 密码错误
var objArr = [$('#email'),$('#company'),$('#name'),$('#phone'),$('#password'),$('#password2'),$('#picCode')];
var regularArr = [isEmail,specialCharacter,noNumber,phoneNumber,passwordCheck];
var emptyStr = ["邮箱不能为空","公司名称不能为空","姓名不能为空","手机号码不能为空","密码不能为空","两次密码不一致","请输入验证码"];
var errorStr = ["请输入正确的邮箱格式","请输入正确的公司名称","请输入真实的姓名，不能包含数字","请输入正确的手机号码","请输入6-16位的数字和字母组合","两次密码不一致","验证码错误"];
var codeStr = ["验证码错误","邮箱不能为空","公司名称不能为空","姓名不能为空","手机号码不能为空","密码不能为空","两次密码不一致","邮箱已被注册","公司已被注册","账号不存在","密码错误"];
//提交表单，验证表单内是否有空，如有空则不提交,空选项获取焦点
function isEmpty(num){
	if(objArr[num].val() == '' || objArr[num].val() == undefined || objArr[num].val() == null){
		objArr[num].focus().siblings('.error-span').html(emptyStr[num]);
		return false;
	}else{
		return objArr[num].val();
	}
}
//ajax访问失败
function ajaxError(){
	console.log(status);
}
function codeIsWrong(){
	$('#picCode').focus().siblings('.error-span').html(errorStr[6]);
}
//code # 0 验证码错误 # 1 账号不为空 # 2 公司名称不为空 # 3 姓名不为空  # 4 手机不为空  # 5 密码不为空  # 6 两次密码不一致  # 7 账号已存在  # 8 公司已存在
function registerError(num){
	if(num == 1 || num == 7){
		objArr[0].focus().siblings('.error-span').html(codeStr[num]);
	}else if(num == 2 || num == 8){
		objArr[1].focus().siblings('.error-span').html(codeStr[num]);
	}else if(num == 0){
		objArr[6].focus().siblings('.error-span').html(codeStr[num]);
	}else{
		objArr[num-1].focus().siblings('.error-span').html(codeStr[num]);
	}
	picCode();
}
function loadMaskAndPic(data){
	loadMask.loadEnd(data);
	picCode();
}
//ajax从后台获取图片验证码
function picCode(){
	$('#myCanvas').attr('src','../getAuthImage?date=' + new Date());
}
//图片验证码是否为空
function checkPicCodeEmpty () {
	var inputCode = document.getElementById("picCode").value.toUpperCase();
	if(inputCode.length <=0) {
		document.getElementById("picCode").setAttribute("placeholder","请输入验证码");
		picCode();
		return false;
	}
}
function checkPicCodeEmpty2 () {
	var inputCode = document.getElementById("picCode").value.toUpperCase();
	if(inputCode.length <=0) {
		document.getElementById("pic-code-error").innerHTML = "请输入验证码";
		picCode();
		return false;
	}
}
//邮箱验证
function isEmail(str){ 
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
	return reg.test(str); 
}
//特殊字符验证
function specialCharacter(str){ 
	var reg = /^[a-zA-Z0-9\u4e00-\u9fa5\s]+$/; 
	return reg.test(str); 
}
//非数字验证
function noNumber(str){ 
	var reg = /^[a-zA-Z\u4e00-\u9fa5]+$/; 
	return reg.test(str); 
}
//手机号码验证
function phoneNumber(str){
	var reg = /^1[3|5|8][0-9]\d{4,8}$/;
	return reg.test(str);
}
//密码强度验证 
function passwordCheck(str){
	var reg = /^(?![^a-zA-Z]+$)(?!\D+$).{6,16}$/;
	return reg.test(str);
}
//密码确认
function psdConfirm(obj1,obj2){
	obj2.focus(function(){
		if(obj1.val() == ''){
			obj1.focus().siblings('.error-span').html('请输入密码');
		}else{
			return false;
		}
	});
	obj2.blur(function(){
		if($(this).val() != obj1.val()){
			$(this).parents('.form-group').removeClass('has-success').addClass('has-error');
			$(this).siblings('.error-span').html('两次密码不一致');
		}else if(($(this).val() == obj1.val()) && obj1.val() != ''){
			$(this).parents('.form-group').removeClass('has-error').addClass('has-success');
			$(this).siblings('.error-span').html('');
		}else if(obj1.val() == ''){
			$(this).parents('.form-group').removeClass('has-error').removeClass('has-success');
			$(this).siblings('.error-span').html('');
		}
	});
}
//验证函数调用
function checkFormat(num){
	objArr[num].blur(function(){
		if($(this).val() == ''){
			$(this).siblings('.error-span').html(emptyStr[num]);
		}else{
			if(regularArr[num]($(this).val())){
	    		$(this).parents('.form-group').removeClass('has-error').addClass('has-success');
	    		$(this).siblings('.error-span').html('');
	    	}else{
	    		$(this).parents('.form-group').removeClass('has-success').addClass('has-error');
	    		$(this).siblings('.error-span').html(errorStr[num]);
	    	}
		}
    });
}
//弹窗通知
function noticeAlert(msg,title,btnCall,data){
	var alertBox = bootbox.dialog({
		message: msg,
		title: title,
		buttons: {
			main: {
				label: "确认",
				className: "btn-primary btn-red",
				callback: function() {
					if(btnCall){
						btnCall(data);
					}else{
						alertBox.hide();
					}
				}
			}
		}
	});
}
//弹窗确认  || 弹窗修改密码
function confirmAlert(msg,title,btnCall,data){
	var alertBox = bootbox.dialog({
		message: msg,
		title: title,
		buttons: {
			success: {
				label: "取消",
				className: "btn-default btn-big",
				callback: function() {
					alertBox.hide();
				}
			},
			main: {
				label: "确认",
				className: "btn-primary btn-red",
				callback: function() {
					if(btnCall){
						btnCall(data);
					}
				}
			}
		}
	});
}
//点击首页时，左侧菜单回到接入量分析
function homePage(){
	parent.toHomePage();
}
