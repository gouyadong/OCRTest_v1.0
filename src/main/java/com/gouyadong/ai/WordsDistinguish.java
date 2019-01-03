package com.gouyadong.ai;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gouyadong.commoms.Constants;
import com.gouyadong.utils.Base64Util;
import com.gouyadong.utils.FileUtil;
import com.gouyadong.utils.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.jsp.tagext.TryCatchFinally;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;


/**
 * 文字识别
 *
 * @author gyd
 * @create 2018-12-29 10:16
 */

@Controller
@RequestMapping(value = "/wordsDistinguish")
public class WordsDistinguish {

    /**
     * 上传文件
     * @param file
     * @return
     */
    @RequestMapping("/uploadFile")
    @ResponseBody
    public Object uploadFile(MultipartFile file) {
        try {
            byte[] b = file.getBytes();
            String base64 = Base64Util.encode(b);
            String param = URLEncoder.encode("image", Constants.UTF8) + "=" + URLEncoder.encode(base64, Constants.UTF8);
            String result = HttpUtil.post(Constants.BAIDU_AI_ACCURATE_BASIC, Constants.BAIDU_AI_TOKEN, param);
            System.out.println("识别结果：===>"+result);
            StringBuffer stringBuffer = new StringBuffer();
            JSONArray words_results = JSONObject.parseObject(result).getJSONArray("words_result");
            for (int i=0;i<words_results.size();i++){
                String words = JSONObject.parseObject(words_results.get(i).toString()).getString("words");
                System.out.println("识别出的车牌号为："+words);
                stringBuffer.append(words+"\r");
            }
            stringBuffer.append("=============================\r");
            String path = this.getClass().getClassLoader().getResource("/").getPath();
            File res = new File(path,"result.txt");
            try {
                FileOutputStream fos = new FileOutputStream(res,true);
                if (!res.exists()){
                    res.createNewFile();
                }
                byte[] bytes = stringBuffer.toString().getBytes();
                fos.write(bytes);
                fos.flush();
                fos.close();
                System.out.println("Done!");
            }catch (IOException e){
                e.printStackTrace();
            }
            JSONObject object = new JSONObject();
            object.put("code",0);
            object.put("words",stringBuffer.toString());
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return  "success";
    }

}
