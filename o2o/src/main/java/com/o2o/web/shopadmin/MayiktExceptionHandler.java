package com.o2o.web.shopadmin;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MayiktExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    @ResponseBody
    public Map<String, Object> exceptionHandler() {
        Map<String, Object> map = new HashMap<>();
        map.put("response", "404");
        map.put("errMsg", "系统错误");
        return map;
    }
}
