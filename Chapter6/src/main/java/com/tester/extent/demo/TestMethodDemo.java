package com.tester.extent.demo;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

/**
 * @author LanTian
 * @create 2020-07-16 22:43
 */
public class TestMethodDemo {

    @Test
    public void test01(){
        Assert.assertEquals(1,2);
    }

    @Test
    public void test02(){
        Assert.assertEquals(1,1);
    }

    @Test
    public void test03(){
        Assert.assertEquals("aaa","aaa");
    }

    @Test
    public void logDemo(){
        Reporter.log("这是我们自己写的日志");
        new RuntimeException("这是我们自己的运行时异常");
    }

}
