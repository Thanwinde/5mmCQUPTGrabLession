package com.grablession.Grab;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.grablession.entity.Lesson;
import com.grablession.entity.Want;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nsh
 * @data 2025/4/24 21:38
 * @description
 **/
public class Grab {

    CloseableHttpClient httpclient = HttpClients.createDefault();

    public String tryGrab(Want lesson, String PHPSESSID) {
        HttpPost httppost = new HttpPost("http://xk2.cqupt.edu.cn/post.php");
        httppost.setHeader("Cookie", PHPSESSID);
        List<NameValuePair> paramPairs = new ArrayList<>();
        paramPairs.add(new BasicNameValuePair("jxb", lesson.getJxb()));
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramPairs, "UTF-8");
            httppost.setEntity(entity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            JSONObject jsonObject = JSONUtil.parseObj(response.getEntity().getContent());
            response.getEntity().getContent().close();//关闭结果集
            if( (int)jsonObject.get("code") == 0)
                return "666";
            else
                return jsonObject.get("info").toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
