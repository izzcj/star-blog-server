/*
 Navicat Premium Data Transfer

 Source Server         : 本地-pgsql
 Source Server Type    : PostgreSQL
 Source Server Version : 170000 (170000)
 Source Host           : localhost:5432
 Source Catalog        : star_blog
 Source Schema         : star_blog

 Target Server Type    : PostgreSQL
 Target Server Version : 170000 (170000)
 File Encoding         : 65001

 Date: 30/01/2026 17:03:56
*/

-- 创建数据库
CREATE DATABASE star_blog;
-- 切换数据库
\c star_blog
-- 创建schema
CREATE SCHEMA IF NOT EXISTS star_blog;
-- 设置默认schema
SET search_path TO star_blog;

-- ----------------------------
-- Table structure for blog_activity
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."blog_activity";
CREATE TABLE "star_blog"."blog_activity" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "content" varchar(512) COLLATE "pg_catalog"."default" NOT NULL,
  "article_id" int8 NOT NULL,
  "ref_id" int8,
  "user_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "star_blog"."blog_activity"."id" IS '主键';
COMMENT ON COLUMN "star_blog"."blog_activity"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."blog_activity"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."blog_activity"."update_time" IS '最后更新时间';
COMMENT ON COLUMN "star_blog"."blog_activity"."type" IS '动态类型';
COMMENT ON COLUMN "star_blog"."blog_activity"."content" IS '动态内容';
COMMENT ON COLUMN "star_blog"."blog_activity"."article_id" IS '文章ID';
COMMENT ON COLUMN "star_blog"."blog_activity"."ref_id" IS '引用ID';
COMMENT ON COLUMN "star_blog"."blog_activity"."user_id" IS '用户ID';
COMMENT ON TABLE "star_blog"."blog_activity" IS '动态表';

-- ----------------------------
-- Records of blog_activity
-- ----------------------------

-- ----------------------------
-- Table structure for blog_article
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."blog_article";
CREATE TABLE "star_blog"."blog_article" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "create_by" int8 NOT NULL,
  "update_by" int8 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "title" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "summary" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "cover_image" varchar(255) COLLATE "pg_catalog"."default",
  "view_count" int8 DEFAULT 0,
  "status" varchar(32) COLLATE "pg_catalog"."default",
  "top" bool DEFAULT false,
  "category" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "publish_time" timestamp(6),
  "recommended" bool
)
;
COMMENT ON COLUMN "star_blog"."blog_article"."id" IS '主键';
COMMENT ON COLUMN "star_blog"."blog_article"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."blog_article"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."blog_article"."update_time" IS '最后更新时间';
COMMENT ON COLUMN "star_blog"."blog_article"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."blog_article"."update_by" IS '最后更新人';
COMMENT ON COLUMN "star_blog"."blog_article"."remark" IS '备注';
COMMENT ON COLUMN "star_blog"."blog_article"."title" IS '标题';
COMMENT ON COLUMN "star_blog"."blog_article"."content" IS '内容';
COMMENT ON COLUMN "star_blog"."blog_article"."summary" IS '摘要';
COMMENT ON COLUMN "star_blog"."blog_article"."cover_image" IS '封面图片';
COMMENT ON COLUMN "star_blog"."blog_article"."view_count" IS '浏览量';
COMMENT ON COLUMN "star_blog"."blog_article"."status" IS '状态';
COMMENT ON COLUMN "star_blog"."blog_article"."top" IS '是否置顶';
COMMENT ON COLUMN "star_blog"."blog_article"."category" IS '分类';
COMMENT ON COLUMN "star_blog"."blog_article"."publish_time" IS '发布时间';
COMMENT ON COLUMN "star_blog"."blog_article"."recommended" IS '是否推荐';
COMMENT ON TABLE "star_blog"."blog_article" IS '文章表';

-- ----------------------------
-- Records of blog_article
-- ----------------------------

