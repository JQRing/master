$(function() {
    var url = '/o2o/local/changelocalpwd';
    var usertype = getQueryString("usertype");

    $('#submit').click(function() {
        var userName = $('#userName').val();
        var password = $('#password').val();
        var newPassword = $('#newPassword').val();

        if (password != newPassword) {
            $.toast('两次输入的密码不一致!');
            return;
        }
        var formData = new FormData();
        formData.append('userName', userName);
        formData.append('password', password);
        formData.append('newPassword', newPassword);
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
            url : url,
            type : 'POST',
            data : formData,
            contentType : false,
            processData : false,
            cache : false,
            success : function(data) {
                if (data.success) {
                    $.toast('提交成功！');
                    if (usertype == 1){
                        window.location.href = '/o2o/frontend/index';
                    } else {
                        window.location.href = '/o2o/shopadmin/shoplist';
                    }
                } else {
                    $.toast('提交失败！');
                    $('#captcha_img').click();
                }
            }
        });
    });

    $('#back').click(function() {
        window.location.href = '/o2o/shopadmin/shoplist';
    });
});
