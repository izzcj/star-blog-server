<div align="center">

# ⭐ StarBlogServer

一个现代化的个人博客系统-后端工程，基于 Springboot + maven 构建

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.9-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.java.net/)
[![License](https://img.shields.io/badge/license-Proprietary-blue.svg)]()

</div>

---

## 📖 项目简介

**StarBlog Server** 是一个基于 Spring Boot 3.5.9 和 Java 21 构建的现代化博客管理平台后端服务。项目采用自研的 **Venus Framework**，为 CRUD 操作、认证授权和数据管理提供了标准化的开发模式，大幅提升开发效率。

系统支持多种数据库（PostgreSQL/MySQL），集成了 Redis 缓存、MinIO 对象存储和即时通讯功能，适用于个人博客、企业内容管理等多种场景。

---

## ✨ 核心特性

- 🚀 **现代化技术栈** - Spring Boot 3.5.9 + Java 21 + MyBatis-Plus
- 🎯 **Venus 框架** - 自研框架，提供泛型基类、钩子系统、自动字段填充等特性
- 🔐 **安全认证** - Spring Security + JWT，支持 RBAC 权限控制
- 💾 **多数据库支持** - 支持 PostgreSQL 和 MySQL，可扩展其他数据库
- 📦 **对象存储** - 集成 MinIO，支持文件上传和富文本图片管理
- ⚡ **高性能缓存** - Redis + Caffeine 多级缓存
- 🔄 **软删除** - 数据逻辑删除，保留审计跟踪
- 📝 **富文本支持** - 完善的富文本编辑和图片处理
- 🐳 **Docker 部署** - 提供完整的 Docker Compose 配置

---

## 🎯 核心功能

### 系统管理

| 功能模块 | 功能说明 |
|---------|---------|
| 👤 用户管理 | 用户账户的增删改查、密码加密（BCrypt）、状态管理 |
| 🎭 角色管理 | 角色定义、权限分配、角色与用户关联 |
| 📋 菜单管理 | 系统菜单和权限树结构管理 |
| 📚 字典管理 | 系统常量字典管理，支持字典类型和字典数据 |
| ⚙️ 配置管理 | 应用级配置的动态存储和管理 |

### 博客管理

| 功能模块 | 功能说明 |
|---------|---------|
| 📝 文章管理 | 文章的增删改查、富文本内容支持、发布状态管理 |
| 🏷️ 标签管理 | 文章标签/分类管理、标签与文章的多对多关联 |
| 🖼️ 图片管理 | 基于 MinIO 的图片上传、存储和管理 |

### 安全与认证

- 🔐 **JWT 认证**：基于 JJWT 的 Token 认证机制
- 🛡️ **权限控制**：RBAC（基于角色的访问控制）模型
- 💾 **Token 存储**：支持 Redis 或内存存储
- 🔒 **密码加密**：采用 BCrypt 加密算法
- 🚪 **公开端点**：灵活配置无需认证的 URI

---

## 🛠️ 技术栈

### 核心框架

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.9 | 核心应用框架 |
| Java | 21 | 编程语言（LTS 版本） |
| MyBatis-Plus | 3.5.7 | ORM 框架，简化数据库操作 |
| Spring Security | - | 安全认证框架 |

### 数据存储

| 技术 | 版本 | 说明 |
|------|------|------|
| PostgreSQL | 12+ | 主要关系型数据库 |
| MySQL | 8.0+ | 可选的关系型数据库 |
| Redis | 5.0+ | 缓存和会话存储 |
| MinIO | - | S3 兼容的对象存储 |

### 工具库

| 技术 | 版本 | 说明 |
|------|------|------|
| HikariCP/Druid | - | 高性能数据库连接池 |
| FastJSON2 | 2.0.34 | JSON 序列化/反序列化 |
| Hutool | 5.8.18 | Java 工具库 |
| JJWT | 0.11.5 | JWT Token 生成和验证 |
| Caffeine | - | 本地内存缓存 |

---

## 📁 项目结构

```
star-blog-server/
│
├── docker/                              # Docker 部署配置
│   ├── db/                              # 数据库初始化脚本
│   ├── nginx/                           # Nginx 反向代理配置
│   ├── redis/                           # Redis 配置文件
│   ├── server/                          # 应用服务配置
│   ├── docker-compose-base.yml          # 基础服务（数据库、Redis、MinIO）
│   └── docker-compose-server.yml        # 应用服务
│
├── star-blog-admin/                     # 主应用模块（后台管理服务）
│   └── src/main/
│       ├── java/com/ale/starblog/admin/
│       │   ├── blog/                    # 博客领域
│       │   │   ├── controller/          # 控制器（文章、标签）
│       │   │   ├── service/             # 业务逻辑
│       │   │   ├── mapper/              # 数据访问层
│       │   │   └── domain/              # 领域模型（Entity、BO、VO、DTO）
│       │   │
│       │   ├── system/                  # 系统领域
│       │   │   ├── controller/          # 控制器（用户、角色、菜单、字典、配置）
│       │   │   ├── service/             # 业务逻辑
│       │   │   ├── mapper/              # 数据访问层
│       │   │   └── domain/              # 领域模型
│       │   │
│       │   └── common/                  # 公共模块
│       │       ├── config/              # 配置类
│       │       └── util/                # 工具类
│       │
│       └── resources/
│           ├── application.yml          # 主配置文件
│           └── application-dev.yml      # 开发环境配置
│
└── star-blog-framework/                 # Venus 框架（自研框架）
    ├── star-blog-framework-common/      # 公共模块
    │   └── src/main/java/com/ale/starblog/framework/common/
    │       ├── domain/                  # 领域基类（BaseEntity、BaseQuery等）
    │       ├── enums/                   # 枚举定义
    │       └── util/                    # 工具类
    │
    ├── star-blog-framework-core/        # 核心模块
    │   └── src/main/java/com/ale/starblog/framework/core/
    │       ├── controller/              # 基础控制器
    │       ├── service/                 # 基础服务（泛型CRUD）
    │       ├── config/                  # 数据库、缓存等配置
    │       └── hook/                    # 钩子系统
    │
    ├── star-blog-framework-security/    # 安全认证模块
    │   └── src/main/java/com/ale/starblog/framework/security/
    │       ├── authentication/          # JWT 认证
    │       ├── authorization/           # 权限控制
    │       └── config/                  # 安全配置
    │
    ├── star-blog-framework-workflow/    # 工作流模块（预留）
    │
    └── star-blog-framework-starter/     # 框架聚合模块
        └── 自动装配所有框架组件
```

---

## 🚀 快速开始

### 📋 环境要求

确保你的开发环境满足以下要求：

| 软件 | 版本要求   | 说明 |
|------|--------|------|
| JDK | 21+    | 必须使用 Java 21 或更高版本 |
| Maven | 3.6+   | 项目构建工具 |
| PostgreSQL | 12+    | 推荐使用 PostgreSQL |
| MySQL | 8.0+   | 可选，作为 PostgreSQL 的替代 |
| Redis | 5.0+   | 缓存和会话存储 |
| MinIO | 合适就行   | 对象存储服务 |

### 📥 克隆项目

```bash
git clone <repository-url>
cd star-blog-server
```

### ⚙️ 本地开发配置

#### 1、Venus 框架配置
所有 Venus 框架配置使用 `venus.*` 前缀：

```yaml
venus:
  # ==================== 数据库配置 ====================
  db:
    type: postgresql              # 数据库类型：postgresql | mysql
    database: star_blog           # 数据库名
    schema: star_blog             # Schema（需要使用schema的数据库用）
    host: localhost
    port: 5432
    username: postgres
    password: password
    pool:
      pool-type: hikari           # 连接池：hikari | druid
      maximum-pool-size: 10
      minimum-idle: 5

  # ==================== 对象存储配置 ====================
  oss:
    minio:
      enabled: true
      endpoint: http://localhost:9000
      access-key: minioadmin
      secret-key: minioadmin
      bucket: star-blog

  # ==================== 安全配置 ====================
  security:
    token-type: redis             # Token 存储：redis | memory
    token-expiration: 24h         # Token 有效期
    permitted-uris:               # 公开端点（无需认证）
      - uri: /api/auth/**
        methods: [POST]
      - uri: /api/public/**
        methods: [GET]

  # ==================== 日志配置 ====================
  logging:
    enabled: true
    level:
      com.ale.starblog.admin: info
    log-file:
      info:
        file-path: logs/info.log
        max-size: 100MB
        max-history: 30
      error:
        file-path: logs/error.log
        max-size: 100MB
        max-history: 30

  # ==================== 即时通讯配置 ====================
  im:
    enabled: true
    sender: websocket             # 消息发送器：websocket | rabbitmq，可扩展

  # ==================== 工作流配置 ====================
  workflow:
    enabled: false                # 工作流引擎

  # ==================== 代理配置 ====================
  proxy:
    enabled: true                 # 启用代理转发

  # ==================== 通用配置 ====================
  common:
    enable-cors: true             # 启用跨域支持
```

#### 2、Spring Boot 配置

```yaml
server:
  port: 9091                      # 服务端口

spring:
  application:
    name: StarBlogAdminService

  profiles:
    active: dev                   # 激活的配置文件

  data:
    redis:
      host: localhost
      port: 6379
      database: 1
      password:
      lettuce:
        pool:
          max-active: 8
          max-idle: 8
          min-idle: 2
```

#### 3、 构建并启动项目

```bash
# 清理并构建整个项目
mvn clean install

# 启动应用程序
mvn spring-boot:run -pl star-blog-admin

# 或者先打包，再运行
mvn clean package
java -jar star-blog-admin/target/star-blog-admin-1.0.0.jar
```

#### 4、 访问服务

启动成功后，服务将运行在：

- 🌐 **服务地址**：`http://localhost:9091`

---

### 🐳 Docker 部署

项目提供了完整的 Docker Compose 配置，可以一键上线部署所有服务。

#### 1. 配置环境变量

编辑 `docker/.env` 文件，设置必要的环境变量：

```env
# 数据库配置
DB_PASSWORD=your_secure_password

# MinIO 配置
MINIO_ROOT_USER=admin
MINIO_ROOT_PASSWORD=your_minio_password
```

#### 2. 启动基础服务

启动数据库、Redis、Nginx、MinIO 等基础服务：

需要将前端项目打包后的dist文件夹复制到`docker/nginx/html/admin/`目录下。
如果需要开启ssl，将证书文件复制到`docker/nginx/ssl`目录下。

```bash
cd docker
docker-compose -f docker-compose-base.yml up -d
```

#### 3. 启动应用服务

需要将后端项目打包后的jar包复制到`docker/server/admin/`目录下。

```bash
docker-compose -f docker-compose-server.yml up -d
```

#### 4. 查看服务状态

```bash
# 查看所有服务状态
docker-compose -f docker-compose-base.yml ps
docker-compose -f docker-compose-server.yml ps

# 查看应用日志
docker-compose -f docker-compose-server.yml logs -f star-blog-admin
```

#### 5. 停止服务

```bash
# 停止应用服务
docker-compose -f docker-compose-server.yml down

# 停止基础服务
docker-compose -f docker-compose-base.yml down
```

---

## 📖 开发指南

### 添加新功能模块

按照以下步骤添加新的业务功能：

#### 1. 定义领域模型

在适当的领域包（如 `blog` 或 `system`）中创建模型类：

```java
// Entity - 数据库实体
@TableName("blog_article")
public class Article extends BaseAuditEntity {
    private String title;
    private String content;
    // ...
}

// BO - 业务对象
public class ArticleBO {
    private Long id;
    private String title;
    // ...
}

// VO - 视图对象（返回给前端）
public class ArticleVO {
    private Long id;
    private String title;
    // ...
}

// CreateDTO - 创建时的输入
public class CreateArticleDTO {
    private String title;
    private String content;
    // ...
}

// ModifyDTO - 修改时的输入
public class ModifyArticleDTO {
    private Long id;
    private String title;
    // ...
}
```

#### 2. 创建 Mapper

```java
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    // 继承 BaseMapper 后自动拥有基础 CRUD 方法
    // 可以添加自定义查询方法
}
```

#### 3. 创建 Service

```java
// 服务
@Service
public class ArticleService extends AbstractCrudService<ArticleMapper, Article, ArticleBO, ArticleQuery> {

    @Override
    protected void beforeCreate(Article entity, HookContext context) {
        // 创建前的业务逻辑
    }

    @Override
    protected void afterQuery(Article entity, HookContext context) {
        // 查询后的业务逻辑
    }
}
```

#### 4. 创建 Controller

```java
@RestController
@RequestMapping("/api/articles")
public class ArticleController extends BaseController<
    Article,
    ArticleService,
    ArticleVO,
    ArticleBO,
    ArticleQuery,
    CreateArticleDTO,
    ModifyArticleDTO
> {
    // 基础 CRUD 端点已由 BaseController 提供：
    // POST   /api/articles          - 创建
    // GET    /api/articles/{id}     - 查询单个
    // GET    /api/articles          - 分页查询
    // PUT    /api/articles/{id}     - 修改
    // DELETE /api/articles/{id}     - 删除

    // 只需添加特殊的业务端点
    @GetMapping("/published")
    public JsonResult<List<ArticleVO>> getPublishedArticles() {
        // ...
    }
}
```

#### 5. 配置权限

如果需要公开访问，更新配置文件：

```yaml
venus:
  security:
    permitted-uris:
      - uri: /api/articles/published
        methods: [GET]
```

## 🤝 贡献指南

欢迎提交 Issue 或 Pull Request 来帮助改进项目。

### 提交 Issue

- 提供详细的问题描述
- 包含复现步骤和环境信息
- 附上相关截图或日志

### 提交 Pull Request

- Fork 本仓库并创建新分支
- 遵循项目代码规范
- 提供清晰的 PR 描述

---

## ⭐ Star

本人也是得到很多开源项目的帮助，因此本项目也是选择开源，希望能给更多朋友带来一点点启发。
如果这个项目对你有帮助，欢迎 Star 支持！

---

## 📜 开源协议

本项目基于 [Apache License 2.0](LICENSE) 开源协议。

---