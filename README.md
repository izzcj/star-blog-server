# StarBlog Server

> 星博客后端服务 - 基于 Spring Boot 3.5.9 和 Java 21 构建的企业级博客管理系统

## 项目简介

StarBlog 是一个现代化的博客管理平台后端服务，采用自研的 Venus 框架，为 CRUD 操作、认证和数据管理提供了标准化的开发模式。项目支持多种数据库，集成了 Redis 缓存、MinIO 对象存储和即时通讯功能。

### 技术栈

| 技术 | 版本 | 说明 |
|------|------|------|
| Spring Boot | 3.5.9 | 核心框架 |
| Java | 21 | 编程语言 |
| MyBatis-Plus | 3.5.7 | ORM 框架 |
| Spring Security | - | 安全认证框架 |
| PostgreSQL | - | 关系型数据库 |
| Redis | - | 缓存和会话存储 |
| MinIO | - | 对象存储 |
| HikariCP/Druid | - | 数据库连接池 |
| FastJSON2 | 2.0.34 | JSON 序列化 |
| Hutool | 5.8.18 | Java 工具库 |

## 项目结构

```
star-blog-server/
│
│── docker/                           # Docker 部署文件
│   ├── db/                           # 数据库初始化脚本
│   ├── nginx/                        # Nginx 配置
│   ├── redis/                        # Redis 配置
│   ├── server/                       # 服务配置
│   ├── docker-compose-base.yml       # 基础服务
│   └── docker-compose-server.yml     # 应用服务
│
├── star-blog-admin/                  # 主应用模块（后台服务）
│   └── src/main/
│       ├── java/com/ale/starblog/admin/
│       │   ├── blog/                 # 博客领域（文章、标签）
│       │   ├── system/               # 系统领域（用户、角色、菜单、字典、配置）
│       │   └── common/               # 公共模块
│       └── resources/
│           └── application.yml       # 配置
│
└── star-blog-framework/              # Venus 框架（内部框架）
    ├── star-blog-framework-common/   # 公共服务模块
    ├── star-blog-framework-core/     # 核心模块
    ├── star-blog-framework-security/ # 认证模块
    ├── star-blog-framework-starter/  # 框架聚合模块
    └── star-blog-framework-workflow/ # 工作流模块
```

## 快速开始

### 环境要求

- **JDK**：21+
- **Maven**：3.x
- **PostgreSQL**：12+ 或 **MySQL**：8.0+
- **Redis**：5.0+
- **Docker**：20.10+（用于部署）

### 克隆项目

```bash
git clone <repository-url>
cd star-blog-server
```

### 本地开发配置

1. **配置数据库**

在 `star-blog-admin/src/main/resources/application-dev.yml` 中配置数据库连接：

```yaml
venus:
  db:
    type: postgresql        # 或 mysql
    database: star_blog
    schema: star_blog        # 仅 PostgreSQL 需要
    username: your_username
    password: your_password
```

2. **配置 Redis**

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      database: 1
```

3. **构建并启动**

```bash
# 清理并构建整个项目
mvn clean install

# 运行应用程序
mvn spring-boot:run -pl star-blog-admin
```

4. **访问服务**

- 服务端口：`9091`
- API 地址：`http://localhost:9091`

### Docker 部署

1. **启动基础服务**

```bash
cd docker
docker-compose -f docker-compose-base.yml up -d
```

2. **启动应用服务**

```bash
docker-compose -f docker-compose-server.yml up -d
```

## 核心功能

### 系统管理

| 功能 | 说明 |
|------|------|
| 用户管理 | 用户账户管理、密码加密（BCrypt） |
| 角色管理 | 角色定义、权限分配 |
| 菜单管理 | 系统菜单/权限树结构 |
| 字典管理 | 系统常量字典管理 |
| 配置管理 | 应用级配置存储 |

### 博客管理

| 功能 | 说明 |
|------|------|
| 文章管理 | 文章的增删改查、富文本内容支持 |
| 标签管理 | 文章标签/分类管理 |

