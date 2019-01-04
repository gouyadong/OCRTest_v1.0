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
 * 银行卡识别
 *
 * @author gyd
 * @create 2019-01-04 12:28
 */
@Controller
@RequestMapping(value = "bankcard")
public class Bankcard {

    /**
     * 上传银行卡照片
     *
     * @param file
     * @return
     */
    @RequestMapping("/bankcard")
    @ResponseBody
    public Object uploadFile(MultipartFile file) {

        try {
            byte[] bytes = file.getBytes();
            String encode = Base64Util.encode(bytes);
            String param = URLEncoder.encode("image", Constants.UTF8) + "=" + URLEncoder.encode(encode, Constants.UTF8);
            String result = HttpUtil.post(Constants.BAIDU_AI_BANKCARD, Constants.BAIDU_AI_TOKEN, param);
            System.out.println("result:==>" + result);
            JSONObject res = JSONObject.parseObject(result).getJSONObject("result");
            //银行卡类型，0:不能识别; 1: 借记卡; 2: 信用卡
            String bank_card_number = res.getString("bank_card_number");
            String bank_card_type = res.getString("bank_card_type");
            if ("0".equals(bank_card_type)) {
                bank_card_type = "不能识别";
            } else if ("1".equals(bank_card_type)) {
                bank_card_type = "借记卡";
            } else {
                bank_card_type = "信用卡";
            }
            String bank_name = res.getString("bank_name");
            JSONObject object = new JSONObject();
            object.put("code", 0);
            object.put("bank_card_number", bank_card_number);
            object.put("bank_card_type", bank_card_type);
            object.put("bank_name", bank_name);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
