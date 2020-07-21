package com.course.testng.groups;

import org.testng.annotations.Test;

/**
 * @author LanTian
 * @create 2020-07-16 20:57
 */
@Test(groups = "teacher")
public class GroupOnClass3 {

    public void teacher01(){
        System.out.println("GroupOnClass03中的teacher运行了");
    }

    public void teacher02(){
        System.out.println("GroupOnClass03中的teacher运行了");
    }

}
