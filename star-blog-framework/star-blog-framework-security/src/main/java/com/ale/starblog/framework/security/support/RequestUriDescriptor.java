package com.ale.starblog.framework.security.support;

import com.ale.starblog.framework.common.enumeration.RequestMethod;
import lombok.Data;

import java.util.List;

/**
 * 请求URI描述符
 *
 * @author Ale
 * @version 1.0.0
 * @since 2024/6/28
 **/
@Data
public class RequestUriDescriptor {

    /**
     * 请求URI
     */
    private String uri;

    /**
     * 请求方法
     */
    private List<RequestMethod> methods;
}
