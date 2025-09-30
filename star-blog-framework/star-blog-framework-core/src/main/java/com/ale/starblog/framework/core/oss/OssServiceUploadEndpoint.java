package com.ale.starblog.framework.core.oss;

import ch.qos.logback.core.util.FileSize;
import cn.hutool.core.io.unit.DataSizeUtil;
import com.ale.starblog.framework.common.constants.StringConstants;
import com.ale.starblog.framework.common.domain.JsonResult;
import com.ale.starblog.framework.common.domain.Result;
import com.ale.starblog.framework.common.support.facade.RequestMethodMatcher;
import com.ale.starblog.framework.common.support.facade.RequestUriMatcher;
import com.ale.starblog.framework.common.utils.ImageUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * OSS对象存储服务端点
 *
 * @author Ale
 * @version 1.0.0 2025/9/28 14:15
 */
@Slf4j
public final class OssServiceUploadEndpoint extends OssPathMatchEndpoint {

    /**
     * OSS对象存储上传路径模式
     */
    private static final String OSS_UPLOAD_PATH_PATTERN = "/oss/upload/{ossProvider}/{fileType}";

    /**
     * 支持的文件类型
     */
    private static final Set<String> SUPPORTED_FILE_TYPES = Set.of("image", "file");

    public OssServiceUploadEndpoint(ObjectProvider<OssService> ossServices) {
        super(ossServices);
    }

    /**
     * 获取请求URI匹配器
     *
     * @return 请求URI匹配器
     */
    @Override
    public RequestUriMatcher getRequestUriMatcher() {
        return uri -> this.pathMatcher.match(OSS_UPLOAD_PATH_PATTERN, uri);
    }

    /**
     * 获取请求方法匹配器
     *
     * @return 请求方法匹配器
     */
    @Override
    public RequestMethodMatcher getRequestMethodMatcher() {
        return method -> HttpMethod.POST == method;
    }


    @Override
    protected String providePathPattern() {
        return OSS_UPLOAD_PATH_PATTERN;
    }

    @Override
    protected Result<?> handle(HttpServletRequest request, HttpServletResponse response, OssService ossService, Map<String, String> variables) throws Exception {
        String fileType = variables.get("fileType");
        if (!SUPPORTED_FILE_TYPES.contains(fileType)) {
            return JsonResult.fail("不支持的文件类型：{}", fileType);
        }
        return this.handleUpload(fileType + StringConstants.SLASH, ossService, request);
    }

    /**
     * 处理文件上传
     *
     * @param objectKeyPrefix 对象Key前缀
     * @param ossService      OSS服务实现
     * @param request         请求对象
     * @return 结果对象
     * @throws IOException 异常
     */
    private JsonResult<?> handleUpload(String objectKeyPrefix, OssService ossService, HttpServletRequest request) throws IOException {
        if (!MultipartResolutionDelegate.isMultipartRequest(request)) {
            return JsonResult.fail("非上传文件请求");
        }

        // 在过滤器中可能request尚未被包装为MultipartHttpServletRequest，因此需要手动包装一下
        MultipartHttpServletRequest multipartRequest =
            Objects.requireNonNullElseGet(
                WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class),
                () -> new StandardMultipartHttpServletRequest(request)
            );
        MultiValueMap<String, MultipartFile> multiFileMap = multipartRequest.getMultiFileMap();
        var objectKeyMap = new LinkedMultiValueMap<String, String>(multiFileMap.size());
        for (Map.Entry<String, List<MultipartFile>> entry : multiFileMap.entrySet()) {
            for (MultipartFile file : entry.getValue()) {
                if (file.getSize() == 0) {
                    continue;
                }

                String objectKey = ossService.upload(
                    objectKeyPrefix,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    (StringUtils.startsWith(file.getContentType(), "image/") || objectKeyPrefix.startsWith("image/") && file.getSize() > FileSize.KB_COEFFICIENT * 500)
                        ? new ByteArrayInputStream(ImageUtils.compressImage(file.getInputStream()).toByteArray())
                        : file.getInputStream()
                );

                objectKeyMap.add(entry.getKey(), objectKey);
                log.info("{} - 文件[{}]上传成功，文件对象Key：{}，文件大小：{}", entry.getKey(), file.getOriginalFilename(), objectKey, DataSizeUtil.format(file.getSize()));
            }
        }

        if (objectKeyMap.isEmpty()) {
            return JsonResult.fail("上传文件为空，请检查");
        }

        if (objectKeyMap.size() > 1) {
            return JsonResult.success(objectKeyMap);
        }

        var objectKeys = objectKeyMap.entrySet().iterator().next().getValue();
        return JsonResult.success(objectKeys.size() > 1 ? objectKeys : objectKeys.getFirst());
    }
}
