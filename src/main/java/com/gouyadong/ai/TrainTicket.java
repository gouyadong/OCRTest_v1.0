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
 * 火车票识别
 *
 * @author gyd
 * @create 2019-01-03 15:54
 */

@Controller
@RequestMapping(value = "trainTicket")
public class TrainTicket {

    /**
     * 上传火车票图片
     *
     * @param file
     * @return
     */
    @RequestMapping("/train_ticket")
    @ResponseBody
    public Object uploadFile(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String encode = Base64Util.encode(bytes);
            String param = URLEncoder.encode("image", Constants.UTF8) + "=" + URLEncoder.encode(encode, Constants.UTF8);
            String result = HttpUtil.post(Constants.BAIDU_AI_TRAIN_TICKET, Constants.BAIDU_AI_TOKEN, param);
            System.out.println("result:==>" + result);
            JSONObject words_result = JSONObject.parseObject(result).getJSONObject("words_result");
            String name = words_result.getString("name");
            String destination_station = words_result.getString("destination_station");
            String seat_category = words_result.getString("seat_category");
            String ticket_rates = words_result.getString("ticket_rates");
            String ticket_num = words_result.getString("ticket_num");
            String date = words_result.getString("date");
            String train_num = words_result.getString("train_num");
            String starting_station = words_result.getString("starting_station");

            JSONObject object = new JSONObject();
            object.put("code", 0);
            object.put("name", name);
            object.put("destination_station", destination_station);
            object.put("seat_category", seat_category);
            object.put("ticket_rates", ticket_rates);
            object.put("ticket_num", ticket_num);
            object.put("date", date);
            object.put("train_num", train_num);
            object.put("starting_station", starting_station);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