-- ----------------------------
-- Table structure for blog_article_tag
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."blog_article_tag";
CREATE TABLE "star_blog"."blog_article_tag" (
  "id" int8 NOT NULL,
  "deleted" bool DEFAULT false,
  "create_time" timestamp(6),
  "update_time" timestamp(6),
  "article_id" int8,
  "tag_id" int8
)
;
COMMENT ON COLUMN "star_blog"."blog_article_tag"."id" IS '主键';
COMMENT ON COLUMN "star_blog"."blog_article_tag"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."blog_article_tag"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."blog_article_tag"."update_time" IS '最后更新时间';
COMMENT ON COLUMN "star_blog"."blog_article_tag"."article_id" IS '文章ID';
COMMENT ON COLUMN "star_blog"."blog_article_tag"."tag_id" IS '标签ID';
COMMENT ON TABLE "star_blog"."blog_article_tag" IS '文章标签关联表';

-- ----------------------------
-- Records of blog_article_tag
-- ----------------------------

-- ----------------------------
-- Table structure for blog_comment
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."blog_comment";
CREATE TABLE "star_blog"."blog_comment" (
  "id" int8 NOT NULL,
  "content" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "parent_id" int8 NOT NULL,
  "article_id" int8 NOT NULL,
  "root_id" int8 NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6),
  "deleted" bool NOT NULL DEFAULT false,
  "status" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "like_count" int8,
  "user_id" int8 NOT NULL,
  "reply_user_id" int8,
  "reject_reason" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "star_blog"."blog_comment"."content" IS '回复内容';
COMMENT ON COLUMN "star_blog"."blog_comment"."parent_id" IS '父级ID';
COMMENT ON COLUMN "star_blog"."blog_comment"."article_id" IS '文章ID';
COMMENT ON COLUMN "star_blog"."blog_comment"."root_id" IS '根评论ID';
COMMENT ON COLUMN "star_blog"."blog_comment"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."blog_comment"."update_time" IS '更新时间';
COMMENT ON COLUMN "star_blog"."blog_comment"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."blog_comment"."status" IS '状态';
COMMENT ON COLUMN "star_blog"."blog_comment"."like_count" IS '点赞数';
COMMENT ON COLUMN "star_blog"."blog_comment"."user_id" IS '评论用户ID';
COMMENT ON COLUMN "star_blog"."blog_comment"."reply_user_id" IS '回复目标用户ID';
COMMENT ON COLUMN "star_blog"."blog_comment"."reject_reason" IS '驳回原因';
COMMENT ON TABLE "star_blog"."blog_comment" IS '评论表';

-- ----------------------------
-- Records of blog_comment
-- ----------------------------

-- ----------------------------
-- Table structure for blog_comment_like
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."blog_comment_like";
CREATE TABLE "star_blog"."blog_comment_like" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "user_id" int8 NOT NULL,
  "comment_id" int8 NOT NULL
)
;
COMMENT ON COLUMN "star_blog"."blog_comment_like"."id" IS 'ID';
COMMENT ON COLUMN "star_blog"."blog_comment_like"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."blog_comment_like"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."blog_comment_like"."update_time" IS '修改时间';
COMMENT ON COLUMN "star_blog"."blog_comment_like"."user_id" IS '用户ID';
COMMENT ON COLUMN "star_blog"."blog_comment_like"."comment_id" IS '评论ID';
COMMENT ON TABLE "star_blog"."blog_comment_like" IS '评论点赞';

-- ----------------------------
-- Records of blog_comment_like
-- ----------------------------


-- ----------------------------
-- Table structure for blog_tag
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."blog_tag";
CREATE TABLE "star_blog"."blog_tag" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6) NOT NULL,
  "create_by" int8 NOT NULL,
  "update_by" int8 NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(512) COLLATE "pg_catalog"."default",
  "color" varchar(128) COLLATE "pg_catalog"."default",
  "icon" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "star_blog"."blog_tag"."id" IS '主键';
COMMENT ON COLUMN "star_blog"."blog_tag"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."blog_tag"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."blog_tag"."update_time" IS '最后更新时间';
COMMENT ON COLUMN "star_blog"."blog_tag"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."blog_tag"."update_by" IS '最后更新人';
COMMENT ON COLUMN "star_blog"."blog_tag"."remark" IS '备注';
COMMENT ON COLUMN "star_blog"."blog_tag"."name" IS '名称';
COMMENT ON COLUMN "star_blog"."blog_tag"."description" IS '描述';
COMMENT ON COLUMN "star_blog"."blog_tag"."color" IS '颜色';
COMMENT ON COLUMN "star_blog"."blog_tag"."icon" IS '图标';
COMMENT ON TABLE "star_blog"."blog_tag" IS '标签表';

