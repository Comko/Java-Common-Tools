package comko.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author zhangpenghao
 * @ClassName : JsonFormatUtils
 * @description: json
 * @date 2020-11-30 17:17
 */
public class JsonFormatUtils {

    public static String JsonFormat(String jsonString){
        JSONObject object = JSONObject.parseObject(jsonString);
        String pretty = JSON.toJSONString(object, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat);
        return pretty;
    }
}
