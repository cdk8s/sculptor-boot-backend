function gotoPageNum(pageNum) {
    var queryString = window.location.search;
    queryString = queryString.substring(1, queryString.length);// 去掉前面的 ? 号

    var splitList = queryString.split("&");
    var queryStringResult = "";
    for (var i = 0; i < splitList.length; i++) {
        if (splitList[i].indexOf("pageNum=") == -1) {
            // 不包含 pageNum 则拼接
            queryStringResult = queryStringResult + "&" + splitList[i];
        }
    }
    queryStringResult = queryStringResult.substring(1, queryStringResult.length);// 去掉拼接的第一个 & 号

    var gotoUrl = window.location.protocol + "//" + window.location.host + window.location.pathname;
    if (queryStringResult == null || queryStringResult === '') {
        gotoUrl = gotoUrl + "?pageNum=" + pageNum;
    } else {
        gotoUrl = gotoUrl + "?" + queryStringResult + "&pageNum=" + pageNum;
    }

    window.location.href = gotoUrl;
}

$(document).ready(function () {
    //返回顶部
    $("#gototop").click(function () {
        $("html,body").animate({scrollTop: 0}, 800);
        return false;
    });
    $("#gotocate").click(function () {
        $("html,body").animate({scrollTop: $("#categories").offset().top - 50}, 800);
        return false;
    });


    //scrollTop
    $(window).scroll(function () {
        var scrolls = $(window).scrollTop()
        if (scrolls > 10) {
            $(".navbar").addClass("small-nav");
            $(".flash").addClass("toflash");
        } else {
            $(".navbar").removeClass("small-nav");
            $(".flash").removeClass("toflash");
        }
    });

    if ($(window).width() > 768) {
        //鼠标划过就展开子菜单
        $('ul.nav li.dropdown').hover(function () {
            $(this).find('.dropdown-menu').stop(true, true).slideDown();
        }, function () {
            $(this).find('.dropdown-menu').stop(true, true).slideUp();
        });
    }

    $("ul.menu_body").each(function () {
        if ($(this).text().replace(/[\r\n ]/g, "").length <= 0) {
            $(this).prev().remove();
        } //去掉span
    });

    $("#firstpane span.menu_head").click(function () {
        var spanatt = $(this).next("ul.menu_body").css('display');
        if (spanatt == "block") {
            var spantext = "+";
            $(this).prev().removeClass("left_active");
        } else {
            var spantext = "-";
            $(this).prev().addClass("left_active");
        }
        $(this).html(spantext).addClass("current").next("ul.menu_body").slideToggle(300).siblings("ul.menu_body");
    });


});
