package com.tao.course.cases;


import com.alibaba.fastjson.JSONObject;
import com.tao.course.config.TestConfig;
import com.tao.course.listener.MyFileAlterationMonitor;
import com.tao.course.listener.MyFileListenerAdaptor;
import com.tao.course.model.InterfaceName;
import com.tao.course.model.LoginCase;
import com.tao.course.utils.ConfigFile;
import com.tao.course.utils.DatabaseUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {


    @BeforeTest(groups = "loginTrue", description = "测试准备工作,获取HttpClient对象")
    public void beforeTest() {
        /*获取HttpClient对象，并设置CookieStore*/
        TestConfig.closeableHttpClient = HttpClientBuilder.create().setDefaultCookieStore(TestConfig.store).build();
        /*初始化各个接口的URI*/
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);

//        MyFileAlterationMonitor monitor = new MyFileAlterationMonitor(
//                "E:\\WorkSpaces\\IdeaProjects\\AutoInterfaceTest\\AutoTest\\Chapter12\\test-output",
//                ".html",
//                new MyFileListenerAdaptor());
//        monitor.start();

    }

    @Test(groups = "loginTrue", description = "用户成功登陆接口")
    public void loginTrue() throws IOException {

        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //下边的代码为写完接口的测试代码
        String result = getResult(loginCase);
        System.out.println("result = " + result);
        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(loginCase.getExpected(), result);

    }

    @Test(description = "用户登陆失败接口")
    public void loginFalse() throws IOException {
        SqlSession session = DatabaseUtil.getSqlSession();
        LoginCase loginCase = session.selectOne("loginCase", 2);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //下边的代码为写完接口的测试代码
        String result = getResult(loginCase);
        System.out.println("result = " + result);
        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(loginCase.getExpected(), result);

    }

    private String getResult(LoginCase loginCase) throws IOException {
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName", loginCase.getUserName());
        jsonObject.put("password", loginCase.getPassword());

        //设置请求头信息 设置header
        post.setHeader("content-type", "application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(jsonObject.toJSONString(), "utf-8");
        post.setEntity(entity);
        //执行post方法
        CloseableHttpResponse response = TestConfig.closeableHttpClient.execute(post);
        //获取响应结果
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        return result;
    }


}
