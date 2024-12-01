import com.linyilinyi.article.client.ArticleClient;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.article.Article;
import com.linyilinyi.model.entity.log.OperLog;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.entity.video.Video;
import com.linyilinyi.model.vo.article.ArticleQueryVo;
import com.linyilinyi.model.vo.log.OperLogQueryVo;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import com.linyilinyi.system.client.SystemClient;
import com.linyilinyi.user.UserApplication;
import com.linyilinyi.user.service.MenuService;
import com.linyilinyi.video.client.VideoClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/30
 * @ClassName: b
 */
@SpringBootTest(classes = UserApplication.class)
public class b {

    @Autowired
    private ArticleClient articleClient;

    @Autowired
    private VideoClient videoClient;

    @Autowired
    private SystemClient systemClient;

    @Autowired
    private MenuService menuService;

    @Test
    public void test() {
        long l = System.currentTimeMillis();
        //同步
        List<Menu> menuList = menuService.getMenuList();
        Result<PageResult<Article>> articleList = articleClient.getArticleList(1, 1000, new ArticleQueryVo());
        Result<PageResult<OperLog>> pageResultResult = systemClient.pageList(1, 200, new OperLogQueryVo());
        Result<PageResult> list = videoClient.list(1, 500, new VideoQueryVo());
        System.out.println(articleList);
        System.out.println(pageResultResult);
        System.out.println(list);
        System.out.println("--------------------耗时："+(System.currentTimeMillis()-l));
    }

    @Test
    public void test1() {
        //异步
        long l = System.currentTimeMillis();

        CompletableFuture<List<Menu>> futureMenuList = CompletableFuture.supplyAsync(() -> menuService.getMenuList());
        CompletableFuture<Result<PageResult<Article>>> futureArticleList = CompletableFuture.supplyAsync(() ->
                articleClient.getArticleList(1, 1000, new ArticleQueryVo())
        );
        CompletableFuture<Result<PageResult<OperLog>>> futureOperLogList = CompletableFuture.supplyAsync(() ->
                systemClient.pageList(1, 200, new OperLogQueryVo())
        );
        CompletableFuture<Result<PageResult>> futureVideoList = CompletableFuture.supplyAsync(() ->
                videoClient.list(1, 500, new VideoQueryVo())
        );

        // 使用CompletableFuture.allOf等待所有异步操作完成
        CompletableFuture.allOf(futureMenuList, futureArticleList, futureOperLogList, futureVideoList)
                .thenRun(() -> {
                    try {
                        List<Menu> menuList = futureMenuList.get();
                        System.out.println(futureArticleList.get());
                        System.out.println(futureOperLogList.get());
                        System.out.println(futureVideoList.get());
                        System.out.println("--------------------耗时：" + (System.currentTimeMillis() - l));
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                })
                // 阻塞直到所有异步操作完成和thenRun中的代码执行完毕
                .join();
    }
}
