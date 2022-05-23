package com.logho.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(HttpServletRequest request, Exception e){
        logger.error("Request url is {}, Exception in {} ",request.getRequestURL(),e);
        ModelAndView mav = new ModelAndView();
        mav.addObject("requestUrl",request.getRequestURL());
        mav.addObject("exception",e);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) == null) {
            mav.setViewName("error/error");
            return mav;
        }
        mav.setViewName("error/404");
        return mav;
    }
}
