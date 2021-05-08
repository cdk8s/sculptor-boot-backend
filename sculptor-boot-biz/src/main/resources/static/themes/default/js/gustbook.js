$(document).ready(function () {
    $('.form-textarea').focus(function () {
        $('.otherInput').addClass('db');
        $('.form-textarea').attr('rows', '8');
    });


    $("#submitBtn").click(function () {
        var messageContent = $("#messageContent").val();
        var guestName = $("#guestName").val();
        var guestEmail = $("#guestEmail").val();
        var guestPhone = $("#guestPhone").val();
        var deviceId = $("#deviceId").val();
        var validateCode = $("#validateCode").val();

        $.ajax({
            type: "POST",
            url: ctx + '/web/cmsGuestBook/create',
            data: JSON.stringify({
                "messageContent": messageContent,
                "guestName": guestName,
                "guestEmail": guestEmail,
                "guestPhone": guestPhone,
                "deviceId": deviceId,
                "validateCode": validateCode
            }),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                alert(data.msg);
                window.location.reload();
            },
            error: function (XMLHttpRequest, textStatus) {
                // alert(XMLHttpRequest.responseText);
                var resultObject = eval("(" + XMLHttpRequest.responseText + ")");
                alert(resultObject.msg);
                $("#imageValidateCode").click();
            }
        });
    });

    $("#imageValidateCode").click(function () {
        $.ajax({
            type: "GET",
            url: ctx + '/oauth/refreshImageValidateCode',
            dataType: "json",
            success: function (data) {
                if (data.deviceId !== undefined && data.deviceId !== '') {
                    $("#imageValidateCode").attr("src", ctx + "/validate/code/imageCode?deviceId=" + data.deviceId);
                    $("#deviceId").val(data.deviceId);
                }
            },
            error: function (XMLHttpRequest, textStatus) {
                alert(XMLHttpRequest.responseText);
            }
        });
    });


});

