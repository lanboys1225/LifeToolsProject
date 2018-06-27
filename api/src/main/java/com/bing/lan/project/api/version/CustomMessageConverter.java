package com.bing.lan.project.api.version;

import com.bing.lan.BaseDomain;
import com.bing.lan.core.api.ApiResult;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by 蓝兵 on 2018/6/23.
 */

public class CustomMessageConverter extends MappingJackson2HttpMessageConverter {

    public CustomMessageConverter() {
    }

    public CustomMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected void writeInternal(Object respContent, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        ApiResult<BaseDomain> apiResult = new ApiResult<>();
        BaseDomain domain = (BaseDomain) respContent;
        if (!StringUtils.isBlank(domain.getMsg())) {
            apiResult.setMsg(domain.getMsg());
        }
        apiResult.setData(domain);
        apiResult.setTime(new Date());
        super.writeInternal(apiResult, outputMessage);
    }

    @Override
    protected void writeInternal(Object respContent, Type type, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {

        ApiResult<BaseDomain> apiResult = new ApiResult<>();
        BaseDomain domain = (BaseDomain) respContent;
        if (!StringUtils.isBlank(domain.getMsg())) {
            apiResult.setMsg(domain.getMsg());
        }
        apiResult.setData(domain);
        apiResult.setTime(new Date());
        super.writeInternal(apiResult, type, outputMessage);
    }
}
