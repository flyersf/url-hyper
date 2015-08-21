#组件说明:遍历项目的web访问地址
#组件功能：查看项目有哪些WEB URL,可以用于检查项目的安全性，也可以提供给测试进行url安全扫描，建议用于测试工作，上线时不要对外开放

#使用说明：
1.添加依赖jar包,选择版本号
<dependency>
	<groupId>com.walker</groupId>
	<artifactId>commons-url-hyper</artifactId>
	<version>1.0.0-SNAPSHOT</version>
</dependency>

2.配置spring文件，添加如下代码：

	<mvc:annotation-driven />
	<context:component-scan base-package="com.walker.controller.component" />
	<bean id="urlTraversalComponent" class="com.walker.controller.component.UrlTraversalComponent">
		<property name="status">
			<value>true</value>
		</property>
		<property name="ignoreUrls">
			<list>
				<value>/admin/urlhyper/show</value>
			</list>
		</property>
	</bean>
属性说明：
status： false:关闭  true:打开
ignoreUrls：忽略不需要的地址

3.接口访问地址：http://项目域名/admin/urlhyper/show
为了安全，建议用于测试工作，上线时不要对外开放。如果要对外，可以将数据进行加密后返回，比如使用AES加密解密。

