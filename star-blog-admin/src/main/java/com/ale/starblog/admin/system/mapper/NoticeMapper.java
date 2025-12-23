package com.ale.starblog.admin.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ale.starblog.admin.system.domain.entity.Notice;

/**
 * 系统公告Mapper
 *
 * @author Ale
 * @version 1.0.0 2025-12-23 15:17:42
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
}
