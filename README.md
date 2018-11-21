# SSHProject
*SSH框架的搭建过程*  
&emsp;&emsp;因为Javaee的课程要求搭建SSH框架，我在搭建过程中出现了很多的问题，版本不匹配，缺包等大大小小的问题。
成功之后，我决定把搭建框架的过程记录下来。

---
## 1.基础说明
+ Eclipse
+ Spring 3.2.8
+ hibernate 4.3.11
+ struts 2.5
+ mysql
---
## 2.准备工作
&emsp;&emsp;先创建一个Web Project。将相关的jar包导入到工程的\WebContent\WEB-INF\lib下。
### 2.1 spring
&emsp;&emsp;下载地址：https://repo.spring.io/libs-release-local/org/springframework/  
![spring相关包](/images/spring-3.2.8.png)
### 2.2 hibernate
&emsp;&emsp;下载地址：http://hibernate.org/  
![hibernate相关包](/images/hibernate-4.3.11.png)
### 2.3 struts
&emsp;&emsp;下载地址：http://struts.apache.org/download.cgi  
![struts相关包](/images/struts-2.5.png)  
*除了以上的jar包之外，还需要数据库连接池的jar包，具体可以在完整框架里面查看*
### 2.4 创建数据库
&emsp;&emsp;创建好数据库里的表。

---
## 3.配置Struts2
### 3.1修改web.xml配置文件
在web.xml文件中添加自己的控制器及HTTP请求的过滤器映射  
```
  <?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://xmlns.jcp.org/xml/ns/javaee" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd" id="WebApp_ID" version="4.0">
  <display-name>SSHProject</display-name>
  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>
  <!-- struts2 -->
  <!-- 定义控制器（过滤器）名称及实现的类文件 -->
  <filter>
    <filter-name>struts2</filter-name>
    <filter-class>
        org.apache.struts2.dispatcher.filter.StrutsPrepareAndExecuteFilter
    </filter-class>
  </filter>
  <!-- 定义过滤器映射 -->
  <filter-mapping>
    <filter-name>struts2</filter-name>
    <url-pattern>*.action</url-pattern>
  </filter-mapping>
```
### 3.2新建struts.xml配置文件
手动在工程/src/目录下添加配置文件struts.xml
```
<?xml version="1.0" encoding="UTF-8"?>

<!DOCTYPE struts PUBLIC
    "-//Apache Software Foundation//DTD Struts Configuration 2.0//EN"
    "http://struts.apache.org/dtds/struts-2.3.dtd">
<struts> 
    <package name="SSHLogin" extends="struts-default">
       <action name="login" class="com.action.LoginAction">
           <result name="SUCCESS">/Success.jsp</result>
           <result name="FAILURE">/Failure.jsp</result>
       </action>
    </package>
</struts>
```
### 3.3创建jsp文件
&emsp;&emsp;本工程表示层的页面有三个。
主页面（index.jsp）提示用户输入用户名、密码，进行登录。
登录成功，系统显示Success.jsp；登录失败，系统显示Failure.jsp。
页面具体代码可以直接在工程里面查看。
### 3.4编写Action类
编写一个LoginAction类，申请处理超链接。  

+ 类名：LoginAction
+ 包名：com.action
+ 父类：com.opensymphony.xwork2.ActionSupport  

```
public String execute() throws Exception {
       System.out.println("点击登录执行该方法");
       return "SUCCESS";
    }

```
### 3.4编写实体类和配置文件
&emsp;&emsp;实体类的属性名要和数据库的列名对应起来。每一个属性的get\set方法都要有，放在com.entity包下。  
&emsp;&emsp;完成实体类之后，可以生成hibernate配置文件：
*File -> New -> Other -> Hibernate -> Hibernate XML Mapping file (hbm.xml) -> next ->Add Class*
之后就选择自己写好的实体类，就可以自动生成。

### 3.5测试struts
&emsp;&emsp;点击运行，配置成功的话，会出现登录界面。
此时无论你的输入是什么，都会跳转到登录成功的界面。  

---
## 4 整合Spring
&emsp;&emsp;前面还算比较简单，错误不多。从这里开始，会有大量的错误伴随，需要有些耐心。
### 4.1修改web.xml配置文件
需要在配置文件中添加Spring的配置。
```
  <!-- Spring 配置和监听start -->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>
  <listener>
    <listener-class>    
    org.springframework.web.context.ContextLoaderListener
    </listener-class>
  </listener>
```
### 4.2创建applicationContext.xml文件
在src目录下创建applictionContext.xml文件，这是spring的配置文件。
```
<bean id="myLoginAction" class="com.action.LoginAction"></bean>

```
并且将struts.xml中的*com.action.LoginAction*改为*myLoginAction*
### 4.3测试
&emsp;&emsp;再次测试程序，如果报错，查看控制台的错误信息，百度的时候尽量百度Couse By的错误提示。

