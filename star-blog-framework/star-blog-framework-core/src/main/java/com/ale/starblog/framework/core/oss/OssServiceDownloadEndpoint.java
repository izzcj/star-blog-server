package com.ale.starblog.framework.core.oss;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import com.ale.starblog.framework.common.constants.StringConstants;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.Result;
import com.ale.starblog.framework.common.support.facade.RequestMethodMatcher;
import com.ale.starblog.framework.common.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * OSS对象下载服务端点
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:12
 */
@Slf4j
public final class OssServiceDownloadEndpoint extends OssPathMatchEndpoint {

    /**
     * OSS对象存储下载路径模式
     */
    private static final String OSS_DOWNLOAD_PATH_PATTERN = "/oss/download/{ossProvider}";

    public OssServiceDownloadEndpoint(ObjectProvider<OssService> ossServices) {
        super(ossServices);
    }

    @Override
    public RequestMethodMatcher getRequestMethodMatcher() {
        return method -> HttpMethod.GET == method;
    }

    @Override
    protected String providePathPattern() {
        return OSS_DOWNLOAD_PATH_PATTERN;
    }

    @Override
    protected Result<?> handle(HttpServletRequest request, HttpServletResponse response, OssService ossService, Map<String, String> variables) throws Exception {
        String objectKey = request.getParameter("objectKey");
        if (StrUtil.isBlank(objectKey)) {
            return JsonResult.fail("要下载的对象文件的Key不能为空");
        }

        boolean multiDownload = objectKey.indexOf(StringConstants.COMMA) > 0;
        if (multiDownload) {
            String[] keys = objectKey.split(StringConstants.COMMA);
            for (String key : keys) {
                if (StrUtil.isBlank(key)) {
                    return JsonResult.fail("要下载的对象文件的Key存在空值");
                }
            }

            return this.doMultiDownload(ossService, keys, response);
        } else {
            return this.doSingleDownload(ossService, objectKey, response);
        }
    }

    /**
     * 单文件下载
     *
     * @param ossService OssService
     * @param objectKey  对象Key
     * @param response   响应对象
     * @return 结果对象
     * @throws IOException IO异常
     */
    private JsonResult<?> doSingleDownload(OssService ossService, String objectKey, HttpServletResponse response) throws IOException {
        try (
            InputStream inputStream = ossService.download(objectKey)
        ) {
            ServletUtils.responseFile(
                response,
                FileNameUtil.getName(objectKey),
                inputStream
            );
        }

        return null;
    }

    /**
     * 多文件下载
     *
     * @param ossService OssService
     * @param keys       文件Key数组
     * @param response   响应对象
     * @return 结果对象
     * @throws IOException IO异常
     */
    private JsonResult<?> doMultiDownload(OssService ossService, String[] keys, HttpServletResponse response) throws IOException {
        InputStream[] inputStreams = new InputStream[keys.length];
        String[] names = new String[keys.length];
        int index = 0;

        try {
            for (String key : keys) {
                inputStreams[index] = ossService.download(key);
                names[index++] = FileNameUtil.getName(key);
            }
            ServletUtils.responseFile(
                response,
                "download.zip",
                os -> ZipUtil.zip(
                    os,
                    names,
                    inputStreams
                )
            );
        } finally {
            for (InputStream inputStream : inputStreams) {
                inputStream.close();
            }
        }

        return null;
    }
}
