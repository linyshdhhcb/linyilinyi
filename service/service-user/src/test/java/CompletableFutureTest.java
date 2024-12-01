import com.linyilinyi.article.client.ArticleClient;
import com.linyilinyi.user.UserApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.lang.invoke.StringConcatException;
import java.util.concurrent.*;

/**
 * @Description CompletableFuture测试类
 * @Author linyi
 * @Date 2024/11/30
 * @ClassName: CompletableFutureTest
 */
public class CompletableFutureTest {

    @Test
    public void test1() throws Exception{
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            return "1";
        });
        String s = stringCompletableFuture.get();
        String join = stringCompletableFuture.join();
        System.out.println(join);
        System.out.println(s);
    }

    @Test
    public void test2(){
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1);
        System.out.println(threadLocal.get());
        ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
        System.out.println("线程组："+Thread.currentThread().getThreadGroup());
        System.out.println("线程"+Thread.currentThread().getName());
        //不推荐
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10,200,1000,TimeUnit.DAYS,new ArrayBlockingQueue<>(5));

        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> {
            System.out.println("线程组："+Thread.currentThread().getThreadGroup());
            System.out.println("线程"+Thread.currentThread().getName());
            System.out.println(threadLocal.get());
            return 1;
        },executorService);
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> {
                System.out.println(threadLocal.get());
                System.out.println("线程组："+Thread.currentThread().getThreadGroup());
                System.out.println("线程"+Thread.currentThread().getName());
                return 2;
        });
        CompletableFuture<Integer> c = a.thenCombine(b, (x, y) -> {
            System.out.println(threadLocal.get());
            System.out.println("线程组："+Thread.currentThread().getThreadGroup());
            System.out.println("线程"+Thread.currentThread().getName());
            System.out.println(x);
            System.out.println(y);
            return x + y;
        });
        Integer join = c.join();
        System.out.println(join);
        System.out.println(threadLocal.get());


    }

    @Test
    public void test3() {

    }

    @Test
    public void test4() {

    }
}