---
## 5 继续整合Hibernate
### 5.1编写Hibernate的配置文件
在src目录下创建hibernate.cfg.xml。
```
    <session-factory>
        <!-- 数据库连接配置 -->
    	<property name="connection.driver_class">com.mysql.jdbc.Driver</property>
    	<property name="connection.url">jdbc:mysql://localhost:3306/mydb</property>
        <property name="connection.username">root</property>
        <property name="connection.password">root</property>
        
        <property name="dialect">org.hibernate.dialect.MySQL5Dialect</property>
        <!-- 设置默认的数据库连接池 -->
        <property name="connection.pool_size">5</property>
        
        <!-- 显示SQL -->
        <property name="show_sql">true</property>
        
        <!-- 格式化SQL -->
        <property name="format_sql">true</property>
        
        <!-- 根据schema更新数据表的工具 -->
        <property name="hbm2ddl.auto">update</property>        
        
        <!-- 数据表映射配置文件 -->
        <mapping resource="com/entity/User.hbm.xml"/>
    </session-factory>
```
&emsp;&emsp;注意数据库名，登录用户名和密码不要写错。
### 5.2创建相应的类
以下只写了一些主要方法的代码，完整的代码可以在文件里面查看。  

+ 类名：IndexServiceImpl
+ 包名：com.service
+ 接口：IndexService
```
public Integer check(String username,String password) {
    	// TODO Auto-generated method stub
		try {
			User user = id.checkandlogin(username, password);
			if (user!=null) {
				return user.getCid();
			}
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return null;
	}
```
+ 类名：IndexDaoImpl
+ 包名：com.dao
+ 接口：IndexDao
```
public User checkandlogin(String username, String password) {
    	// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		
		List<User> list = session.createQuery("from User where username = '"+username+"'and password = '"+password+"'").list();
		if(list.size() == 1) {
			System.out.println("Ok "+username);
			close(session);
			return (User)list.get(0);
		}
		close(session);
		return null;
	}
```
### 5.3修改LoginAction.java
```
public String execute() throws Exception{
    	System.out.println("点击登录执行该方法");
		if (username == null || username.equals("")) {
			return "FAILURE";
		}
		Integer userId = is.check(username, password);
		if (userId != null) {
			System.out.println("登陆成功");
			return "SUCCESS";
		}else {
			System.out.println("登录错误");
			return "FAILURE";
		}
	}
```
&emsp;&emsp;*每一个新加了属性的类都需要添加get/set方法。*
### 5.4编辑，修改配置文件
打开Spring的配置文件applicationContext.xml,添加如下配置行
```
    <bean id="myLoginAction" class="com.action.LoginAction" scope="prototype">
    	<property name="is" ref="myIndexService"></property>
	</bean>
	
	<bean id="myIndexService" class="com.service.IndexServiceImpl" scope="prototype">
		<property name="id" ref="myIndexDao"></property>
	</bean>
	
	<bean id="myIndexDao" class="com.dao.IndexDaoImpl" scope="prototype">
		<property name="sessionFactory" ref="sessionFactory"></property>
	</bean>
	
	<bean id="sessionFactory" class="org.springframework.orm.hibernate4.LocalSessionFactoryBean" scope="prototype">
		<property name="configLocation" value="classpath:hibernate.cfg.xml"></property>
	</bean>
```
### 5.5测试
&emsp;&emsp;至此，所有步骤完成。测试程序，查看结果。

---
## 6 总结
&emsp;&emsp;配置采用的是逐步配置逐步测试，每一步都成功测试之后再进行下一步的配置，
可以提早查出错误，提早修改；也会对框架有一个比较全面的了解。
如果觉得反复修改配置文件比较复杂，可以一次全部完成之后再进行测试，源码可以直接查看SSHProject。  

---
## 7 注意事项
*根据我和我的同学在搭建过程当中出现的一些问题，提示需要注意的几个方面，
报错但不知错在哪里可以查看以下以下几点：*  

+ 注意jar包之间的冲突。名字一样，版本不同，删除其中一个。
+ applicationContext.xml配置文件中的 *id,class,name,ref* 都要一一对应。需要细心检查。
+ 每一个类里面的属性都需要set/get方法。（虽然不是每个都要有才可以正常运行，但是为了不出错，所有属性都创立set/get方法更保险）
+ sql语句不要写错！这个错误可以在控制台看出来。（我的室友因为这个错误干耗了2个小时）
+ 所有配置文件尽量选择系统生成，不会出现很奇怪并且不知如何解决的错误。
