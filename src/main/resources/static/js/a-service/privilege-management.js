var currentPaging=1;
var currentPageSize=10;
getDataList(1);
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
                        var customerId = item.customerId || '';
                        var customerName = item.customerName || '';
                        var email = item.email || '';
                        var name = item.name || '';
                        var mobile = item.mobile || '';
                        var status = item.status;
                        if(status == 0){
                            var statusStr = '未审核';
                            var classStr = '开通';
                        }else if(status == 3){
                            var statusStr = '关闭';
                            var classStr = '启用';
                        }else{
                            var statusStr = '开通';
                            var classStr = '禁用';
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
                                                    +classStr
                                                +'</a>'
                                            +'</td>'
                                        +'</tr>';
                        $('#datatBody').append(tBodyHtml);
                    });
                    $('.review-user').click(function(){
                        var status = Number($(this).attr('status'));
                        if(status == 0){
                            var msg = '确定开通此用户吗?';
                        }else if(status == -1){
                            var msg = '确定禁用此用户吗?';
                        }else if(status == 3){
                            var msg = '确定启用此用户吗?';
                        }
                        var title = '审核';
                        var data ={
                            "id":$(this).attr('id'),
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
    loadMask.loadStart($('#dataTable'));
    var data = JSON.parse(data);
    var id = data.id;
    var status = data.status;
    if(status == 0){
        var newStatus = -1;
    }else if(status == 3){
        var newStatus = -1;
    }else{
        var newStatus = 3;
    }
    //0，	新注册用户（未经过超级管理员审核）-1、体验客户 1、正式收费  2、正式免费  3、暂停客户4
    $.ajax({
        url: "../customer/reviewCustomer",
        type: "get",
        dataType:"json",
        data:{
            "customerId":id,
            "status":newStatus
        },
        success: function (data){
            if(data.isSuccess == 1){
                loadMask.loadEnd($('#dataTable'));
                if(status == 0){
                    $('#'+id).attr('status',-1).html('禁用');
                    $('#'+id).parent().siblings('.statusStr').html('开通');
                    $('#'+id).parent().siblings('.userStr').html('体验客户');
                }else if(status == 3){
                    $('#'+id).attr('status',-1).html('禁用');
                    $('#'+id).parent().siblings('.statusStr').html('开通');
                    $('#'+id).parent().siblings('.userStr').html('体验客户');
                }else{
                    $('#'+id).attr('status',3).html('启用');;
                    $('#'+id).parent().siblings('.statusStr').html('关闭');
                    $('#'+id).parent().siblings('.userStr').html('暂停客户');
                }
                noticeAlert('修改用户状态成功。','成功','','');
            }else{
                noticeAlert('修改用户状态失败，请重新修改。','失败',loadMaskHide,$('#dataTable'));
            }
        },
        error: function (error) {
            noticeAlert('网络出错，请重新连接网络。','错误！',loadMaskHide,$('#dataTable'));
        }
    });
}