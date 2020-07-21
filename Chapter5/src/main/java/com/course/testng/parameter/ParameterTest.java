package com.course.testng.parameter;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

/**
 * @author LanTian
 * @create 2020-07-16 21:34
 */
public class ParameterTest {

    @Test
    @Parameters({"name", "age"})
    public void test01(String name, int age){
        System.out.println("name=" + name + ";age=" + age);
    }

    @Test(dataProvider = "data")
    public void test02(String name, int age){
        System.out.println("Test02:name=" + name + ";age=" + age);
    }

    @Test(dataProvider = "data")
    public void test03(String name, int age){
        System.out.println("Test03:name=" + name + ";age=" + age);
    }


    @DataProvider(name = "data")
    public Object[][] providerData(Method method){

        Object[][] obj = null;
        if("test02".equals(method.getName())){
            obj = new Object[][]{
                    {"zhangli", 12},
                    {"zhaoliu", 25}
            };
        }else if("test03".equals(method.getName())){
            obj = new Object[][]{
                    {"liuhuan", 45},
                    {"wangbao", 69}
            };
        }

        return obj;
    }

}
