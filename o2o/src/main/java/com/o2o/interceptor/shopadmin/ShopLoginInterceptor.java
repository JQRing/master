package com.o2o.interceptor.shopadmin;

import com.o2o.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShopLoginInterceptor extends HandlerInterceptorAdapter {

    /**
     * 主要做事前拦截，及用户操作发生前，改写prehandle里的逻辑，进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从session中取出用户信息
        Object userObj = request.getSession().getAttribute("user");
        if (userObj != null) {
            // 若用户信息不为空则将session中的用户信息转换成PersonInfo实体类
            PersonInfo user = (PersonInfo) userObj;
            // 空值判断，确保userID不为空并且该账号的可用状态为1
            if (user != null && user.getUserId() != null && user.getUserId() > 0 && user.getEnableStatus() == 1)
                return true;
        }
        // 若不满足登录验证，则跳转到登录界面
       /* PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + request.getContextPath()
                + "/local/login?usertype=2','_self')");
        out.println("</script>");
        out.println("</html>");*/
       response.sendRedirect(request.getContextPath() + "/local/login?usertype=2");
        return false;
    }
}
