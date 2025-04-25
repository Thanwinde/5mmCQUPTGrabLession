package com.grablession;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.grablession.Grab.Grab;

import com.grablession.entity.Want;
import com.grablession.utils.ShowUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * @author nsh
 * @data 2025/4/24 20:52
 * @description
 **/
public class Start {

    static Grab grab = new Grab();

    static String PHPSESSID;

    static int gap;

    static ArrayList<Want> Suc = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        ShowUtil showUtil = new ShowUtil();

        /*

        GetAlready getAlready = new GetAlready();
        GetRW getRW = new GetRW();
        GetZR getZR = new GetZR();
        GetWY getWY = new GetWY();
        ArrayList<AlreadyClass> alreadyClass = getAlready.getAlreadyLesions(PHPSESSID);
        ArrayList<Lesson> RWLesions = getRW.getRWLesions(PHPSESSID);
        ArrayList<Lesson> ZRLesions = getZR.getRWLesions(PHPSESSID);
        ArrayList<Lesson> WYLesions = getWY.getWYLesions(PHPSESSID);*/

        System.out.println("本软件由5mm应援团制作，仅供参考学习，下载者不得用于商业以及非法用途并在24小时内将其删除。由本软件造成的一切后果与制作者无关");
        System.out.println("GitHub： https://github.com/Thanwinde");
        System.out.println("- 本软件仅供个人学习与交流使用，严禁用于商业以及不良用途。\n" +
                "- 如有发现任何商业行为以及不良用途，本软件作者有权撤销使用权。\n" +
                "- 使用本软件所存在的风险将完全由其本人承担，本软件作者不承担任何责任。\n" +
                "- 因不当使用本软件而导致的任何意外、疏忽、合约毁坏、诽谤、版权或其他知识产权侵犯及其所造成的任何损失，本软件作者概不负责，亦不承担任何法律责任。\n" +
                "- 对于因不可抗力或因黑客攻击、通讯线路中断等不能控制的原因造成的服务中断或其他缺陷，导致用户不能正常使用，本软件作者不承担任何责任，但将尽力减少因此给用户造成的损失或影响。\n" +
                "- 本声明未涉及的问题请参见国家有关法律法规，当本声明与国家有关法律法规冲突时，以国家法律法规为准。 \n" +
                "- 本软件相关声明版权及其修改权、更新权和最终解释权均属本软件作者所有。");


        String str = ResourceUtil.readUtf8Str("file:aim.json");
        if(str.isEmpty()){
            System.out.println("aim文件为空或格式错误！");
            return;
        }
        JSONArray jsonArray = JSONUtil.parseArray(str);

        ArrayList<Want> wants = new ArrayList<>();

        PHPSESSID = jsonArray.getJSONObject(0).get("Cookie").toString();
        gap = (int) jsonArray.getJSONObject(0).get("gap");
        for(int i = 1; i < jsonArray.size(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            Want te = JSONUtil.toBean(obj, Want.class);
            wants.add(te);
        }

        System.out.println("你的信息如下：");
        System.out.println("Cookie: " + PHPSESSID);
        System.out.println("间隔: " + gap + " (注意！这是每个线程的间隔，实际会更短，太低(<0.1s)可能会被ban)");
        showUtil.showWants(wants);
        System.out.println("输入任何内容启动:");
        Scanner scanner = new Scanner(System.in);
        String start = scanner.nextLine();
        scanner.close();

        ArrayList<MyThread> threads = new ArrayList<>();

        for (Want want : wants) {
            MyThread thread = new MyThread(want);
            threads.add(thread);
            thread.start();
        }

        for (MyThread thread : threads) {
            thread.join();
        }

        System.out.println("所有课均已抢到!如下:");
        for(Want want : Suc){
            System.out.println(want.getJxb() + " " + want.getKcmc());
        }

        Thread.sleep(1000L * 60 * 10);
    }
}

class MyThread extends Thread {
    Want want;

    public MyThread(Want want){
        this.want = want;
    }

    public void run() {

        int cnt = 1;

        while(true) {

            String msg = Start.grab.tryGrab(want, Start.PHPSESSID);
            Date date = new Date();
            if (msg.equals("666")) {
                System.out.println( "第" + cnt +"次尝试: " + date +" " + want.getJxb() + " " + want.getKcmc() + " 抢到了！\n");
                Start.Suc.add(want);
                break;
            } else {
                System.out.println("第" + cnt +"次尝试: " + date +" " + want.getJxb() + " " + want.getKcmc() + " 没抢到！原因：" + msg + "\n");
            }

            try {
                Thread.sleep(Start.gap);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            cnt++;
        }

    }
}
