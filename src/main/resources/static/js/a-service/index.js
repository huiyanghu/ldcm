/*#45c8dc  #988fd8  #24c5a4  #ffc941   #988fd8   #717af6   #ff9242   #84d9e7*/
function checkWindowHeight(){
    var height = $(window).height() - $('#header').height();
    $('#page').css('height',height);
    $('#rightIframe').attr('style', 'height:' + (height-7) + 'px');
    $('#sidebar').attr('style', 'min-height:' + height + 'px !important; border-right: 1px solid #e9ecf2;');
}
function collapseSidebar(){
	var iconLeft = $('#sidebar-collapse').attr("data-icon1");
	var iconRight = $('#sidebar-collapse').attr("data-icon2");
	var par = $('#sidebar-collapse').parent('.navbar-brand');
	$('#sidebar-collapse').click(function(){
		if(par.hasClass('mini-menu')){
			par.removeClass('mini-menu');
			$('#sidebar').removeClass('mini-menu');
			$('#main-content').removeClass('margin-left-50');
		}else{
			par.addClass('mini-menu');
			$('#sidebar').addClass('mini-menu');
			$('#main-content').addClass('margin-left-50');
		}
	});
}
var flagStr = 'business-1';
function setIframeUrl(url,flag){
	flagStr = flag;
	$('#rightIframe').attr('src',url);
}
function getIframeUrl(){
	var url = $('#rightIframe').attr('src');
	return url;
}
$(function(){
	checkWindowHeight();
	collapseSidebar();
	$('.has-sub > a').click(function(){
		$(this).parent('.has-sub').siblings().find('.sub').hide();
		var sib = $(this).siblings('.sub');
		if(sib.is(':hidden')){
			sib.show();
		}else{
			sib.hide();
		}
	});
    $('#userDropList .jump-page a').click(function(){
    	setIframeUrl($(this).attr('url'),$(this).attr('flag'));
		$('.has-sub').removeClass('active').children('.sub').hide().find('li').removeClass('active');
		$('.has-sub').find('.arrow').removeClass('open');
    });
    $('.has-sub').click(function(){
    	$(this).addClass('active').siblings().removeClass('active').find('.arrow').addClass('open');
    	var sib = $(this).children('.sub');
    	var sibLen = sib.length;
    	var chi = $(this).find('.arrow');
    	if(sibLen == 0){
    		var url = $(this).children('a').attr('url');
    		var flag = $(this).children('a').attr('flag');
	    	if(url != '' || url != undefined || url != null){
	    		setIframeUrl(url,flag);
	    	}
    	}else{
    		if(chi.hasClass('open')){
    			chi.removeClass('open');
    			sib.children().each(function(){
    				var flag = $(this).children('a').attr('flag');
    				if(flag == flagStr){
    					$(this).addClass('active');
    				}else{
    					$(this).removeClass('active');
    				}
    			});
    		}else{
    			chi.addClass('open');
    			sib.children().removeClass('active');
    		}
    	}
    });
    $('.sub a').click(function(e){
    	e.stopPropagation();
    	$(this).parent().addClass('active').siblings().removeClass('active');
    	$(this).parents('.has-sub').siblings().find('li').removeClass('active');
    	var url = $(this).attr('url');
    	var flag = $(this).attr('flag');
    	if(url != '' || url != undefined || url != null){
    		setIframeUrl(url,flag);
    	}
    });
    $('#team-status-btn').click(function(){
    	$('#team-status').toggleClass('open');
    	$('#team-status-i').toggleClass('fa-angle-down').toggleClass('fa-angle-up');
    	checkWindowHeight();
    });
});
function toHomePage(){
	$('.homePage').addClass('active').siblings().removeClass('active').children('.sub').hide().find('li').removeClass('active');
	$('.homePage').addClass('active').siblings().find('.arrow').removeClass('open');
	$('.homePage').children('.sub').show().children('li').eq(0).addClass('active');
}
function getCustomerInfo(){
	$.ajax({
		url: "../customer/getCustomerInfo",
		type: "get",
		success: function (data) {
			if(data.isSuccess == 1){
				//运营信息
				var operateInfo = data.result.operateInfo;
				$('#operatePic').attr('src',operateInfo.icon);
				$('#operateName').html(operateInfo.name);
				$('#operatePhone').html(operateInfo.mobile);
				$('#operateWeChat').html(operateInfo.weixin);
				$('#operateOICQ').html(operateInfo.qq);
				//常见信息
				var commonInfo = data.result.commonInfo;
				$('#shaKey').html(commonInfo.shaKey);
				$('#domain').html(commonInfo.domain);
				$('#feedback').html(commonInfo.feedback);
			}else{
				noticeAlert('信息获取失败，请重新进入。','失败','','');
			}
		},
		error: function (error) {
			noticeAlert('网络出错，请重新连接网络。','错误！','','');
		}
	});
}
getCustomerInfo();

