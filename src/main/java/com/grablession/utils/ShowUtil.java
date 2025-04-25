package com.grablession.utils;

import com.grablession.entity.AlreadyClass;
import com.grablession.entity.Lesson;

import java.util.ArrayList;

/**
 * @author nsh
 * @data 2025/4/24 21:22
 * @description
 **/
public class showLesions {
    public void showAlready(ArrayList<AlreadyClass> list) {
        for (AlreadyClass alreadyClass : list) {
            String te = "教学号： " + alreadyClass.getJxb() +" 课程名称： "+ alreadyClass.getKcmc();
            System.out.println(te);
        }
    }

    public void showLesions(ArrayList<Lesson> list) {
        for (Lesson lesson : list) {
            String te = "教学号： " + lesson.getJxb() +" 课程名称： "+ lesson.getKcmc() + " 任课老师： " + lesson.getKcmc();
            System.out.println(te);
        }
    }
}
