getDataList(1);
var currentPaging=1;
var currentPageSize=10;
function getDataList(page){
    loadMask.loadStart($('#dataTable'));
    $.ajax({
        url: "../customer/list",
        type: "post",
        dataType:"json",
        data:{
            "page":page,
            "pageSize":currentPageSize
        },
        success: function (data) {
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#dataTable'));
                var result = data.result;
                var paginationJson = {
                    currentPage: result.currentPage,
                    pageSize: result.pageSize,
                    pageCount: result.pageCount,
                    total: result.total
                };
                creat_pagination(paginationJson);
                var dataList = result.content;
                if(dataList.length == 0){
                    $('.noData').show().siblings('table').hide();
                }else{
                    var tHeadHtml = '<tr>'
                                        +'<th>公司名称</th>'
                                        +'<th>账号</th>'
                                        +'<th>姓名</th>'
                                        +'<th>手机</th>'
                                        +'<th>用户类型</th>'
                                        +'<th>状态</th>'
                                        +'<th>操作</th>'
                                    +'</tr>';
                    $('#datatHead').html(tHeadHtml);
                    $('#datatBody').html('');
                    $.each(dataList,function(i,item){
                        var customerId = item.customerId;
                        var customerName = item.customerName;
                        var email = item.email;
                        var name = item.name;
                        var mobile = item.mobile;
                        var status = item.status;
                        if(status == 0){
                            var statusStr = '未审核';
                            var classStr = 'fa fa-lock';
                        }else if(status == 3){
                            var statusStr = '关闭';
                            var classStr = 'fa fa-lock';
                        }else{
                            var statusStr = '开通';
                            var classStr = 'fa fa-unlock';
                        }
                        var userStr = item.statusStr;
                        var tBodyHtml = '<tr>'
                                            +'<td>'+customerName+'</td>'
                                            +'<td title="'+email+'">'+email+'</td>'
                                            +'<td>'+name+'</td>'
                                            +'<td>'+mobile+'</td>'
                                            +'<td class="userStr">'+userStr+'</td>'
                                            +'<td class="statusStr">'+statusStr+'</td>'
                                            +'<td>'
                                                +'<a href="javascript:void(0);" class="color-9 review-user" status="'+status+'" id="'+customerId+'">'
                                                    +'<i title="审核" class="'+classStr+'"></i>'
                                                +'</a>'
                                            +'</td>'
                                        +'</tr>';
                        $('#datatBody').append(tBodyHtml);
                    });
                    $('.review-user').click(function(){
                        var status = Number($(this).attr('status'));
                        if(status == 0 || status == 3){
                            var msg = '确定开通此用户吗?';
                        }else{
                            var msg = '确定关闭此用户吗?';
                        }
                        var title = '审核';
                        var data ={
                            "obj":$(this).attr('id'),
                            "status":status
                        }
                        var dataStr = JSON.stringify(data);
                        confirmAlert(msg,title,reviewUser,dataStr);
                    });
                }
            }else{
                noticeAlert('数据获取失败，请重新搜索。','失败',loadMaskHide,$('#dataTable'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#dataTable'));
        }
    });
}

function reviewUser(data){
    var data = JSON.parse(data);
    var obj = data.obj;
    var status = data.status;
    //0，	新注册用户（未经过超级管理员审核）-1、体验客户 1、正式收费  2、正式免费  3、暂停客户4
    if(status == 0){
        $('#'+obj).attr('status',-1);
        $('#'+obj).find('.fa').removeClass('fa-lock').addClass('fa-unlock');
        $('#'+obj).parent().siblings('.statusStr').html('开通');
        $('#'+obj).parent().siblings('.userStr').html('体验客户');
    }else if(status == 3){
        $('#'+obj).attr('status',-1);
        $('#'+obj).find('.fa').removeClass('fa-lock').addClass('fa-unlock');
        $('#'+obj).parent().siblings('.statusStr').html('开通');
    }else{
        $('#'+obj).attr('status',3);
        $('#'+obj).find('.fa').removeClass('fa-unlock').addClass('fa-lock');
        $('#'+obj).parent().siblings('.statusStr').html('关闭');
    }
}