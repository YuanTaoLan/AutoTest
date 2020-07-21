package com.course.testng.groups;

import org.testng.annotations.Test;

/**
 * @author LanTian
 * @create 2020-07-16 20:57
 */
@Test(groups = "stu")
public class GroupOnClass01 {

    public void stu01(){
        System.out.println("GroupOnClass01中的stu01运行了");
    }

    public void stu02(){
        System.out.println("GroupOnClass01中的stu02运行了");
    }

}
