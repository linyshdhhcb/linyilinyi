import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.entity.user.RoleMenu;
import com.linyilinyi.user.UserApplication;
import com.linyilinyi.user.mapper.RoleMenuMapper;
import com.linyilinyi.user.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 测试类
 * @Author linyi
 * @Date 2024/9/24
 * @ClassName: a
 */
@SpringBootTest(classes = UserApplication.class)
public class a {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Test
    public void test() {
        List<Menu> menuList = menuService.getMenuList();
        List<Long> collect = menuList.stream()
                .filter(menu -> menu.getMenuType() == 12003) // 使用 equals 方法进行比较
                .map(Menu::getId)
                .collect(Collectors.toList());
        for (int i = 0; i < collect.size(); i++) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(1846549674836168706L);
            roleMenu.setMenuId(collect.get(i));
            roleMenu.setCreateTime(LocalDateTime.now());
            roleMenuMapper.insert(roleMenu);
        }
    }

    @Test
    public void test1() {
        Boolean b = redisTemplate.hasKey("satoken:login:token:4034bef8-e601-4997-bfe7-9ea015d3dfea");
        System.out.println(b);
    }
}
