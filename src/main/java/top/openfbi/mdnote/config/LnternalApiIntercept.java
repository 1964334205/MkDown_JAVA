package top.openfbi.mdnote.config;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import top.openfbi.mdnote.common.Result;
import top.openfbi.mdnote.common.ResultStatus;
import top.openfbi.mdnote.utils.JsonResponse;

@Component
public class LnternalApiIntercept implements HandlerInterceptor {
    /**
     * 日志
     */
    private static final Logger logger
            = LoggerFactory.getLogger(LnternalApiIntercept.class);


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 内部接口访问拦截
        String forwardedIp = request.getHeader("x-forwarded-for");
        if (!(forwardedIp.equals("127.0.0.1") || forwardedIp.equals("::1"))){
            logger.error("请求被拦截，试图非法进入内部测试接口，拦截URL: {}，访问者IP地址: {}",request.getRequestURL(),forwardedIp);
            // 序列化状态码
            String json = JSON.toJSONString(Result.failure( ResultStatus.CLIENT_IP_NOT_ALLOWED,""));
            // 返回数据
            JsonResponse.returnJsonResponse(response, json,"试图非法进入内部测试接口,异常信息");
            return false;
        }

        return true;
    }
}
