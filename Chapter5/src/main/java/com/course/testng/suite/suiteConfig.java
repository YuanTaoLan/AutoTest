package com.course.testng.suite;

import org.testng.annotations.*;

/**
 * @author LanTian
 * @create 2020-07-16 20:24
 */
public class suiteConfig {

    @BeforeClass
    public void beforeClass(){
        System.out.println("Before Class执行了");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("After Class执行了");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Before Suite执行了");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("After Suite执行了");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("Before Test执行了");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("After Test执行了");
    }

}
