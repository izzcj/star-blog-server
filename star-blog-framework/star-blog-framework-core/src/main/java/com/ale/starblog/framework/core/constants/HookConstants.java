package com.ale.starblog.framework.core.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * 钩子常量
 *
 * @author Ale
 * @version 1.0.0
 * @since 2025/4/8
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HookConstants {

    /**
     * 查询参数-key
     */
    public static final String QUERY_KEY = "query";

    /**
     * 创建实体DTO-key
     */
    public static final String CREATE_DTO_KEY = "createDTO";

    /**
     * 创建实体DTO列表-key
     */
    public static final String CREATE_DTO_LIST_KEY = "createDTOList";

    /**
     * 修改实体DTO-key
     */
    public static final String MODIFY_DTO_KEY = "modifyDTO";

    /**
     * 修改实体DTO映射关系-key
     */
    public static final String MODIFY_DTO_MAP_KEY = "modifyDTOMap";

    /**
     * 旧实体-key
     */
    public static final String OLD_ENTITY_KEY = "oldEntity";

    /**
     * 旧实体映射关系-key
     */
    public static final String OLD_ENTITY_MAP_KEY = "oldEntityMap";
}
