# Java代码生成器
## 介绍
这是一个基于数据库表<br/>用来自动生成 Model & Mapper & Service & ServiceImpl & Controller 等代码的代码生成器<br/>
使用者可以通过修改ftl模板来生成自己所需的基本代码块<br/>
主要是为了解决日常工作、练习中代码的重复工作量<br/>
目前仅支持 Mybatis 底层代码的生成

注：本项目fork自https://github.com/zhaohaihao/CodeGenerator ，感谢原作者zhaohaihao。不过我在使用代码的时候发现原项目有很多功能或者使用场景不适合我，因而我在原来项目的基础之上做了大的调整。在原来的基础之上修改点如下：
1、去掉依赖tk.mybatis和pagehelper的依赖，完全自已实现相关的逻辑，因为我还是觉得mybatis原始的实现方式更适合我；
2、实现自定义的CustomizeJavaMapperGenerator和CustomizeXMLMapperGenerator，可以灵活的定制自已经需要生成的功能；
3、实现了按条件的分页查询，并在Controler层实现统一返回结果；
4、去掉了一些原来需要用户参与的配置和定制，完全按照MybatisGenerator的标准实现，使配置看起来更简洁，代码实现也更简单；
5、增加单元测试的相关的依赖，方便对生成代码的单元测试。


## 目录结构
```
├── src
│   ├── main
│   │   ├── java   
│   │   │   └── com
│   │   │       ├── codegen 
│   │   │       │   └── vo   // 项目需要的代码
│   │   │       │       ├── Page.java
│   │   │       │       └── Result.java
│   │   │       └── gencode  // 存放生成的代码（该目录中的代码可以删除）
│   │   │           └── generate
│   │   │               ├── persistence
│   │   │               │   ├── mapper
│   │   │               │   │   └── SitesMapper.java
│   │   │               │   └── model
│   │   │               │       └── Sites.java
│   │   │               ├── service
│   │   │               │   └── SitesService.java
│   │   │               └── web
│   │   │                   └── controller
│   │   │                       └── SitesController.java
│   │   └── resources
│   │       └── com          //生成的mapper.xml（该目录中的内容可以删除）
│   │           └── gencode
│   │               └── generate
│   │                   └── persistence
│   │                       └── mapper
│   │                           └── SitesMapper.xml
│   └── test
│       ├── java
│       │   └── com
│       │       └── codegen
│       │           ├── generator  //根据Mybatis实现的代码和xml配置生成器
│       │           │   ├── CountByConditionElementGenerator.java
│       │           │   ├── CountByConditionMethodGenerator.java
│       │           │   ├── CustomizeJavaMapperGenerator.java
│       │           │   ├── CustomizeXMLMapperGenerator.java
│       │           │   ├── DeleteByConditionElementGenerator.java
│       │           │   ├── DeleteByConditionMethodGenerator.java
│       │           │   ├── SelectByPageElementGenerator.java
│       │           │   └── SelectByPageMethodGenerator.java
│       │           ├── main
│       │           │   └── CodeGeneratorMain.java  //代码生成器的主类
│       │           ├── service
│       │           │   ├── CodeGeneratorConfig.java
│       │           │   ├── CodeGenerator.java
│       │           │   ├── CodeGeneratorManager.java
│       │           │   └── impl
│       │           │       ├── ControllerGenerator.java
│       │           │       ├── ModelAndMapperGenerator.java
│       │           │       └── ServiceGenerator.java
│       │           ├── test //单元测试存的目录（该目录可以删除）
│       │           │   ├── BaseTestCase.java
│       │           │   ├── controller
│       │           │   │   └── SitesControllerTest.java
│       │           │   └── mapper
│       │           │       └── SitesMapperTest.java
│       │           └── util //相关工具类的存放目录
│       │               ├── DataUtil.java
│       │               ├── FileUtil.java
│       │               ├── MethodUtil.java
│       │               └── StringUtils.java
│       └── resources
│           ├── config //用于单元测试的配置（）
│           │   └── jdbc.properties 
│           ├── generator //controller和service的模板所在目录
│           │   └── template
│           │       ├── controller.ftl
│           │       └── service.ftl
│           ├── generatorConfig.properties  //生成器核心配置文件
│           └── spring //用于单元测试的配置
│               ├── applicationContext-db-normal-context-test.xml
│               └── spring-application-context-test.xml
```

## 使用说明
#### 运行
进入到 `src/test/java` 目录下<br />
找到`CodeGeneratorMain`类 为生成器的启动项<br />
直接 `Run As Java Application` 运行即可<br />

#### 修改配置
进入到 `src/test/resources` 目录下<br />
找到 `generatorConfig.properties` 文件<br />
修改对应的参数即可<br />
具体的注释信息可参考 `/src/test/java/com/codegen/service/CodeGeneratorConfig.java` 类<br />

#### 自定义生成器
参考目录src/test/java/com/codegen/generator下面相关的实现

#### 模板样式修改
如果需要生成自己所需的 Controller & Service 样式<br/>
进入到 `src/test/resources/generator/template` 目录下<br />
修改对应的ftl文件即可

#### 数据库表名规则
表名支持单个单词的表名，如”table1“，已经根据下划线分隔的表名如“my_table1”，其它的没有测试。

#### 使用过程遇到问题
使用过程仍存在相关Bug<br />
您可以将详情发送至我的邮箱<a href="mailto:fenglibin@gmail.com">fenglibin@gmail.com</a><br />
万分感激

## 相关参考文档
- MyBatis（[查看官方中文文档](http://www.mybatis.org/mybatis-3/zh/index.html)）
- MyBatis通用Mapper插件（[查看官方中文文档](https://mapperhelper.github.io/docs/)）
- MyBatis PageHelper分页插件（[查看官方中文文档](https://pagehelper.github.io/docs/)）

<br /><br />
