function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split("=");
        if (pair[0] == variable) {
            return pair[1];
        }
    }
    return (false);
}

$(function () {
    var key = $('#searchKeyword').val();
    if (key.length > 0) {
        var body = $('#articleListContent');
        body.removeHighlight();
        var arr = key.split(' ');
        if (arr.length > 0) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] !== '') {
                    body.highlight(arr[i]);
                }
            }
        }
    }

    $("#searchKeywordBtn").click(function () {
        var key = $('#searchKeyword').val();
        var categoryId = getQueryVariable("categoryId");
        if (key.length > 0) {
            var gotoUrl = window.location.protocol + "//" + window.location.host + window.location.pathname + "?searchKeyword=" + key;
            if (categoryId.length > 0) {
                gotoUrl = gotoUrl + "&categoryId=" + categoryId;
            }
            window.location.href = gotoUrl;
        }
    });


});
