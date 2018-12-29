package com.gouyadong.token;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 获取token类
 *
 * @author gyd
 * @create 2018-12-28 17:46
 */
public class AuthService {

    /**
     * 获取权限token
     *
     * @return assess_token：24.6644da40f952e6f2a81d8099c3ebf7dc.2592000.1548585047.282335-15297118
     */
    public static String getAuth() {
        //百度云官网获取的 API Key
        String ak = "1EwWtkQeQCyFrFyzgWnYkGUU";
        //百度云官网获取的 Securet Key
        String sk = "TdlrVWfvjAbHrZ3WcCQd5f58Ql0awvUc";
        return getAuth(ak, sk);
    }

    /**
     * 24.6644da40f952e6f2a81d8099c3ebf7dc.2592000.1548585047.282335-15297118
     * 获取API访问token
     *
     * @param ak 百度云官网获取的 API Key
     * @param sk 百度云官网获取的 Securet Key
     * @return assess_token：24.6644da40f952e6f2a81d8099c3ebf7dc.2592000.1548585047.282335-15297118
     */
    public static String getAuth(String ak, String sk) {
        //获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                + "grant_type=client_credentials" //grant_type为固定参数
                + "&client_id=" + ak //百度云官网获取的 API Key
                + "&client_secret=" + sk; //百度云官网获取的 Securet Key

        try {
            URL realUrl = new URL(getAccessTokenUrl);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            //获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();

            for (String key : map.keySet()) {
                System.out.println(key + "====>" + map.get(key));
            }
            //定义BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("result=====>" + result);

            JSONObject jsonObject = new JSONObject(result);
            String access_token = jsonObject.getString("access_token");
            return access_token;
        } catch (Exception e) {
            System.err.printf("获取token失败！");
            e.printStackTrace(System.err);
        }
        return null;
    }


    public static void main(String[] args) {
        String auth = getAuth();
        System.out.println("auth==>" + auth);
        try {
            InputStream inputStream = new FileInputStream("");




        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