-- ----------------------------
-- Records of blog_tag
-- ----------------------------
INSERT INTO "star_blog"."blog_tag" VALUES (1993947052503171074, 'f', '2025-11-27 15:36:37.731037', '2025-12-02 14:45:46.939037', 1, 1, NULL, 'PostgresSql', '', '#ffffff', NULL);
INSERT INTO "star_blog"."blog_tag" VALUES (1993946630124175362, 'f', '2025-11-27 15:34:57.030005', '2025-12-02 14:45:54.287098', 1, 1, NULL, 'Javascript', '', '#ffffff', NULL);
INSERT INTO "star_blog"."blog_tag" VALUES (1993946391015292930, 'f', '2025-11-27 15:34:00.010683', '2025-12-02 14:51:06.008511', 1, 1, NULL, 'Java', '', '#ffffff', '');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_config";
CREATE TABLE "star_blog"."sys_config" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "create_by" int8 NOT NULL,
  "update_time" timestamp(6),
  "update_by" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "category" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "key" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "value" text COLLATE "pg_catalog"."default",
  "data_source_type" varchar(32) COLLATE "pg_catalog"."default",
  "data_source_config" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int4,
  "deletable" bool NOT NULL DEFAULT true
)
;
COMMENT ON COLUMN "star_blog"."sys_config"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_config"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_config"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_config"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."sys_config"."update_time" IS '更新时间';
COMMENT ON COLUMN "star_blog"."sys_config"."update_by" IS '更新人';
COMMENT ON COLUMN "star_blog"."sys_config"."remark" IS '备注';
COMMENT ON COLUMN "star_blog"."sys_config"."category" IS '类别';
COMMENT ON COLUMN "star_blog"."sys_config"."name" IS '配置名称';
COMMENT ON COLUMN "star_blog"."sys_config"."type" IS '配置类型';
COMMENT ON COLUMN "star_blog"."sys_config"."key" IS '配置key';
COMMENT ON COLUMN "star_blog"."sys_config"."value" IS '配置值';
COMMENT ON COLUMN "star_blog"."sys_config"."data_source_type" IS '数据源类型（字典、枚举、接口）';
COMMENT ON COLUMN "star_blog"."sys_config"."data_source_config" IS '数据源配置';
COMMENT ON COLUMN "star_blog"."sys_config"."sort" IS '排序';
COMMENT ON COLUMN "star_blog"."sys_config"."deletable" IS '是否允许删除';
COMMENT ON TABLE "star_blog"."sys_config" IS '系统配置';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO "star_blog"."sys_config" VALUES (2017145600312807426, 'f', '2026-01-30 15:59:22.527377', 1, '2026-01-30 16:13:54.739931', 1, NULL, 'system-config', '登录页背景图', 'IMAGE', 'login-bg', NULL, NULL, NULL, 2, 'f');
INSERT INTO "star_blog"."sys_config" VALUES (1995695140959887361, 'f', '2025-12-02 11:22:54.49236', 1, '2025-12-02 11:56:36.366314', 1, NULL, 'system-config', '是否启用评论审核', 'BOOLEAN', 'enable-comment-audit', 'true', NULL, NULL, 3, 'f');
INSERT INTO "star_blog"."sys_config" VALUES (1988154623745261569, 'f', '2025-11-11 15:59:35.085239', 1, '2025-11-27 11:28:44.851546', 1, NULL, 'home-config', '首页标题', 'TEXT', 'home-title', '十月秋雨凉', NULL, NULL, 1, 'f');
INSERT INTO "star_blog"."sys_config" VALUES (1988132356466343937, 'f', '2025-11-11 14:31:06.168428', 1, '2025-11-19 15:32:54.91734', 1, NULL, 'home-config', '首页格言', 'JSON', 'home-motto', '[
  "醉后不知天在水，满船清梦压星河",
  "人道洛阳花似锦，偏我来时不逢春",
  "愿你长歌乘风去，归来仍少年",
  "晚安"
]', NULL, NULL, 2, 'f');
INSERT INTO "star_blog"."sys_config" VALUES (1988182434421854209, 'f', '2025-11-11 17:50:05.669271', 1, '2025-11-27 10:18:43.053287', 1, NULL, 'home-config', '网站logo', 'IMAGE', 'logo', NULL, NULL, NULL, 3, 'f');
INSERT INTO "star_blog"."sys_config" VALUES (1997904731911155714, 'f', '2025-12-08 13:43:02.021758', 1, '2025-12-31 15:01:03.589613', 1, NULL, 'home-config', '导航栏分类', 'MULTI_SELECT', 'home-article-category-navbar', '["server-develop","web-develop","daily-life","ai-programme"]', 'DICT', 'article-category', 4, 'f');
INSERT INTO "star_blog"."sys_config" VALUES (2017149625594728450, 'f', '2026-01-30 16:15:22.227085', 1, '2026-01-30 16:15:32.629762', 1, NULL, 'system-config', '网站背景图', 'IMAGE', 'web-bg', NULL, NULL, NULL, 1, 'f');

