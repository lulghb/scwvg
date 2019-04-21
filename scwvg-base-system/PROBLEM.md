
#### 出现问题一
* 问题明细如下：
    > java.lang.NoSuchMethodError: javax.servlet.http.HttpServletRequest.isAsyncStarted()......
    
* 解决问题：
    > pom.xml 文件中 **javax.servlet.javax.servlet-api 依赖**不能与 **org.apache.tomcat.servlet-api 依赖**共存

#### 出现问题二
 * 问题明细如下：
    > thymeleaf 使用 springSecurity 表达式 sec:authentication="name"取不到值问题?
* 解决问题：
    + 导入的 thymeleaf 的 security 扩展版本 必须与 springSecurity 的版本一直
    + 该工程 引入依赖jar如下：
        ```xml
        <!-- thymeleaf 模板依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <!-- springSecurity 权限控制依赖 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <!-- thymeleaf layout依赖 必须引入否则取不到值 -->
        <dependency>
            <groupId>nz.net.ultraq.thymeleaf</groupId>
            <artifactId>thymeleaf-layout-dialect</artifactId>
            <version>2.0.5</version>
        </dependency>
        <!-- thymeleaf-extras-springsecurity5 与 springSecurity 5 必须一直 -->
        <dependency>
            <groupId>org.thymeleaf.extras</groupId>
            <artifactId>thymeleaf-extras-springsecurity5</artifactId>
            <version>3.0.4.RELEASE</version><!--$NO-MVN-MAN-VER$-->
        </dependency>
        ```
#### 出现问题三
 * 问题明细如下：
    > thymeleaf + layui 渲染错误 ；org.thymeleaf.exceptions.TemplateProcessingException: Could not parse as expression: ......"
* 解决问题：
    ````js
    ,cols: [[
        {field:'id', title: 'ID', sort: true}
       ,{field:'username', title: '用户名'}  
      ]]
    
     也就是把cols后的[[ ]]变为
        [
          [
            
          ]
        ]
    ````
#### 出现问题四
 * 问题明细如下：
    > cting getter definitions for property异常，org.codehaus.jackson.map.JsonMappingException: Conflicting getter definitions for property "enabled": com.scwvg.system.vo.UserVO#isEnabled
    
    > 错误信息大概意思是 在我们的类中有个 isEnabled，还有 getEnabled 两个方法，jackson无法确定唯一的getter函数。 
 * 解决问题：
    字段去掉 **is** 打头