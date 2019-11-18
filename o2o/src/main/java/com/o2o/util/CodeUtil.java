package com.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        // 实际验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtils.getString(request, "verifyCodeActual");
        if (verifyCodeActual.equalsIgnoreCase(verifyCodeExpected)) {
            return true;
        }
        return false;
    }
}