-- ----------------------------
-- Table structure for sys_daily_stat_info
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_daily_stat_info";
CREATE TABLE "star_blog"."sys_daily_stat_info" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6),
  "view_count" int4 NOT NULL,
  "register_count" int4 NOT NULL,
  "active_count" int4 NOT NULL,
  "stat_date" date NOT NULL
)
;
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."update_time" IS '修改时间';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."view_count" IS '访问数（单个IP多次访问算一次）';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."register_count" IS '注册用户数';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."active_count" IS '活跃用户数';
COMMENT ON COLUMN "star_blog"."sys_daily_stat_info"."stat_date" IS '统计日期';
COMMENT ON TABLE "star_blog"."sys_daily_stat_info" IS '系统每日统计信息';

-- ----------------------------
-- Records of sys_daily_stat_info
-- ----------------------------


-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_dict_data";
CREATE TABLE "star_blog"."sys_dict_data" (
  "id" int8 NOT NULL,
  "dict_label" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_value" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "css_class" varchar(255) COLLATE "pg_catalog"."default",
  "list_class" varchar(255) COLLATE "pg_catalog"."default",
  "deletable" bool DEFAULT true,
  "sort" int2,
  "enabled" bool NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "star_blog"."sys_dict_data"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."dict_label" IS '字典标签';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."dict_value" IS '字典键值';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."dict_key" IS '字典key';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."css_class" IS '样式属性';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."list_class" IS '表格字典样式';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."deletable" IS '是否可删除';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."sort" IS '排序';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."enabled" IS '是否启用';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."create_by" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."update_by" IS '修改人';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."update_time" IS '修改时间';
COMMENT ON COLUMN "star_blog"."sys_dict_data"."remark" IS '备注';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO "star_blog"."sys_dict_data" VALUES (1989159034869137410, '首页配置', 'home-config', 'system-config-category', '', NULL, 'f', 1, 't', 'f', '1', '2025-11-14 10:30:45.362037', '1', '2025-12-08 13:40:29.077504', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (1997907567000612865, 'AI编程', 'ai-programme', 'article-category', 'background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)', '', 't', 3, 't', 'f', '1', '2025-12-08 13:54:17.967503', '1', '2025-12-08 13:54:23.275373', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (1997907858114670594, '日常生活', 'daily-life', 'article-category', 'background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)', '', 't', 4, 't', 'f', '1', '2025-12-08 13:55:27.377437', '1', '2025-12-08 13:55:27.377437', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (1659009853498867717, '系统配置', 'system-config', 'system-config-category', NULL, NULL, 'f', 2, 't', 'f', '1', '2025-11-10 17:37:01', '1', '2025-12-11 14:06:45.302006', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (2001489419267395585, '男', '1', 'sex', '', '', 't', 0, 't', 'f', '1', '2025-12-18 11:07:18.092909', '1', '2025-12-18 11:07:18.092909', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (2001489441757253634, '女', '2', 'sex', '', '', 't', 0, 't', 'f', '1', '2025-12-18 11:07:23.460716', '1', '2025-12-18 11:07:23.460716', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (2001489471545200642, '秘密', '3', 'sex', '', '', 't', 0, 't', 'f', '1', '2025-12-18 11:07:30.563588', '1', '2025-12-18 11:07:30.563588', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (2005571330575142913, '更新公告', 'update', 'notice-type', '', '', 't', 0, 't', 'f', '1', '2025-12-29 17:27:21.580523', '1', '2025-12-29 17:27:21.580523', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (1997907093178478594, '后端开发', 'server-develop', 'article-category', 'background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%)', '', 't', 1, 't', 'f', '1', '2025-12-08 13:52:24.991678', '1', '2025-12-31 15:03:54.491949', NULL);
INSERT INTO "star_blog"."sys_dict_data" VALUES (1659009853498867716, '前端开发', 'web-develop', 'article-category', 'background: linear-gradient(135deg, #667eea 0%, #764ba2 100%)', NULL, 't', 2, 't', 'f', '1', '2025-09-22 17:03:30', '1', '2025-12-31 15:04:00.19826', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_dict_type";
CREATE TABLE "star_blog"."sys_dict_type" (
  "id" int8 NOT NULL,
  "dict_name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_key" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "sort" int2
)
;
COMMENT ON COLUMN "star_blog"."sys_dict_type"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."dict_name" IS '字典名称';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."dict_key" IS '字典key';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."update_by" IS '修改人';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."update_time" IS '修改时间';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."remark" IS '备注';
COMMENT ON COLUMN "star_blog"."sys_dict_type"."sort" IS '排序';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO "star_blog"."sys_dict_type" VALUES (1659008235701288965, '系统配置类别', 'system-config-category', 'f', '1', '2025-11-10 17:35:04', '1', '2025-11-10 17:35:10', NULL, 1);
INSERT INTO "star_blog"."sys_dict_type" VALUES (1659008235701288964, '文章类别', 'article-category', 'f', '1', '2025-09-22 17:01:38', '1', '2025-09-22 17:01:44', NULL, 2);
INSERT INTO "star_blog"."sys_dict_type" VALUES (2001489339433013249, '用户性别', 'sex', 'f', '1', '2025-12-18 11:06:59.068189', '1', '2025-12-18 11:07:05.462109', NULL, 3);
INSERT INTO "star_blog"."sys_dict_type" VALUES (2005571234928234498, '系统公告类型', 'notice-type', 'f', '1', '2025-12-29 17:26:58.777842', '1', '2025-12-29 17:27:35.27174', NULL, 4);

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_menu";
CREATE TABLE "star_blog"."sys_menu" (
  "id" int8 NOT NULL,
  "component" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "icon" varchar(255) COLLATE "pg_catalog"."default",
  "parent_id" int8 NOT NULL,
  "sort" int4,
  "enabled" bool NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "update_time" timestamp(6),
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "uri" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "redirect_uri" varchar(255) COLLATE "pg_catalog"."default",
  "top_level" bool NOT NULL,
  "keep_alive" bool NOT NULL,
  "hidden" bool NOT NULL,
  "params" jsonb,
  "common" bool
)
;
COMMENT ON COLUMN "star_blog"."sys_menu"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_menu"."component" IS '组件名称';
COMMENT ON COLUMN "star_blog"."sys_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "star_blog"."sys_menu"."parent_id" IS '父菜单id';
COMMENT ON COLUMN "star_blog"."sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "star_blog"."sys_menu"."enabled" IS '是否启用';
COMMENT ON COLUMN "star_blog"."sys_menu"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_menu"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."sys_menu"."update_time" IS '修改时间';
COMMENT ON COLUMN "star_blog"."sys_menu"."update_by" IS '修改人';
COMMENT ON COLUMN "star_blog"."sys_menu"."remark" IS '备注';
COMMENT ON COLUMN "star_blog"."sys_menu"."name" IS '菜单名称';
COMMENT ON COLUMN "star_blog"."sys_menu"."uri" IS '菜单rui';
COMMENT ON COLUMN "star_blog"."sys_menu"."redirect_uri" IS '重定向uri';
COMMENT ON COLUMN "star_blog"."sys_menu"."top_level" IS '是否一级菜单';
COMMENT ON COLUMN "star_blog"."sys_menu"."keep_alive" IS '是否keep-alive缓存';
COMMENT ON COLUMN "star_blog"."sys_menu"."hidden" IS '是否隐藏';
COMMENT ON COLUMN "star_blog"."sys_menu"."params" IS '路由参数';
COMMENT ON COLUMN "star_blog"."sys_menu"."common" IS '是否公共菜单';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_notice";
CREATE TABLE "star_blog"."sys_notice" (
  "id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "create_by" int8 NOT NULL,
  "update_time" timestamp(6),
  "update_by" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "title" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "content" text COLLATE "pg_catalog"."default" NOT NULL,
  "published" bool NOT NULL,
  "published_time" timestamp(6)
)
;
COMMENT ON COLUMN "star_blog"."sys_notice"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_notice"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_notice"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_notice"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."sys_notice"."update_time" IS '更新时间';
COMMENT ON COLUMN "star_blog"."sys_notice"."update_by" IS '更新人';
COMMENT ON COLUMN "star_blog"."sys_notice"."remark" IS '备注';
COMMENT ON COLUMN "star_blog"."sys_notice"."type" IS '分类';
COMMENT ON COLUMN "star_blog"."sys_notice"."title" IS '标题';
COMMENT ON COLUMN "star_blog"."sys_notice"."content" IS '内容';
COMMENT ON COLUMN "star_blog"."sys_notice"."published" IS '是否发布';
COMMENT ON COLUMN "star_blog"."sys_notice"."published_time" IS '发布时间';
COMMENT ON TABLE "star_blog"."sys_notice" IS '系统公告';

-- ----------------------------
-- Records of sys_notice
-- ----------------------------


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_role";
CREATE TABLE "star_blog"."sys_role" (
  "id" int8 NOT NULL,
  "name" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(32) COLLATE "pg_catalog"."default" NOT NULL,
  "sort" int4,
  "default_role" bool,
  "deleted" bool NOT NULL DEFAULT false,
  "create_by" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6) NOT NULL,
  "update_by" varchar(64) COLLATE "pg_catalog"."default",
  "update_time" timestamp(6),
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "star_blog"."sys_role"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_role"."name" IS '角色名称';
COMMENT ON COLUMN "star_blog"."sys_role"."type" IS '角色类型';
COMMENT ON COLUMN "star_blog"."sys_role"."sort" IS '排序';
COMMENT ON COLUMN "star_blog"."sys_role"."default_role" IS '是否是系统内置';
COMMENT ON COLUMN "star_blog"."sys_role"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_role"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_role"."update_by" IS '修改人';
COMMENT ON COLUMN "star_blog"."sys_role"."update_time" IS '修改时间';
COMMENT ON COLUMN "star_blog"."sys_role"."remark" IS '备注';
COMMENT ON TABLE "star_blog"."sys_role" IS '角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO "star_blog"."sys_role" VALUES (1, '超级管理员', 'ADMIN', 1, 't', 'f', '1', '2023-04-18 17:45:01', '1', '2025-05-29 10:27:28', NULL);
INSERT INTO "star_blog"."sys_role" VALUES (2, '普通用户', 'COMMON_USER', 2, 't', 'f', '1', '2023-04-18 17:54:36', '1', '2025-06-03 17:45:31', NULL);
INSERT INTO "star_blog"."sys_role" VALUES (1991071886693515265, '博客审核员', 'COMMON_ADMIN', 3, 'f', 't', '1', '2025-11-19 17:11:44.775627', '1', '2025-11-19 17:48:50.331643', NULL);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_role_menu";
CREATE TABLE "star_blog"."sys_role_menu" (
  "id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "menu_id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "star_blog"."sys_role_menu"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_role_menu"."role_id" IS '系统角色id';
COMMENT ON COLUMN "star_blog"."sys_role_menu"."menu_id" IS '系统菜单id';
COMMENT ON COLUMN "star_blog"."sys_role_menu"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_role_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_role_menu"."update_time" IS '修改时间';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO "star_blog"."sys_role_menu" VALUES (1993927637321613313, 2, 1990711281612689410, 'f', '2025-11-27 14:19:28.778536', '2025-11-27 14:19:28.778536');
INSERT INTO "star_blog"."sys_role_menu" VALUES (1993927637321613314, 2, 1990984169322340353, 'f', '2025-11-27 14:19:28.779536', '2025-11-27 14:19:28.779536');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_user";
CREATE TABLE "star_blog"."sys_user" (
  "id" int8 NOT NULL,
  "account" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "avatar" varchar(1024) COLLATE "pg_catalog"."default",
  "nickname" varchar(32) COLLATE "pg_catalog"."default",
  "sex" int4,
  "mobile" varchar(32) COLLATE "pg_catalog"."default",
  "email" varchar(64) COLLATE "pg_catalog"."default",
  "last_login_time" timestamp(6),
  "last_login_ip" varchar(64) COLLATE "pg_catalog"."default",
  "deleted" bool NOT NULL DEFAULT false,
  "status" int4 NOT NULL,
  "sort" int4,
  "create_time" timestamp(6) NOT NULL,
  "create_by" int8 NOT NULL,
  "update_time" timestamp(6),
  "update_by" int8,
  "remark" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "star_blog"."sys_user"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_user"."account" IS '登录账号';
COMMENT ON COLUMN "star_blog"."sys_user"."password" IS '密码';
COMMENT ON COLUMN "star_blog"."sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "star_blog"."sys_user"."nickname" IS '昵称';
COMMENT ON COLUMN "star_blog"."sys_user"."sex" IS '性别';
COMMENT ON COLUMN "star_blog"."sys_user"."mobile" IS '手机号';
COMMENT ON COLUMN "star_blog"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "star_blog"."sys_user"."last_login_time" IS '上次登录时间';
COMMENT ON COLUMN "star_blog"."sys_user"."last_login_ip" IS '上次登录ip';
COMMENT ON COLUMN "star_blog"."sys_user"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_user"."status" IS '状态';
COMMENT ON COLUMN "star_blog"."sys_user"."sort" IS '排序';
COMMENT ON COLUMN "star_blog"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_user"."create_by" IS '创建人';
COMMENT ON COLUMN "star_blog"."sys_user"."update_time" IS '更新时间';
COMMENT ON COLUMN "star_blog"."sys_user"."update_by" IS '更新人';
COMMENT ON COLUMN "star_blog"."sys_user"."remark" IS '备注';
COMMENT ON TABLE "star_blog"."sys_user" IS '用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "star_blog"."sys_user" VALUES (1, 'ale', '$2a$10$gnZL0kvJqhPaYoVJB30Vme8PpTr2v/lsBeY9vGf1MTHrKKjXrMoYC', NULL, '不快乐的阿乐', 1, '', 'sh1031578962@163.com', '2026-01-29 17:19:09.228325', '127.0.0.1', 'f', 1, 0, '2023-03-14 11:02:02', 1, '2025-12-18 17:36:57.606164', 1, '后端开发一只，立志成为糕手');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."sys_user_role";
CREATE TABLE "star_blog"."sys_user_role" (
  "id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "deleted" bool NOT NULL DEFAULT false,
  "create_time" timestamp(6) NOT NULL,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "star_blog"."sys_user_role"."id" IS 'id';
COMMENT ON COLUMN "star_blog"."sys_user_role"."user_id" IS '系统用户id';
COMMENT ON COLUMN "star_blog"."sys_user_role"."role_id" IS '系统角色id';
COMMENT ON COLUMN "star_blog"."sys_user_role"."deleted" IS '是否删除';
COMMENT ON COLUMN "star_blog"."sys_user_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."sys_user_role"."update_time" IS '修改时间';
COMMENT ON TABLE "star_blog"."sys_user_role" IS '用户角色';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO "star_blog"."sys_user_role" VALUES (1, 1, 1, 'f', '2025-05-29 10:26:20', '2025-05-29 10:26:23');
INSERT INTO "star_blog"."sys_user_role" VALUES (1929829549822128129, 2, 2, 'f', '2025-06-03 17:16:34', '2025-06-03 17:16:34');
INSERT INTO "star_blog"."sys_user_role" VALUES (1928383512028839938, 1905553869777395714, 2, 't', '2025-05-30 17:30:32', '2025-11-19 17:08:11.682101');
INSERT INTO "star_blog"."sys_user_role" VALUES (1991071073476685825, 1, 2, 'f', '2025-11-19 17:08:30.887647', '2025-11-19 17:08:30.887647');

-- ----------------------------
-- Table structure for oss_mate
-- ----------------------------
DROP TABLE IF EXISTS "star_blog"."oss_mate";
CREATE TABLE "star_blog"."oss_mate" (
  "id" varchar(128) COLLATE "pg_catalog"."default" NOT NULL,
  "create_time" timestamp(6),
  "object_key" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "provider" varchar(64) COLLATE "pg_catalog"."default" NOT NULL,
  "url" text COLLATE "pg_catalog"."default",
  "size" int8,
  "mime_type" varchar(64) COLLATE "pg_catalog"."default",
  "last_visit_time" timestamp(6),
  "reference_count" int2
)
;
COMMENT ON COLUMN "star_blog"."oss_mate"."id" IS 'ID';
COMMENT ON COLUMN "star_blog"."oss_mate"."create_time" IS '创建时间';
COMMENT ON COLUMN "star_blog"."oss_mate"."object_key" IS '对象key';
COMMENT ON COLUMN "star_blog"."oss_mate"."provider" IS 'oss服务提供器';
COMMENT ON COLUMN "star_blog"."oss_mate"."url" IS '访问url';
COMMENT ON COLUMN "star_blog"."oss_mate"."size" IS '文件大小';
COMMENT ON COLUMN "star_blog"."oss_mate"."mime_type" IS '文件类型';
COMMENT ON COLUMN "star_blog"."oss_mate"."last_visit_time" IS '最后访问时间';
COMMENT ON COLUMN "star_blog"."oss_mate"."reference_count" IS '引用计数';
COMMENT ON TABLE "star_blog"."oss_mate" IS 'oss元信息';

-- ----------------------------
-- Records of oss_mate
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table blog_activity
-- ----------------------------
ALTER TABLE "star_blog"."blog_activity" ADD CONSTRAINT "blog_tag_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table blog_article
-- ----------------------------
CREATE INDEX "idx_blog_status" ON "star_blog"."blog_article" USING btree (
  "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_blog_top" ON "star_blog"."blog_article" USING btree (
  "top" "pg_catalog"."bool_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table blog_article
-- ----------------------------
ALTER TABLE "star_blog"."blog_article" ADD CONSTRAINT "blog_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table blog_article_tag
-- ----------------------------
CREATE INDEX "idx_blog_tag_blog_id" ON "star_blog"."blog_article_tag" USING btree (
  "article_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_blog_tag_tag_id" ON "star_blog"."blog_article_tag" USING btree (
  "tag_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table blog_article_tag
-- ----------------------------
ALTER TABLE "star_blog"."blog_article_tag" ADD CONSTRAINT "blog_tag_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table blog_comment
-- ----------------------------
CREATE INDEX "idx_article_root" ON "star_blog"."blog_comment" USING btree (
  "article_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "root_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "create_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST,
  "status" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_like_count" ON "star_blog"."blog_comment" USING btree (
  "like_count" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_root_create" ON "star_blog"."blog_comment" USING btree (
  "root_id" "pg_catalog"."int8_ops" ASC NULLS LAST,
  "create_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table blog_comment
-- ----------------------------
ALTER TABLE "star_blog"."blog_comment" ADD CONSTRAINT "comment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table blog_comment_like
-- ----------------------------
ALTER TABLE "star_blog"."blog_comment_like" ADD CONSTRAINT "blog_comment_like_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table blog_tag
-- ----------------------------
ALTER TABLE "star_blog"."blog_tag" ADD CONSTRAINT "blog_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_config
-- ----------------------------
ALTER TABLE "star_blog"."sys_config" ADD CONSTRAINT "sys_config_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_data
-- ----------------------------
CREATE INDEX "sys_dict_data_dict_key_idx" ON "star_blog"."sys_dict_data" USING btree (
  "dict_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "sys_dict_data_dict_value_idx" ON "star_blog"."sys_dict_data" USING btree (
  "dict_value" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict_data
-- ----------------------------
ALTER TABLE "star_blog"."sys_dict_data" ADD CONSTRAINT "sys_dict_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_dict_type
-- ----------------------------
CREATE INDEX "sys_dict_type_dict_key_idx" ON "star_blog"."sys_dict_type" USING btree (
  "dict_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "star_blog"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "star_blog"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_notice
-- ----------------------------
ALTER TABLE "star_blog"."sys_notice" ADD CONSTRAINT "sys_config_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "star_blog"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "star_blog"."sys_role_menu" ADD CONSTRAINT "sys_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "star_blog"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table oss_mate
-- ----------------------------
ALTER TABLE "star_blog"."oss_mate" ADD CONSTRAINT "file_mate_pkey" PRIMARY KEY ("id");
