你是一个资深的java专家，请在开发中遵循如下规则：
- 严格遵循 **SOLID、DRY、KISS、YAGNI** 原则
- 遵循 **OWASP 安全最佳实践**（如输入验证、SQL注入防护）
- 采用 **分层架构设计**，确保职责分离
- 以下规则适用于star-blog-admin、star-blog-web两个模块
---

## 二、技术栈规范
### 技术栈要求
- **框架**：Spring Boot 3.x + Java 17
- **依赖**：
    - 核心：Spring Web, Mybatis, Lombok
    - 数据库：PostgreSQL Driver
    - 其他：Spring Security
---

## 三、应用逻辑设计规范
### 1. 分层架构原则
| 层级             | 职责                      | 约束条件                                                                                       |
|----------------|-------------------------|--------------------------------------------------------------------------------------------|
| **Controller** | 处理 HTTP 请求与响应，定义 API 接口 | - 禁止直接操作数据库<br>- 必须通过 Service 层调用                                                          |
| **Service**    | 业务逻辑实现，事务管理，数据校验        | - 接口必须继承IBaseService<br>- 返回 DTO 而非实体类（除非必要）                                               |
| **Mapper**     | Mapper层，用于数据库交互         | - 必须继承 `BaseMapper`<br>                                                                    |
| **Domain**     | 实体层                     | - 每个实体必须包含Entity、CreateDTO、ModifyDTO、BO、Query、VO<br/>- 实体必须放在entity包下<br>- 其余中间转换类放在pojo包下 |

---

## 四、核心代码规范
### 通用规范
- 状态类字段使用枚举
### 1.1 实体类（Entity）规范
```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@TableName("sys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseAuditEntity {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;
}
```
### 1.2 创建实体类（CreateDTO）规范
```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateUserDTO extends BaseCreateDTO {

    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String account;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;
}
```
### 1.3 修改实体类（ModifyDTO）规范
```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateUserDTO extends BaseCreateDTO {

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;
}
```
### 1.4 业务传输类（BO）规范
```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserBO extends BaseBO {

    /**
     * ID
     */
    private Long id;

    /**
     * 账号
     */
    private String account;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;
}
```
### 1.4 查询条件类（Query）规范
```java
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends BaseQuery {

    /**
     * 账号
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String account;

    /**
     * 昵称
     */
    @Query(type = QueryType.LIKE_ANYWHERE)
    private String nickname;
}
```
### 1.4 数据展示类（VO）规范
```java
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BaseVO {

    /**
     * 账号
     */
    private String account;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    @TranslationField(type = TranslationConstants.TRANSLATION_DICT, params = "type=sex")
    private String sex;
}
```
### 2. 数据访问层（Mapper）规范
```java
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
}
```

### 3.1 业务表服务层（Service）规范
```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User, UserBO, CreateUserDTO, ModifyUserDTO> implements IUserService {

    /**
     * 密码编码器
     */
    private final PasswordEncoder passwordEncoder;

    @Override
    public void beforeCreate(User entity, HookContext context) {
        if (StrUtil.isBlank(entity.getPassword())) {
            entity.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));
        }
    }

    @Override
    public void changePassword(Long id, String newPassword, String oldPassword) {
        User user = this.getById(id);
        if (user == null) {
            throw new ServiceException("修改密码失败！用户不存在！");
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ServiceException("修改密码失败！原密码错误！");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        this.update(user, null);
    }
}
```

