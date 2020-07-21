package com.course.testng;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

/**
 * @author LanTian
 * @create 2020-07-16 20:09
 */
public class BasicAnnotation {

    @Test(groups = "server")
    public void testCase01(){
        System.out.println("server:这是测试用例1");
    }

    @Test(enabled = false)
    public void testCase02(){
        System.out.println("这是测试用例2");
    }

    @Test(invocationCount = 10, threadPoolSize = 4)
    public void testCase03(){
        System.out.println("server:这是测试用例3");
        throw new RuntimeException();
    }

    @Test(dependsOnMethods = {"testCase03"})
    public void testCase04(){
        System.out.println("client:这是测试用例4，依赖于testCase03");
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testCase05(){
        System.out.println("client:这是测试用例5");
        throw new RuntimeException();
    }

    @BeforeGroups("server")
    public void beforeGroupsOnServer(){
        System.out.println("这是server组之前运行的方法！！！");
    }

    @AfterGroups("server")
    public void afterGroupsOnServer(){
        System.out.println("这是server组之后运行的方法！！！");
    }

}
