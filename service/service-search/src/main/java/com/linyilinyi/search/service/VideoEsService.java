package com.linyilinyi.search.service;


import com.linyilinyi.model.vo.article.ArticleEsQueryVo;
import com.linyilinyi.model.vo.video.VideoEsQueryVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author linyi
 * @Date 2024/10/24
 * @ClassName: VideoEsService
 */
public interface VideoEsService {

    String addVideoDoc() throws IOException;

    String addArticleDoc();

    List<Map<String, Object>> searchAll(String keyword);

    List<Map<String, Object>> searchVideo(VideoEsQueryVo videoQueryVo);

    List<Map<String, Object>> searchArticle(ArticleEsQueryVo articleQueryVo);


}