### 3.2 中间表服务层（Service）规范
```java
@Service
@RequiredArgsConstructor
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

   /**
    * 用户服务
    */
   private final IUserService userService;

   /**
    * 角色服务
    */
   private final IRoleService roleService;

   @Override
   public List<RoleBO> queryRoleByUserId(Long userId) {
      if (userId == null) {
         return Collections.emptyList();
      }
      List<UserRole> userRoleList = this.lambdaQuery()
              .eq(UserRole::getUserId, userId)
              .list();
      if (CollectionUtil.isEmpty(userRoleList)) {
         return Collections.emptyList();
      }
      List<Long> roleIds = userRoleList.stream()
              .map(UserRole::getRoleId)
              .toList();
      return this.roleService.lambdaQuery()
              .in(Role::getId, roleIds)
              .list()
              .stream()
              .map(role -> BeanUtil.copyProperties(role, RoleBO.class))
              .toList();
   }
}
```
### 4. 控制器（RestController）规范
```java
@RestController
@RequiredArgsConstructor
@RequestMapping("/system/user")
public class UserController extends BaseController<User, IUserService, UserVO, UserBO, CreateUserDTO, ModifyUserDTO> {

    /**
     * 用户角色服务
     */
    private final IUserRoleService userRoleService;

    /**
     * 通过id获取用户
     *
     * @param id 用户id
     * @return 用户信息
     */
    @GetMapping("/{id}")
    public JsonResult<UserVO> get(@PathVariable(name = "id") Long id) {
        return this.queryById(id);
    }

    /**
     * 分页获取用户
     *
     * @param pageable 分页参数
     * @param query    查询条件
     * @return 用户分页数据
     */
    @GetMapping("/page")
    public JsonResult<JsonPageResult.PageData<UserVO>> page(Pageable pageable, UserQuery query) {
        return this.queryPage(pageable, query);
    }

    /**
     * 创建用户
     *
     * @param createUserDTO 创建用户dto
     *
     * @return Void
     */
    @PostMapping
    public JsonResult<Void> create(@RequestBody @Validated CreateUserDTO createUserDTO) {
        return this.createEntity(createUserDTO);
    }

    /**
     * 修改用户
     *
     * @param modifyUserDTO 修改用户dto
     *
     * @return Void
     */
    @PutMapping
    public JsonResult<Void> modify(@RequestBody @Validated ModifyUserDTO modifyUserDTO) {
        List<Long> roleIds = modifyUserDTO.getRoleIds();
        if (CollectionUtil.isNotEmpty(roleIds)) {
            this.userRoleService.authUser(modifyUserDTO.getId(), roleIds);
        }
        return this.modifyEntity(modifyUserDTO);
    }

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return Void
     */
    @DeleteMapping("/{id}")
    public JsonResult<Void> delete(@PathVariable(name = "id") Long id) {
        return this.deleteEntity(id);
    }
}
```
### 5. 枚举类（Enum）规范
```java
@Getter
public enum MenuType implements BaseEnum<String> {

   /**
    * 目录
    */
   CATALOGUE("M", "目录"),

   /**
    * 菜单
    */
   MENU("C", "菜单"),

   /**
    * 按钮
    */
   BUTTON("B", "按钮");

   MenuType(String type, String msg) {
      this.init(type, msg);
   }
}
```

---

## 五、安全与性能规范
1. **输入校验**：
    - 使用 `@Validated` 注解 + jakarta.validation 校验注解（如 `@NotBlank`, `@Size`）
    - 禁止直接拼接 SQL 防止注入攻击
2. **事务管理**：
    - `@Transactional` 注解仅标注在 Service 方法上
    - 避免在循环中频繁提交事务
3. **性能优化**：
    - 避免在循环中执行数据库查询（批量操作优先）
---

## 六、代码风格规范
1. **命名规范**：
    - 类名：`UpperCamelCase`（如 `UserServiceImpl`）
    - 方法/变量名：`lowerCamelCase`（如 `saveUser`）
    - 常量：`UPPER_SNAKE_CASE`（如 `MAX_LOGIN_ATTEMPTS`）
    - Bool类型变量：`禁止使用isXXX`
2. **注释规范**：
    - 类属性必须添加注释
    - 方法必须添加注释且方法级注释使用 Javadoc 格式
    - 计划待完成的任务需要添加 `// TODO` 标记
    - 存在潜在缺陷的逻辑需要添加 `// FIXME` 标记
3. **代码格式化**：
    - 使用 IntelliJ IDEA 默认的 Spring Boot 风格
    - 禁止手动修改代码缩进（依赖 IDE 自动格式化）
---

## 七、部署规范
1. **部署规范**：
    - 生产环境需禁用 `@EnableAutoConfiguration` 的默认配置
    - 敏感信息通过 `application.yml` 外部化配置
    - 使用 `Spring Profiles` 管理环境差异（如 `dev`, `prod`）
---

## 八、扩展性设计规范
1. **接口优先**：
    - 服务层接口（`IUserService`）与实现（`UserServiceImpl`）分离
2. **扩展点预留**：
    - 关键业务逻辑需提供 `Strategy` 或 `Template` 模式支持扩展
3. **日志规范**：
    - 使用 `SLF4J` 记录日志（禁止直接使用 `System.out.println`）
    - 核心操作需记录 `INFO` 级别日志，异常记录 `ERROR` 级别
```