package com.ego.order.interceptor;

import it.ego.commons.pojo.EgoResult;
import it.ego.commons.utils.CookieUtils;
import it.ego.commons.utils.HttpClientUtil;
import it.ego.commons.utils.JsonUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName LoginInterceptor
 * @Description TODD
 * @AUTHOR sh-wangbs
 * @Date 2019/4/1213:50
 * @Version 1.0
 **/
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
     // 判断用户是否登录
        String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        if(token!=null||!token.equals("")){
            //访问用户登录的接口，验证用户信息是否正确
            String egoResult = HttpClientUtil.doPost("http://localhost:8084/user/token/" + token);
            EgoResult egoResult1 = JsonUtils.jsonToPojo(egoResult, EgoResult.class);
            if (egoResult1.getStatus()==200){
                //用户验证成功
                return true;
            }else{
                //调到登录页面 需要携带被拦截页面的地址
                String num = httpServletRequest.getParameter("num");
                String requestURL = httpServletRequest.getRequestURL().toString();
                System.out.println(requestURL);
                httpServletResponse.sendRedirect("http://localhost:8084/user/showLogin?interurl="+httpServletRequest.getRequestURL()+"%3Fnum="+num);
                return false;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
