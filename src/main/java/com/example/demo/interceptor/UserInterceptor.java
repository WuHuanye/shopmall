package com.example.demo.interceptor;

import com.example.demo.pojo.User;
import com.example.demo.vo.SessionUser;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户过滤器
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    /*
     * 进入controller层之前拦截请求
     * 返回值：表示是否将当前的请求拦截下来  false：拦截请求，请求别终止。true：请求不被拦截，继续执行
     * Object obj:表示被拦的请求的目标对象（controller中方法）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从session中获取author，判断是否登录
        SessionUser user = (SessionUser)request.getSession().getAttribute("user");
//        System.out.println("用户id：："+user.getUserId());
        if (user == null){      //用户没有登录直接跳转到非首页
            response.sendRedirect(request.getContextPath()+"/nologin");
            return false;
        }
        if(user.getUserId()==0){    //用户没有登录跳转到首页后再跳转到其他页面
            response.sendRedirect(request.getContextPath()+"/nologin");
            return false;
        }
        return true;
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     * 通过ModelAndView参数改变显示的视图，或发往视图的方法
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    /*
     * 视图渲染之后的操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
