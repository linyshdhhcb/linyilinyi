import com.linyilinyi.search.SearchApplication;
import jakarta.annotation.Resource;
import lombok.ToString;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/23
 * @ClassName: test
 */
@ToString
@SpringBootTest(classes = SearchApplication.class)
public class test {

    @Resource
    private RestHighLevelClient restHighLevelClient;




//    POST /{索引库名}/_doc/{id}
//    {
//        对象json
//    }
    @Test
    public void addtest() throws IOException {
        // 创建一个IndexRequest对象，指定索引名称为"indexrequest"
        IndexRequest indexRequest = new IndexRequest("indexrequest");

        // 生成一个随机的UUID作为文档ID，并去除UUID中的"-"
        indexRequest.id(UUID.randomUUID().toString().replaceAll("-",""));

        // 定义一个JSON字符串，包含要索引的文档内容
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";

        // 将JSON字符串作为文档源数据，并指定内容类型为JSON
        indexRequest.source(jsonString, XContentType.JSON);

        // 执行索引操作，将文档添加到Elasticsearch中
        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        // 获取索引操作的结果
        DocWriteResponse.Result result = index.getResult();

        // 检查结果是否为创建了一个新的文档
        if(DocWriteResponse.Result.CREATED.equals(index.getResult())){
            System.out.println("创建索引 插入文档完毕！！");
        }
    }

    //GET /{索引库名}/_doc/{id}
    @Test
    public void getByIdtest() throws IOException {
        // 创建GetRequest对象，指定索引库名为"indexrequest"，文档ID为"4eef991b200d45cba4eaf29582ec3390"
        GetRequest indexrequest = new GetRequest("indexrequest").id("081558642fd047eab9f472941e1b1c14");

        // 使用restHighLevelClient执行GetRequest，获取文档信息
        GetResponse documentFields = restHighLevelClient.get(indexrequest, RequestOptions.DEFAULT);

        System.out.println(documentFields);

        // 将获取到的文档信息转换为字符串形式 "_source"部分
        String sourceAsString = documentFields.getSourceAsString();

        // 打印文档信息字符串
        System.out.println(sourceAsString);
    }

    //DELETE /{index}/_doc/{id}

    @Test
    public void deleteByIdtest() throws IOException {
        DeleteRequest indexrequest = new DeleteRequest("indexrequest", "081558642fd047eab9f472941e1b1c14");

        DeleteResponse delete = restHighLevelClient.delete(indexrequest, RequestOptions.DEFAULT);
       if (delete.getResult().equals(DocWriteResponse.Result.DELETED)){
            System.out.println("删除成功");
        }
        System.out.println(delete);
    }

//    POST /{索引库名}/_update/{id}
//    {
//        "doc": {
//        "字段名": "字段值",
//                "字段名": "字段值"
//    }
//    }
    @Test
    public void updateByIdtest() throws IOException {
        UpdateRequest indexrequest = new UpdateRequest("indexrequest", "c2707f3f58e746a3998f2c7664a16ac7");

        UpdateRequest doc = indexrequest.doc(
                "user", "linyi",
                "postDate", "2023-01-30",
                "message", "1212121212"
        );
        System.out.println(doc);

        UpdateResponse update = restHighLevelClient.update(doc, RequestOptions.DEFAULT);
        if (update.getResult().equals(DocWriteResponse.Result.UPDATED)){
            System.out.println("更新成功");
            System.out.println(update);
        }
    }
    //批量添加
    @Test
    public void batchAdd() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("indexrequest").id("1").source("user","linyi","postDate","2023-01-30","message","1212121212"));
        bulkRequest.add(new IndexRequest("indexrequest").id("2").source("user","linyi","postDate","2023-01-30","message","1212121212"));
        bulkRequest.add(new IndexRequest("indexrequest").id("3").source("user","linyi","postDate","2023-01-30","message","1212121212"));
        restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulkRequest.requests().size()==3){
            System.out.println("批量添加成功");
        }
    }


}
