package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
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
public class MyCookiesForGet {

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

        HttpResponse response = client.execute(new HttpGet("http://localhost:8080/getcookies"));
        String result = EntityUtils.toString(response.getEntity());
        System.out.println("result = " + result);

        /*获取Cookies的信息*/
        List<Cookie> cookies = cookieStore.getCookies();
        for(Cookie cookie:cookies){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("name = " + name + ";  value = " + value);
        }

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetWithCookies() throws IOException {
        String getWithCookies = bundle.getString("getWithCookies.uri");
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        HttpResponse response = client.execute(new HttpGet(baseUri + getWithCookies));
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println("result = " + result);
    }

}
