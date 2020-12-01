package comko.utils;

import com.alibaba.fastjson.JSONArray;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zhangpenghao
 * @ClassName : GoogleTranslateUtils
 * @description: googletranslate
 * @date 2020-12-01 09:42
 */
public class GoogleTranslateUtils {

    private static final String PATH = "https://translate.googleapis.com/translate_a/single";

    private static final String CLIENT = "gtx";

    private static final String USER_AGENT = "Mozilla/5.0";

    private static final Map<String, String> LANGUAGE_MAP = new HashMap();

    static {
        LANGUAGE_MAP.put("auto", "Automatic");
        LANGUAGE_MAP.put("zh_cn", "Chinese Simplified");
        LANGUAGE_MAP.put("en", "English");
    }

    public static boolean isSupport(String language) {
        if (null == LANGUAGE_MAP.get(language)) {
            return false;
        }
        return true;
    }

    public static String translate(String text, String sourceLang, String targetLang) throws Exception {

        String retStr = "";
        if (!(isSupport(sourceLang) || isSupport(targetLang))) {
            throw new Exception("Not support");
        }

        List<NameValuePair> nvps = new ArrayList();
        nvps.add(new BasicNameValuePair("client", CLIENT));
        nvps.add(new BasicNameValuePair("sl", sourceLang));
        nvps.add(new BasicNameValuePair("tl", targetLang));
        nvps.add(new BasicNameValuePair("dt", "t"));
        nvps.add(new BasicNameValuePair("q", text));
        String resp = postHttp(PATH, nvps);
        if (null == resp) {
            throw new Exception("Network Error");
        }

        JSONArray jsonObject = JSONArray.parseArray(resp);
        for (Iterator<Object> it = jsonObject.getJSONArray(0).iterator(); it.hasNext(); ) {
            JSONArray a = (JSONArray) it.next();
            retStr += a.getString(0);
        }

        return retStr;
    }


    /**
     * post 请求
     *
     * @param url  请求地址
     * @param nvps 参数列表
     * @return
     * @throws
     */
    private static String postHttp(String url, List<NameValuePair> nvps) {
        String responseStr = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse response2 = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, Charset.forName("UTF-8")));
            response2 = httpclient.execute(httpPost);
            HttpEntity entity2 = response2.getEntity();
            responseStr = EntityUtils.toString(entity2);
            EntityUtils.consume(entity2);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != response2) {
                try {
                    response2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpclient) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseStr;
    }
}
