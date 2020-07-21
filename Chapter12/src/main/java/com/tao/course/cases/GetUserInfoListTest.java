package com.tao.course.cases;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tao.course.config.TestConfig;
import com.tao.course.model.GetUserListCase;
import com.tao.course.model.User;
import com.tao.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class GetUserInfoListTest {

    @Test(dependsOnGroups = "loginTrue", description = "获取性别为男的用户信息")
    public void getUserListInfo() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = session.selectOne("getUserListCase", 1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //下边为写完接口的代码
        String resultJson = getJsonResult(getUserListCase);
        /**
         * 可以先讲*/

        Thread.sleep(2000);
        List<User> userList = session.selectList(getUserListCase.getExpected(), getUserListCase);
        for (User u : userList) {
            System.out.println("list获取的user:" + u.toString());
        }
        JSONArray userListJson = new JSONArray(Collections.singletonList(userList));
        System.out.println("list获取的userlistJson字符串 = " + userListJson.toJSONString());

        Assert.assertEquals(userListJson.toJSONString().length()-2, resultJson.length());
//        for (int i = 0; i < resultJson..to(); i++) {
//            JSONObject expect = (JSONObject) resultJson.get(i);
//            JSONObject actual = (JSONObject) userListJson.get(i);
//            Assert.assertEquals(expect.toString(), actual.toString());
//        }

    }

    private String getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
        param.put("userName", getUserListCase.getUserName());
        param.put("sex", getUserListCase.getSex());
        param.put("age", getUserListCase.getAge());
        //设置请求头信息 设置header
        post.setHeader("content-type", "application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(), "utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.closeableHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(), "utf-8");

        System.out.println("调用接口list result:" + result);

        return result;

    }

}
