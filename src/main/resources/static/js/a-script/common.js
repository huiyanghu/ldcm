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
function checkFormat(obj,fun,str1,str2){
	obj.blur(function(){
		if($(this).val() == ''){
			$(this).siblings('.error-span').html(str1);
		}else{
			if(fun($(this).val())){
	    		$(this).parents('.form-group').removeClass('has-error').addClass('has-success');
	    		$(this).siblings('.error-span').html('');
	    	}else{
	    		$(this).parents('.form-group').removeClass('has-success').addClass('has-error');
	    		$(this).siblings('.error-span').html(str2);
	    	}
		}
    });
}
//弹窗通知
function noticeAlert(msg,title,btnCall,funCall,data){
	var alertBox = bootbox.dialog({
		message: msg,
		title: title,
		buttons: {
			main: {
				label: "确认",
				className: "btn-primary btn-red",
				callback: function() {
					if(btnCall){
						btnCall(alertBox,data);
					}else{
						alertBox.hide();
					}
				}
			}
		}
	});
	if(funCall){
		funCall.call();
	}
}
//弹窗确认  || 弹窗修改密码
function confirmAlert(msg,title,btnCall,funCall,data){
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
						btnCall(alertBox,data);
					}
				}
			}
		}
	});
	if(funCall){
		funCall.call();
	}
}
function homePage(){
	parent.toHomePage();
}
