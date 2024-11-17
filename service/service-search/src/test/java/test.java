
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.search.SearchApplication;
import lombok.ToString;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.expression.spel.ast.OpOr;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.*;

import static com.linyilinyi.common.constant.SystemConstant.DOMAIN;


/**
 * @Description
 * @Author linyi
 * @Date 2024/10/23
 * @ClassName: test
 */
@ToString
@SpringBootTest(classes = SearchApplication.class)
public class test {
    @Test
    public void test() {
        String urlString = DOMAIN+"8603/v3/api-docs/default";
        List<String> list = Arrays.asList("get", "put", "post", "delete");
        List<HashMap<String, String>> mapArrayList = new ArrayList<>();
        try {
            int i=0;
            // 发送 HTTP GET 请求
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应内容
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // 解析 JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                JsonNode pathsNode = rootNode.path("paths");
                // 遍历路径
                Iterator<String> fieldNames = pathsNode.fieldNames();

                while (fieldNames.hasNext()) {
                    String path = fieldNames.next();
                    JsonNode pathNode = pathsNode.get(path);
                    for (String method : list) {
                        if (pathNode.has(method)) {
                            JsonNode putNode = pathNode.get(method);
                            JsonNode tagsNode = putNode.get("tags");
                            JsonNode summaryNode = putNode.get("summary");
                            if (tagsNode != null && summaryNode != null) {
                                HashMap<String, String> map = new HashMap<>();
                                String tag = tagsNode.toString().substring(2, tagsNode.toString().length() - 2);
                                String summary = summaryNode.toString().substring(1, summaryNode.toString().length() - 1);
                                map.put("tag", tag);
                                map.put("summary", summary);
                                map.put("path", path);
                                map.put("method", method);
                                mapArrayList.add(map);
                            }
                        }
                    }
                }
            } else {
                System.out.println("检索数据失败。处理步骤HTTP错误码: " + responseCode);
            }
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println(JSON.toJSONString(mapArrayList));
        }

    }
}
