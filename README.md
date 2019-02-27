# Microservice
MSF4J for survey

![alt text](https://cdn-images-1.medium.com/max/1600/0*bqCEfzwZJowivElX.png)

https://docs.wso2.com/display/EI611/About+this+Release

**WSO2** 提供了整合平台，由 Enterprise Service Bus (ESB) 管理 MSF4J 框架所開發出的 service ，這篇以如何使用 MSF4J 開發 microservice 為主題。


## **佈署方式**
`mvn clean install`

每次執行前執行後 project 中會建立出 target 資料夾，其中包含 jar file

加上 **clean** 指令可以在每次打包時刪除 target 資料夾並建立一個新的

`java -jar target/wso2_msf4j-2.6.5-SNAPSHOT.jar`

執行後 service 將在指定的 port 上運行

在網址列輸入 `http://localhost:8080/menu` 即可看到程式運行結果

MSF4J 支援 JAXRS annotations，開發時可參考 [Java 官方文件](https://docs.oracle.com/javaee/6/tutorial/doc/gilik.html#ginpw)


## **開發時啟動方式**

程式碼中以 package 區別出使用 spring 與不使用 spring 來管理 service

在 eclipse 中開啟下列任一 Application，開發工具上方列表 `Run > Run as > Java Application`

<pre>
<code>
package com.evn.msf4j.original;

public class MyApplication {

	public static void main(String[] args) {
		System.out.println("Start Service");
		new MicroservicesRunner()
        .deploy(new MyService(), new MenuService())
        .start();

	}

}
</code>
</pre>

<pre>
<code>
package com.evn.msf4j.spring;

public class MySpringApplication {

	public static void main(String[] args) {
		System.out.println("Start Spring Service");
		MSF4JSpringApplication.run(MySpringApplication.class, args);
	}

}
</code>
</pre>

## **修改 Port**
**MicroservicesRunner** 建構式可傳入 port，如果欲將 port 修改為7070可參考下列

<pre>
<code>
public class MyApplication {

	public static void main(String[] args) {
		System.out.println("Start Service");
		new MicroservicesRunner(7070)
        .deploy(new MyService(), new MenuService())
        .start();

	}

}
</code>
</pre>

**MSF4JSpringApplication** port 設定在 `com.evn.msf4j.spring.TransportConfiguration`，如下所示

<pre>
<code>
package com.evn.msf4j.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.wso2.msf4j.spring.transport.HTTPTransportConfig;

@Configuration
public class TransportConfiguration {

    @Bean
    public HTTPTransportConfig http() {
        return new HTTPTransportConfig(9090);
    }

}
</code>
</pre>

## **pom.xml**

<pre>
<code>
&lt;project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd"&gt;
    專案繼承自 msf4j，不需要在 pom.xml 維護太多 dependancy
    &lt;parent&gt;
        &lt;groupId&gt;org.wso2.msf4j&lt;/groupId&gt;
        &lt;artifactId&gt;msf4j-service&lt;/artifactId&gt;
        &lt;version&gt;2.6.5-SNAPSHOT&lt;/version&gt;
    &lt;/parent&gt;
    &lt;modelVersion&gt;4.0.0&lt;/modelVersion&gt;
    &lt;artifactId&gt;wso2_msf4j&lt;/artifactId&gt;
    &lt;dependencies&gt;
        Spring libary
        &lt;dependency&gt;
            &lt;groupId&gt;org.wso2.msf4j&lt;/groupId&gt;
            &lt;artifactId&gt;msf4j-spring&lt;/artifactId&gt;
            &lt;version&gt;2.6.5-SNAPSHOT&lt;/version&gt; 
        &lt;/dependency&gt;
	    &lt;dependency&gt;
	      &lt;groupId&gt;junit&lt;/groupId&gt;
	      &lt;artifactId&gt;junit&lt;/artifactId&gt;
	      &lt;version&gt;3.8.1&lt;/version&gt;
	      &lt;scope&gt;test&lt;/scope&gt;
	    &lt;/dependency&gt;
		&lt;dependency&gt;
		    &lt;groupId&gt;com.fasterxml.jackson.core&lt;/groupId&gt;
		    &lt;artifactId&gt;jackson-databind&lt;/artifactId&gt;
		    &lt;version&gt;2.9.8&lt;/version&gt;
		&lt;/dependency&gt;
		&lt;dependency&gt;
		    &lt;groupId&gt;org.skyscreamer&lt;/groupId&gt;
		    &lt;artifactId&gt;jsonassert&lt;/artifactId&gt;
		    &lt;version&gt;1.5.0&lt;/version&gt;
		&lt;/dependency&gt;
    &lt;/dependencies&gt;
    &lt;properties&gt;
        入口程式 (執行 jar 時由哪個 application 載入 service)
        &lt;microservice.mainClass&gt;com.evn.msf4j.spring.MySpringApplication&lt;/microservice.mainClass&gt;
    &lt;/properties&gt;
&lt;/project&gt;
</code>
</pre>