### 安全与认证

- **JWT 认证**：基于 JJWT 的 Token 认证
- **权限控制**：RBAC（基于角色的访问控制）
- **Token 存储**：支持 Redis 或内存存储
- **密码加密**：BCrypt 加密算法

### Venus 框架特性

- **泛型基类**：自动处理 CRUD 操作，减少样板代码
- **钩子系统**：支持服务生命周期的拦截和扩展
- **字段自动填充**：审计字段（创建时间、更新时间等）自动处理
- **软删除**：逻辑删除支持，保留数据审计跟踪
- **翻译系统**：动态字段映射，ID 自动转换为显示值

## 配置说明

### Venus 框架配置

配置前缀：`venus.*`

```yaml
venus:
  # 数据库配置
  db:
    type: postgresql          # 数据库类型：postgresql | mysql（可自行扩展）
    database: star_blog       # 数据库名
    schema: star_blog         # Schema
    username: postgres
    password: password
    pool:
      pool-type: hikari       # 连接池：hikari | druid

  # 对象存储（MinIO）
  oss:
    minio:
      enabled: true
      access-key: your_key
      secret-key: your_secret
      bucket: star-blog

  # 安全配置
  security:
    token-type: redis          # Token 存储：redis | memory
    token-expiration: 24h      # Token 有效期
    permitted-uris:            # 公开端点（无需认证）
      - uri: /**
        methods:
          - all

  # 日志配置
  logging:
    enabled: true
    level:
      com.ale.starblog.admin: info
    log-file:
      info:
        file-path: star-blog-admin/logs/info.log
      error:
        file-path: star-blog-admin/logs/error.log

  # 即时通讯
  im:
    enabled: true
    sender: websocket

  # 工作流
  workflow:
    enabled: false

  # 代理配置
  proxy:
    enabled: true

  # 通用配置
  common:
    enable-cors: true          # 启用 CORS
```

### 端口配置

默认端口：`9091`

可在 `application-dev.yml` 中通过 `server.port` 修改。

## 开发指南

### 添加新功能

1. **定义领域模型**：创建 Entity、BO、VO、DTOs
2. **创建 Mapper**：继承 `BaseMapper<Entity>`
3. **创建 Service**：接口 + 实现，重写钩子添加自定义逻辑
4. **创建 Controller**：继承 `BaseController` 并提供正确的类型参数
5. **配置权限**：更新 `venus.security.permitted-uris`

### 常用命令

```bash
# 清理并构建整个项目
mvn clean install

# 只构建指定模块
mvn clean install -pl star-blog-admin

# 运行应用程序（开发模式）
mvn spring-boot:run -pl star-blog-admin

# 打包为可执行 JAR
mvn clean package

# 跳过测试构建
mvn clean install -DskipTests
```

### 命名规范

| 类型 | 命名规则 | 示例 |
|------|----------|------|
| 控制器 | `XxxController` | `UserController` |
| 服务接口 | `IXxxService` | `IUserService` |
| 服务实现 | `XxxServiceImpl` | `UserServiceImpl` |
| 实体 | 单数名词 | `User`、`Article` |
| Mapper | `XxxMapper` | `UserMapper` |
| 创建 DTO | `CreateXxxDTO` | `CreateUserDTO` |
| 修改 DTO | `ModifyXxxDTO` | `ModifyUserDTO` |
| 视图对象 | `XxxVO` | `UserVO` |
| 查询对象 | `XxxQuery` | `UserQuery` |
| 业务对象 | `XxxBO` | `UserBO` |

## 常见问题

### 数据库连接失败

检查数据库配置是否正确，确保数据库服务已启动。

### Redis 连接失败

检查 Redis 服务是否运行，端口配置是否正确。

### MinIO 上传失败

检查 MinIO 配置，确保 access-key、secret-key 和 bucket 正确。

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 创建 Pull Request

## 许可证

本项目采用 [MIT 许可证](LICENSE) 进行授权。

---
