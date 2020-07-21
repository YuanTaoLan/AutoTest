package com.course.testng.groups;

import org.testng.annotations.Test;

/**
 * @author LanTian
 * @create 2020-07-16 20:57
 */
@Test(groups = "stu")
public class GroupOnClass02 {

    public void stu01(){
        System.out.println("GroupOnClass02中的stu01运行了");
    }

    public void stu02(){
        System.out.println("GroupOnClass02中的stu02运行了");
    }

}
