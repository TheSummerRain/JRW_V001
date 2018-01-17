# JR  javaWeb代码生成器

##介绍
这是一款 javaWeb 代码生成器 目前处于初成阶段： 仅支持mysql 数据库表作为原始模型。 通过简单的配置 然后 导入mysql表 。即可一键生成 java + 前端代码 前后端分离 ajax json 交互

### 技术架构
后端
: 	mybatis
:	spring
:	springMvc
:	gradle

前端
:	ace 模板
:	vue
:	elemui

###生成器功能
- 父子表模式
- 单对单管理
- 文件上传（下版本更新）
- 单对多管理（看心情 预计 v003 更新）
- 范围查询日期查询（下版本更新） 
> 下一个版本 会在 2017.02左右更新

###教程
- 数据库配置
![这里写图片描述](http://img.blog.csdn.net/20180117183227989?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMjI5NTY4Njc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
- 首页路径
  http://localhost/jrgo/ace&index
- 账号表（需自行添加账号） 
  tab_account
- 使用流程
![这里写图片描述](http://img.blog.csdn.net/20180117185347720?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMjI5NTY4Njc=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)
- 路径说明
访问页面需经过com.jryz.web.action.WebController渲染（仅仅为了统一路径） 
规则： http://localhost/jrgo/test&core&testuser
test&core&testuser（前端代码完整路径 WebRoot\views\test\core\user）
- 前端代码需放置在 改项目的  WebRoot\views 下面否则无妨访问 如需更改 请更改 WebController 和 springmvc配置 于 ftl模板

###序言

目前网上有一些代码生成器 有些功能很强大 但是 或多或少都有以下的特质：

- **高度集成**
- **高耦合**
- **高封装层级**
- **简陋**
- **非开源**

所以 干脆空闲时间自己写了个这个项目 目标：

- **低度集成**
- **低耦合**
- **开源**
- **简单**
- **完善**

## 