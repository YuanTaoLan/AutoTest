package com.tao.course.cases;

import com.alibaba.fastjson.JSONObject;
import com.tao.course.config.TestConfig;
import com.tao.course.model.AddUserCase;
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

public class AddUserTest {

    @Test(dependsOnGroups = "loginTrue", description = "添加用户接口接口")
    public void addUser() throws IOException, InterruptedException {

        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase", 1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //下边的代码为写完接口的测试代码
        String result = getResult(addUserCase);

        //处理结果，就是判断返回结果是否符合预期
        Assert.assertEquals(addUserCase.getExpected(), result);

    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        //下边的代码为写完接口的测试代码
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName", addUserCase.getUserName());
        param.put("password", addUserCase.getPassword());
        param.put("sex", addUserCase.getSex());
        param.put("age", addUserCase.getAge());
        param.put("permission", addUserCase.getPermission());
        param.put("isDelete", addUserCase.getIsDelete());
        //设置请求头信息 设置header
        post.setHeader("content-type", "application/json");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toJSONString(), "utf-8");
        post.setEntity(entity);
        //声明一个对象来进行响应结果的存储
        String result;
        //执行post方法
        HttpResponse response = TestConfig.closeableHttpClient.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        return result;
    }


}
