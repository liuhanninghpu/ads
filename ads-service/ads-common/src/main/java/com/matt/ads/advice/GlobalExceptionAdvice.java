package com.matt.ads.advice;


import com.matt.ads.exception.AdsException;
import com.matt.ads.vo.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(value = AdsException.class)
    public CommonResponse<String> handlerAdException(HttpServletRequest req, AdsException ex){
        CommonResponse<String> response = new CommonResponse<>(-1,"bussiness error");
        response.setData(ex.getMessage());
        return response;
    }
}
