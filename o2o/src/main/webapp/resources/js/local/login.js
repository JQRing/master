$(function() {
    var loginUrl = '/o2o/local/logincheck';
    // 登录次数，累计登录失败三次弹出验证码
    var loginCount = 0;
    var usertype = getQueryString("usertype");


    $('#submit').click(function() {
        var userName = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        // 登录失败三次需要验证码
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }
        // 访问后台进行登录验证
        $.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                userName : userName,
                password : password,
                verifyCodeActual : verifyCodeActual,
                needVerify : needVerify
            },
            success : function(data) {
                if (data.success) {
                    $.toast('登录成功！');
                    if (usertype == 1) {
                        // 若用户在前段展示系统页面则自动链接到前端展示系统首页
                        window.location.href = '/o2o/frontend/index';
                    } else {
                        // window.location.href = '/o2o/shopadmin/shoplist';
                        window.open("/o2o/shopadmin/shoplist");
                    }
                } else {
                    $.toast('登录失败！'+ data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        $('#verifyPart').show();
                    }
                }
            }
        });
    });

    $('#register').click(function() {
        window.location.href = '/o2o/shopadmin/register';
    });
});