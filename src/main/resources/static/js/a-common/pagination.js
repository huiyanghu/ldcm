var pageCount;
var currentPaging;
var currentPageSize;
var total;
function creat_pagination(paginationJson,pic){
    if(pic == 'pic'){
        var pageSizeHtml = '<li class="active">12</li><li>24</li><li>36</li><li>48</li><li>60</li>';
    }else{
        var pageSizeHtml = '<li class="active">10</li><li>20</li><li>30</li><li>50</li><li>100</li>';
    }
    pageCount = paginationJson.pageCount;
    currentPaging = paginationJson.currentPage;
    currentPageSize = paginationJson.pageSize;
    total = paginationJson.total;
    var pageHtml = '<div class="page">'
                        +'<div class="prevPage paddingBox borderBox gray">上一页</div>'
                        +'<div class="paging"></div>'
                        +'<div class="nextPage paddingBox borderBox gray">下一页</div>'
                    +'</div>'
                    +'<div class="pageJump">'
                        +'<span>跳转到</span>'
                        +'<input type="number" class="pageJumpNum borderBox gray" min="1" max="'+pageCount+'" id="pageJump" />'
                        +'<a href="javascript:void(0)" class="paddingBox borderBox" id="pageJumpBtn">跳转</a>'
                    +'</div>'
                    +'<div class="pageSize">'
                        +'<span>每页显示</span>'
                        +'<div class="select borderBox gray pageSelect">'
                            +'<span id="pageSize">10</span>'
                            +'<span class="pageSizeBtn"></span>'
                            +'<div class="selectCon borderBox">'
                                +'<ul>'
                                    +pageSizeHtml
                                +'</ul>'
                            +'</div>'
                        +'</div>'
                    +'</div>'
                    +'<div class="total">共'+total+'条</div>';
    $('#pagination').html(pageHtml);
    creat_paging(currentPaging,pageCount);
    $('#pageSize').html(currentPageSize);
    $.each($('.selectCon li'),function(){
        var sizeNum = Number($(this).text());
        if(sizeNum == currentPageSize){
            $(this).addClass('active').siblings().removeClass('active');
        }
    });
    $('.pageSelect').click(function(){
        if($('.selectCon').is(':hidden')){
            $('.selectCon').show();
        }else{
            $('.selectCon').hide();
        }
    });
    /* pageSize */
    $('.pagination .selectCon li').click(function(){
        var pageTotal = pageCount*currentPageSize;
        $(this).addClass('active').siblings().removeClass('active');
        var newPageSize = Number($(this).html());
        $('#pageSize').html(newPageSize);
        paginationJson.pageSize = currentPageSize = newPageSize;
        var newPageCount = Math.ceil(total/newPageSize);
        paginationJson.pageCount = pageCount = newPageCount;
        if(currentPaging >= newPageCount){
            currentPaging = newPageCount;
        }
        $('#pageJump').attr('max',newPageCount);
        creat_paging(currentPaging,pageCount);
        getDataList(currentPaging);
        $('.selectCon').hide();
    });
    /* 上一页 */
    $('.prevPage').click(function(){
        if(currentPaging == 1){
            return false;
        }else{
            creat_paging(currentPaging-1,pageCount);
            getDataList(currentPaging);
        }
    });
    /* 下一页 */
    $('.nextPage').click(function(){
        if(currentPaging == pageCount){
            return false;
        }else{
            creat_paging(currentPaging+1,pageCount);
            getDataList(currentPaging);
        }
    });
    /* 跳转某页 */
    $('#pageJumpBtn').click(function(){
        var page = $('#pageJump').val();
        if(page != '' || page != undefined || page != null){
            page = Number(page);
            creat_paging(page,pageCount);
            getDataList(page);
        }
    });
    document.onkeyup = keyUp;
}
/* 键盘操作 */
function keyUp(e) {
    var currKey = 0, e = e || event;
    if (e.preventDefault) {
        e.preventDefault();
    } else {
        e.returnValue = false;
    }
    currKey = e.keyCode || e.which || e.charCode;
    if (currKey == 37 || currKey == 33) {
        $('.prevPage').click();
    } else if (currKey == 39 || currKey == 34) {
        $('.nextPage').click();
    }
}
/* 根据currentPage，重写pageing */
function creat_paging(currentPage,pageCount){
    currentPaging = currentPage;
    $('#pageJump').val(currentPaging);
    var minPage = currentPage - 2;
    var maxPage = currentPage + 2;
    var pagingHtml = '';
    var activeStr = '';
    if(pageCount <= 7){
        for(minPage = 1; minPage <= pageCount; minPage++){
            if(minPage == currentPage){
                activeStr = 'active';
            }else{
                activeStr = '';
            }
            pagingHtml += '<li class="borderBox gray '+activeStr+'">'+minPage+'</li>';
        }
        $('.paging').html('<ul>'+pagingHtml+'</ul>');
    }else{
        if(minPage <= 2){
            minPage = 1;
        }else{
            pagingHtml = '<li class="borderBox">1</li><li>...</li>';
        }
        var endPageHtml = '';
        if(maxPage >= pageCount-1){
            maxPage = pageCount;
            endPageHtml = ''
        }else{
            endPageHtml = '<li>...</li><li class="borderBox">'+pageCount+'</li>';
        }
        for(minPage; minPage <= maxPage; minPage++){
            if(minPage == currentPage){
                activeStr = 'active';
            }else{
                activeStr = '';
            }
            pagingHtml += '<li class="borderBox '+activeStr+'">'+minPage+'</li>';
        }
        pagingHtml += endPageHtml;
        $('.paging').html('<ul>'+pagingHtml+'</ul>');
    }
    $('.paging .borderBox').click(function(){
        $(this).addClass('active').siblings().removeClass('active');
        var page = Number($(this).html());
        creat_paging(page,pageCount);
        getDataList(page);
    });
}