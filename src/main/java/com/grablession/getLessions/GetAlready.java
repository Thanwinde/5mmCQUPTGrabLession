package com.grablession.getLessions;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.grablession.entity.AlreadyClass;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author nsh
 * @data 2025/4/24 20:53
 * @description
 **/
public class GetAlready {

    CloseableHttpClient httpclient = HttpClients.createDefault();

    public ArrayList<AlreadyClass> getAlreadyLesions(String PHPSESSID) {
        ArrayList<AlreadyClass> alist = new ArrayList<>();
        HttpGet httpGet = new HttpGet("http://xk2.cqupt.edu.cn/json-data-yxk.php?type=yxk");
        httpGet.setHeader("Cookie", PHPSESSID);
        try {
            CloseableHttpResponse response = httpclient.execute(httpGet);
            JSONObject json = new JSONObject();
            HttpEntity entity = response.getEntity();
            String te = EntityUtils.toString(entity);
            json = JSONUtil.parseObj(te);
            JSONArray array = json.getJSONArray("data");
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = array.getJSONObject(i);
                AlreadyClass alreadyClass = JSONUtil.toBean(obj.toString(), AlreadyClass.class);
                alist.add(alreadyClass);
            }
            response.getEntity().getContent().close();
            return alist;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
