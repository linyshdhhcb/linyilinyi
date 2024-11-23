package com.linyilinyi.search.service.impl;

import com.alibaba.fastjson2.JSON;
import com.linyilinyi.article.client.ArticleClient;
import com.linyilinyi.common.constant.EsConstant;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.article.ArticleEsQueryVo;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.model.vo.user.UserQueryVo;
import com.linyilinyi.model.vo.video.VideoEsQueryVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.search.service.SearchService;
import com.linyilinyi.user.client.UserClient;
import com.linyilinyi.video.client.VideoClient;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @Description
 * @Author linyi
 * @Date 2024/10/24
 * @ClassName: VideoEsServiceImpl
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class VideoEsServiceImpl implements SearchService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Resource
    private VideoClient videoClient;

    @Resource
    private ArticleClient articleClient;

    @Resource
    private UserClient userClient;

    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public String addVideoDoc() throws IOException {
        int pageNo = 1, pageSize = 100;
        BulkRequest bulkRequest = new BulkRequest();
        PageResult data = videoClient.list(pageNo, pageSize, new VideoQueryVo()).getData();
        List<Map<String, Object>> items = data.getItems();
        items.stream().forEach(item -> {
            Video video = JSON.parseObject(JSON.toJSONString(item), Video.class);
            bulkRequest.add(new IndexRequest(EsConstant.VIDEO_INDEX).id(video.getId().toString()).source(JSON.toJSONString(video), XContentType.JSON));
        });
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        if (bulk.hasFailures()) {
            System.out.println("批量添加失败");
        }
        return "成功";
    }

    @Override
    public String addArticleDoc() {
        int pageNo = 1;
        int pageSize = 100;

        BulkRequest bulkRequest = new BulkRequest();
        PageResult<Article> data = articleClient.getArticleList(pageNo, pageSize, new ArticleQueryVo()).getData();
        List<Article> items = data.getItems();
        items.stream().forEach(item -> {
            Article article = JSON.parseObject(JSON.toJSONString(item), Article.class);
            bulkRequest.add(new IndexRequest(EsConstant.ARTICLE_INDEX).id(article.getId().toString()).source(JSON.toJSONString(article), XContentType.JSON));
        });

        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulk.hasFailures()) {
                throw new LinyiException("添加文章索引失败");
            }
        } catch (IOException e) {
            throw new LinyiException("添加文章索引失败");
        }

        return "成功";
    }

    @Override
    public String addUser() {
        int pageNo = 1;
        int pageSize = 100;
        PageResult<User> data = userClient.getUserList(pageNo, pageSize, new UserQueryVo()).getData();
        List<User> items = data.getItems();
        BulkRequest bulkRequest = new BulkRequest();
        for (User user : items) {
            UserQueryVo userQueryVo = JSON.parseObject(JSON.toJSONString(user), UserQueryVo.class);
            bulkRequest.add(new IndexRequest(EsConstant.USER_INDEX).id(user.getId().toString()).source(JSON.toJSONString(userQueryVo), XContentType.JSON));
        }
        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulk.hasFailures()) {
                throw new LinyiException("添加文章索引失败");
            }
        } catch (IOException e) {
            throw new LinyiException("添加用户索引失败");
        }
        return "成功";
    }

    @Override
    public List<Map<String, Object>> searchAll(String keyword) {
        SearchRequest searchRequest = new SearchRequest(keyword);

        //DSL参数
        searchRequest.source().query(QueryBuilders.matchAllQuery());

        ArrayList<Map<String, Object>> videoEsVos = new ArrayList<>();
        SearchResponse searchs = null;
        //发送查询请求
        try {
            searchs = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = searchs.getHits().getHits();
            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                videoEsVos.add(sourceAsMap);
            }

        } catch (IOException e) {
            throw new LinyiException("DSL查询失败");
        }
        return videoEsVos;
    }

    /**
     * 根据关键词和查询条件进行模糊搜索
     *
     * @param videoQueryVo 查询条件封装对象，用于动态构建查询
     * @return 返回一个包含搜索结果的列表，每个搜索结果是一个键值对映射
     */
    @Override
    public List<Map<String, Object>> searchVideo(VideoEsQueryVo videoQueryVo) {

        // 创建BoolQueryBuilder，用于构建复杂的查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        BoolQueryBuilder must = null;
        // 使用反射动态构建查询条件
        for (Field field : VideoQueryVo.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object o = field.get(videoQueryVo);
                // 检查字段值是否非空，且不为字符串类型，避免空字符串查询
                if (Optional.ofNullable(o).isPresent()) {
                    // 添加匹配查询条件到bool查询中
                    must = boolQueryBuilder.must(QueryBuilders.matchQuery(name, o));
                    log.info("添加查询条件：value:{},name:{}", name, o);
                    continue;
                }
            } catch (IllegalAccessException e) {
                // 反射异常处理
                throw new LinyiException("反射异常");
            }
        }

        // 创建SearchSourceBuilder并设置查询
        SearchSourceBuilder query = new SearchSourceBuilder().query(must);

        // 创建SearchRequest，keyword作为索引名称
        SearchRequest searchRequest = new SearchRequest(EsConstant.VIDEO_INDEX);

        // 初始化用于存储搜索结果的列表
        List<Map<String, Object>> maps = new ArrayList<>();
        try {
            SearchRequest source = searchRequest.source(query);
            // 执行搜索并处理响应
            SearchResponse search = restHighLevelClient.search(source, RequestOptions.DEFAULT);
            SearchHit[] hits = search.getHits().getHits();
            for (SearchHit hit : hits) {
                // 将每个搜索结果作为映射添加到列表中
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                maps.add(sourceAsMap);
            }
        } catch (Exception e) {
            // DSL查询失败异常处理
            List<String> collect = Arrays.stream(e.getSuppressed()).map(Throwable::getMessage).collect(Collectors.toList());
            throw new LinyiException(collect.get(0));
        }
        // 返回搜索结果列表
        return maps;
    }

    @Override
    public List<Map<String, Object>> searchArticle(ArticleEsQueryVo articleQueryVo) {

        //创建BoolQueryBuilder，用于构建复杂的查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        BoolQueryBuilder must = null;
        for (Field field : ArticleEsQueryVo.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object o = field.get(articleQueryVo);
                if (Optional.ofNullable(o).isPresent()) {
                    must = boolQueryBuilder.must(QueryBuilders.matchQuery(name, o));
                    log.info("添加查询条件：value:{},name:{}", name, o);
                    continue;
                }
            } catch (IllegalAccessException e) {
                throw new LinyiException("反射异常");
            }
        }

        SearchRequest searchRequest = new SearchRequest(EsConstant.ARTICLE_INDEX);
        SearchSourceBuilder query = new SearchSourceBuilder().query(must);

        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        try {
            SearchRequest source = searchRequest.source(query);
            //执行搜索并处理响应
            SearchResponse search = restHighLevelClient.search(source, RequestOptions.DEFAULT);

            SearchHit[] hits = search.getHits().getHits();
            for (SearchHit hit : hits) {
                maps.add(hit.getSourceAsMap());
            }
        } catch (IOException e) {
            throw new LinyiException(Arrays.stream(e.getSuppressed()).map(Throwable::getMessage).collect(Collectors.toList()).get(0));
        }
        return maps;
    }

    @Override
    public List<Map<String, Object>> searchUser(UserQueryVo userQueryVo) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        BoolQueryBuilder must = null;
        for (Field field : UserQueryVo.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String name = field.getName();
                Object o = field.get(userQueryVo);
                if (Optional.ofNullable(o).isPresent()) {
                    must = boolQueryBuilder.must(QueryBuilders.matchQuery(name, o));
                    log.info("添加查询条件：value:{},name:{}", name, o);
                    continue;
                }
            } catch (IllegalAccessException e) {
                throw new LinyiException("反射异常");
            }
        }
        SearchRequest searchRequest = new SearchRequest(EsConstant.USER_INDEX);
        SearchSourceBuilder query = new SearchSourceBuilder().query(must);
        List<Map<String, Object>> maps = new ArrayList<>();
        SearchRequest source = searchRequest.source(query);
        try {
            SearchResponse search = restHighLevelClient.search(source, RequestOptions.DEFAULT);
            for (SearchHit hit : search.getHits().getHits()) {
                System.out.println(hit.getSourceAsMap());
                maps.add(hit.getSourceAsMap());
            }
            // 按 score 降序排序
            Collections.sort(maps, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    float score1 = (float) o1.get("_score");
                    float score2 = (float) o2.get("_score");
                    return Float.compare(score2, score1); // 降序排序
                }
            });
        } catch (IOException e) {
            throw new LinyiException(Arrays.stream(e.getSuppressed()).map(Throwable::getMessage).collect(Collectors.toList()).get(0));
        }
        return maps;
    }

    @Override
    public Video getLatestVideo() {
        SearchRequest searchRequest = new SearchRequest(EsConstant.VIDEO_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //根据di降序
        return null;
    }

    @Override
    public Integer getByToken(HttpServletRequest request) {
        Object o = redisTemplate.opsForValue().get("satoken:login:token:" + request.getCookies()[1].getValue());
        return Integer.parseInt(String.valueOf(o));
    }
}
