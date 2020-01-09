package com.o2o.web.local;

import com.o2o.dto.LocalAuthExecution;
import com.o2o.entity.LocalAuth;
import com.o2o.entity.PersonInfo;
import com.o2o.enums.LocalAuthStateEnum;
import com.o2o.exceptions.LocalAuthExeception;
import com.o2o.service.LocalAuthService;
import com.o2o.util.CodeUtil;
import com.o2o.util.HttpServletRequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/local", method = {RequestMethod.GET, RequestMethod.POST})
public class LocalAuthController {

    @Autowired
    private LocalAuthService localAuthService;


    /**
     * 将用户信息和平台账户绑定
     * @param request
     * @return
     */
    @RequestMapping(value = "/bindlocalauth", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> bindLocalAuth(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 验证码校验
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误");
            return modelMap;
        }

        // 获取信息
        String username = HttpServletRequestUtils.getString(request, "userName");
        String password = HttpServletRequestUtils.getString(request, "password");
        // 从session中获取当前用户信息(用户通过微信登录后就能获取到用户信息)
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        // 非空判断，要求账号密码及当前的用户session非空
        if (username != null && password != null && user != null && user.getUserId() != null) {
            // 创建localAuth对象，并赋值
            LocalAuth localAuth = new LocalAuth();
            localAuth.setUsername(username);
            localAuth.setPassword(password);
            localAuth.setPersonInfo(user);
            // 绑定账号
            LocalAuthExecution le = localAuthService.bindLocalAuth(localAuth);
            if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", le.getStateInfo());
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名和密码均不能为空！");
        }
        return modelMap;
    }

    /**
     * 修改密码
     * @param request
     * @return
     */
    @RequestMapping(value = "/changelocalpwd", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> changeLocalPwd(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误!");
            return modelMap;
        }
        // 获取信息
        String username = HttpServletRequestUtils.getString(request, "userName");
        String password = HttpServletRequestUtils.getString(request, "password");
        String newPasswrod = HttpServletRequestUtils.getString(request, "newPassword");
        // 从session中获取当前用户信息(用户通过微信登录后就能获取到用户信息)
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        // 非空判断，要求账号密码及当前的用户session非空
        if (username != null && password != null && newPasswrod != null && user != null && user.getUserId() != null
        && !password.equals(newPasswrod)) {
            try {
                // 查看原先账号，看看输入账号是否一致，不一致则认为是非法操作
                LocalAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
                if (localAuth == null || !localAuth.getUsername().equals(username)) {
                    // 不一致则直接退出
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "输入的账号非本次登录的账号!");
                    return modelMap;
                }
                // 修改平台账号的用户密码
                LocalAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), username, password, newPasswrod);
                if (le.getState() == LocalAuthStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", le.getStateInfo());
                }
            } catch (LocalAuthExeception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入密码");
        }

        return modelMap;
    }

    /**
     * 登录检查
     * @param request
     * @return
     */
    @RequestMapping(value = "/logincheck", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> loginCheck(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 获取是否需要进行验证码校验的标识符
        boolean needVerify = HttpServletRequestUtils.getBoolean(request, "needVerify");
        if (needVerify && !CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码错误！");
            return modelMap;
        }
        // 获取信息
        String username = HttpServletRequestUtils.getString(request, "userName");
        String password = HttpServletRequestUtils.getString(request, "password");
        // 非空校验
        if (username != null && password != null) {
            // 传入账号和密码去平台获取账号信息
            LocalAuth localAuth = localAuthService.getLocalAuthByUserNameAndPwd(username, password);
            if (localAuth != null) {
                // 若能获取到信息，则登录成功
                modelMap.put("success", true);
                // 同时在session中设置用户信息
                request.getSession().setAttribute("user", localAuth.getPersonInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "用户名或密码错误!");
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "用户名或密码均不能为空!");
        }
        return modelMap;
    }

    /**
     * 退出并注销session
     * @param request
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> logout(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        // 将用户session置为空
        request.getSession().setAttribute("user", null);
        modelMap.put("success", true);
        return modelMap;
    }
}
