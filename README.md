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

如果遇到一直無法排除的錯誤，請試著將 **Maven** 中的 `.m2\repository\org\wso` 刪除，重新 build 一次 project

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
    #專案繼承自 msf4j，不需要在 pom.xml 維護太多 dependancy
    &lt;parent&gt;
        &lt;groupId&gt;org.wso2.msf4j&lt;/groupId&gt;
        &lt;artifactId&gt;msf4j-service&lt;/artifactId&gt;
        &lt;version&gt;2.6.5-SNAPSHOT&lt;/version&gt;
    &lt;/parent&gt;
    &lt;modelVersion&gt;4.0.0&lt;/modelVersion&gt;
    &lt;artifactId&gt;wso2_msf4j&lt;/artifactId&gt;
    &lt;dependencies&gt;
        #Spring libary
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
        #入口程式 (執行 jar 時由哪個 application 載入 service)
        &lt;microservice.mainClass&gt;com.evn.msf4j.spring.MySpringApplication&lt;/microservice.mainClass&gt;
    &lt;/properties&gt;
&lt;/project&gt;
</code>
</pre>

## **`com.evn.msf4j.original.MyApplication`**

WSF4J 透過 **MicroservicesRunner.deploy()** 來啟動 service， deploy() 傳入的參數為將提供的 service class 實例(instance) 陣列

`new MicroservicesRunner().deploy( new MyService(), new MenuService() ).start();`

<pre>
<code>

@Path("/")
public class MyService {
	
	@GET
	public String index() {
		return "default content";
	}
	
	@GET
	@Path("/hello/{name}")
	public String hello(@PathParam("name") String name) {
		return "Hello " + name;
	}
}


@Path("/menu")
public class MenuService {

	OrigianlMealDao mealDao = new OrigianlMealDao();

	@Path("/")
	@GET
	@Produces({"application/json"})
	public Response index() {
		return Response.ok().entity(mealDao.findAll()).build();
	}

	@Path("/findMeal/{id}")
	@GET
	@Produces({"application/json"})
	public Response findMeal(@PathParam("id") int id) {
		return Response.ok().entity(mealDao.find(id)).build();
	}
	
	@Path("/{id}")
	@GET
	@Produces({"application/json"})
	public String meal(@PathParam("id") int id) {
		Gson gson = new Gson();
		return gson.toJson(mealDao.find(id));
	}

	@PostConstruct
    public void init() {
		// Invoke by the container on newly constructed service instances after all dependency injection has completed and before transport starts.
        System.out.println("MenuService is calling PostConstruct method");
    }

    @PreDestroy
    public void close() {
    	// Invoke by the container during server shutdown before the container removes the service instance.
    	System.out.println("MenuService is calling PreDestroy method");
    }

}
</code>
</pre>

Service class 搭配 JAXRS annotations 來串接 **REST** (Representational State Transfer)

上述例子，當服務啟動時，主機下根目錄會對應到 **MyService**，因這個 class 利用 @Path 註冊了 url 根目錄(/)

所以當你在瀏覽器輸入`http://localhost:8080/` 網址時，瀏覽器所發出的請求會導到 **MyService** 的 **index()** method

Service method 所回傳的資料型態如果定義為 **String** ，所 return 的字串會直接傳回到瀏覽器

如果回傳的資料型態定義為 **Response**，則我們可以透過 **Response** 物件來封裝回傳資料

<pre>
<code>
return Response.ok().entity(mealDao.find(id)).build()
</code>
</pre>

這段程式碼說明，Response Code 為 200，並將 `com.evn.msf4j.model.Meal` bean 透過 **enitity()** 轉成 json 格式回傳

<pre>
<code>
return Response.ok().entity(mealDao.findAll()).build()
</code>
</pre>

**enitity()** 也能將 List\<Meal\> 轉為 jsonArray

**@Path** 定義在 class 上表示這個 service 在服務器上所註冊的目錄，定義在 method 上表示在這 service 上所對應的子目錄

`http://localhost:9090/menu/findMeal/0`

上述 url 會導向 **MenuService** 的 **findMeal(int id)**

**@PathParam** 變數對應到 **@Path** 中所定義的參數，如 `@PathParam("id")` 取得 `@Path("/findMeal/{id}")` 的 id 值


**@GET** 定義此 method 服務 Http GET 請求(Request)，可用其他 Annotation 分別對應 Http PUT、POST、DELETE 等

**@Produces** 可產生的Mime Type，對應到 Http Request Header 的 Accept

**@PostConstruct** 當服務器啟動， service 被掛載上去的時候，會觸發這個 annotation 綁定的 method

**@PreDestroy** 當服務器關閉， service 被移除之前會觸發這個 annotation 綁定的 method



## **`com.evn.msf4j.spring.MySpringApplication`**

這小節將敘述如何透過 Spring 開發 microservice

**MSF4JSpringApplication** 會自動載入目錄/子目錄下所註冊的 Spring Component，只須在 Service Class 前宣告 ""@Component"" 即可

`MSF4JSpringApplication.run(MySpringApplication.class, args);`

<pre>
<code>
@Component
@Path("/myComponent")
public class MyService {

    @GET
    @Path("/restAPI/{value}")
    public String restAPI(@PathParam("value") String value) {
        return "restAPI " + value;
    }
}

@Component
@Path("/menu")
public class MenuService {
	
    @Autowired
    private MealSpringService mealService;
	
    @GET
    @Path("/")
    @Produces({ "application/json" })
    public Response index() {
        return Response.ok().entity(mealService.findAll()).build();
    }
	
    @GET
    @Path("/html")
    @Produces({ "application/json" })
    public Response html() {
        Map map = Collections.singletonMap("meals", mealService.findAll());
        String html = "&lt;body&gt;&lt;h1&gt; " + map.get("meals") + "&lt;h1\&gt;&lt;body\&gt;";
        return Response.ok()
          .type(MediaType.TEXT_HTML)
          .entity(html)
          .build();
    }
 
    @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    public Response meal(@PathParam("id") int id) {
        return Response.ok().entity(mealService.find(id)).build();
    }

</code>
</pre>

在使用 Spring 開發時，物件交由 Spring 管理，所以在使用物件前，我不們不會透過 new 的方式來建立實例 (instance)，而是用 Annotation 註冊的方式，告知 Spring 對物件做注入的動作 (inject)

<pre>
<code>

@Service
public class MealSpringService {
	
    @Autowired
    MealDao mealDao;
 
    public Meal find(int id) {
        return mealDao.find(id);
    }
 
    public List<Meal> findAll() {
        return mealDao.findAll();
    }
 
    public void create(Meal meal) {
    	mealDao.create(meal);
    }
}
</code>
</pre>

如上程式碼，**MealSpringService** 透過 **@Service** 向 Spring 註冊為 Spring service <span style="color:red">(注意，這裡指的是 Spring 的 service，並非microservice 的 service)</span>，而 **MenuService** 宣告的 MealSpringService 物件定義了 **@Autowired** ，告訴 Spring 此物件要用注入的方式取得，透過 Spring 給予實例，而不是用 new 的方式產生實例。

**MealDao** 同上述用法，在提供的地方註冊 **@Service** ，使用的地方注入 **@Autowired**
