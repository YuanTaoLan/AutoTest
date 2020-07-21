package com.course.httpclient.demo;

import com.sun.jndi.toolkit.url.Uri;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LanTian
 * @create 2020-07-19 9:35
 */
public class MyHttpClient {

    private CloseableHttpClient client = null;
    private CloseableHttpResponse response = null;

    @BeforeTest
    public void beforeTest(){
        //获取client对象
        client = HttpClientBuilder.create().build();
    }

    @Test
    public void test01() throws IOException {

        /*创建Get请求*/
        HttpGet get = new HttpGet("http://localhost:8890/get");

        /*响应模型*/
        response = client.execute(get);

        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("result = " + result + ";  statusCode=" + statusCode);

    }

    @Test
    public void test02(){

        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", "zhangyuantao"));
        params.add(new BasicNameValuePair("age", "23"));

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("localhost")
                    .setPort(8890)
                    .setPath("/getwithparam")
                    .setParameters(params).build();

            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(5000)
                    .setConnectionRequestTimeout(5000)
                    .setSocketTimeout(5000)
                    .setRedirectsEnabled(true).build();
            HttpGet get = new HttpGet(uri);
            get.setConfig(config);

            response = client.execute(get);

            HttpEntity entity = response.getEntity();
            System.out.println("entity.getContentLength() = " + entity.getContentLength());
            System.out.println("EntityUtils.toString(entity) = " + EntityUtils.toString(entity));

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test03(){

        try {
            HttpPost post = new HttpPost("http://localhost:8890/post");
            response = client.execute(post);
            System.out.println("EntityUtils.toString(response.getEntity()) = " + EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test04(){

        try {
            List<NameValuePair> formparams = new ArrayList<>();
            formparams.add(new BasicNameValuePair("name", "huhansan"));
            formparams.add(new BasicNameValuePair("age", "34"));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "utf-8");

            HttpPost post = new HttpPost("http://localhost:8890/postwithparam");
            post.setEntity(entity);
            response = client.execute(post);

            System.out.println("EntityUtils.toString(response.getEntity()) = " + EntityUtils.toString(response.getEntity()));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @AfterTest
    public void afterTest(){

        try {
            if(response != null) {
                response.close();
            }
            if(client != null){
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
