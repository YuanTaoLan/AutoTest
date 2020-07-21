package com.course.httpclient.cookies;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author LanTian
 * @create 2020-07-19 10:07
 */
public class MyCookiesForPost {

    private String baseUri = null;
    private ResourceBundle bundle = null;
    private CookieStore cookieStore = null;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application");
        baseUri = bundle.getString("test.baseUri");
    }

    @Test
    public void testGetCookies() throws IOException {
        String getCookiesUri = bundle.getString("getCookies.uri");
        cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        HttpGet get = new HttpGet(baseUri + getCookiesUri);
        CloseableHttpResponse response = client.execute(get);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println("result = " + result);

        /*获取Cookies的信息*/
        List<Cookie> cookies = cookieStore.getCookies();
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name = " + name);
            System.out.println("value = " + value);
        }
    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testPostWithCookies() throws IOException {
        /*设置uri*/
        String postWithCookies = bundle.getString("postWithCookies.uri");

        /*创建HttpClient对象*/
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();

        /*创建Post方法对象*/
        HttpPost post = new HttpPost(baseUri + postWithCookies);

        /*新建Json对象*/
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("name", "huhansan");
        jsonObj.put("age", "45");

        /*设置请求头信息*/
        post.setHeader("Content-Type", "application/json");

        /*设置Json到请求参数中*/
        StringEntity jsonEnt = new StringEntity(jsonObj.toString(), "utf-8");
        post.setEntity(jsonEnt);

        /*执行请求*/
        HttpResponse response = client.execute(post);
        String result = EntityUtils.toString(response.getEntity());
        System.out.println("result = " + result);

        /*获取响应状态码*/
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode = " + statusCode);

    }

}
