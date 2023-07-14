package top.openfbi.mdnote.utils;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;

public class JsonResponse {
    private static final Logger logger
            = LoggerFactory.getLogger(JsonResponse.class);
    /*返回客户端数据*/
    public static void returnJsonResponse(HttpServletResponse response, String json,String reeorDescription) throws Exception {
        // 设置编码格式
        response.setCharacterEncoding("UTF-8");
        // 设置请求头格式
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = null;
        // 写入返回值
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            logger.warn(reeorDescription+": {}",e);
            throw e;
        } finally {
            // 关闭写入流
            if (writer != null)
                writer.close();
        }
    }
}
