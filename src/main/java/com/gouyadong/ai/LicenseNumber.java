package com.gouyadong.ai;

import com.alibaba.fastjson.JSONObject;
import com.gouyadong.commoms.Constants;
import com.gouyadong.utils.Base64Util;
import com.gouyadong.utils.HttpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;

/**
 * 识别车牌号
 *
 * @author gyd
 * @create 2018-12-29 10:16
 */

@Controller
@RequestMapping(value = "/licenseNumber")
public class LicenseNumber {

    /**
     * 上传车牌号图片
     *
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
            String result = HttpUtil.post(Constants.BAIDU_AI_LICENSE_PLATE, Constants.BAIDU_AI_TOKEN, param);
            String number = JSONObject.parseObject(result).getJSONObject("words_result").getString("number");
            System.out.println("识别出的车牌号为：" + number);
            JSONObject object = new JSONObject();
            object.put("code", 0);
            object.put("number", number);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
