package com.online.www.result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author TopView
 */

public class ResultUtil {

    public static <T> void printCode(HttpServletResponse response, CommonResult<T> result, Integer statusCode) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setStatus(statusCode);
        PrintWriter writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        writer.print(json);
        writer.close();
        response.flushBuffer();
    }
}
