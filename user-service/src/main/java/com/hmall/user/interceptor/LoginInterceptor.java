package com.hmall.user.interceptor;

import com.hmall.common.utils.UserContext;

import com.hmall.user.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTool jwtTool;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的 token
        String token = request.getHeader("authorization");
        Long userId;
        if (token != null && !token.isBlank()) {
            // 2.校验token
            userId = jwtTool.parseToken(token);
        } else {
            // 尝试读取内部调用携带的 userInfo 头
            String userInfoHeader = request.getHeader("userInfo");
            if (userInfoHeader == null || userInfoHeader.isBlank()) {
                throw new com.hmall.common.exception.UnauthorizedException("未登录");
            }
            try {
                userId = Long.valueOf(userInfoHeader);
            } catch (NumberFormatException e) {
                throw new com.hmall.common.exception.UnauthorizedException("无效的用户信息", e);
            }
        }
        // 3.存入上下文
        UserContext.setUser(userId);
        // 4.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理用户
        UserContext.removeUser();
    }
}
