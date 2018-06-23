package com.bing.lan.project.api;

import com.bing.lan.core.api.ApiResult;
import com.bing.lan.core.api.LogUtil;
import com.bing.lan.exception.BaseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.bing.lan.core.api.ApiResult.HTTP_CODE_FAIL;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_FAIL_MSG;
import static com.bing.lan.core.api.ApiResult.HTTP_CODE_SERVICE_EXCEPTION;

/**
 * SpringMvc自定义的异常处理器
 */
public class CustomExceptionHandler implements HandlerExceptionResolver {

    private final LogUtil log = LogUtil.getLogUtil(getClass(), LogUtil.LOG_VERBOSE);

    @Autowired
    private MappingJackson2JsonView mappingJackson2JsonView;

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse, Object o, Exception e) {
        ApiResult<Object> apiResult = new ApiResult<Object>();
        apiResult.setTime(new Date());
        ModelAndView mav = new ModelAndView();
        mav.setView(mappingJackson2JsonView);
        mav.addObject(apiResult);

        if (e instanceof BaseException) {
            log.e("捕获到业务异常, 来源:" + o, e);
            apiResult.setCode(HTTP_CODE_SERVICE_EXCEPTION);
            apiResult.setMsg(e.getMessage());
            return mav;
        }

        log.e("捕获到未处理的异常, 来源:" + o, e);
        apiResult.setCode(HTTP_CODE_FAIL);
        apiResult.setMsg(HTTP_CODE_FAIL_MSG);

        return mav;
    }
}
