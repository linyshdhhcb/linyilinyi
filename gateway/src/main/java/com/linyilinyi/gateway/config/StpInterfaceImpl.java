package com.linyilinyi.gateway.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.user.client.UserClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description 自定义权限加载接口实现类
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: StpInterfaceImpl
 */
@Slf4j
@Component
public class StpInterfaceImpl implements StpInterface {


    @Resource
    private UserClient userClient;

    @Resource
    private RedisTemplate redisTemplate;

    //获取全部权限
    public List<String> UrlList() {
        List<Menu> data = userClient.getMenuList().getData();
        return data.stream().map(Menu::getPath).collect(Collectors.toList());
    }


    /**
     * 返回一个账号所拥有的权限码集合
     *
     * @param loginId   用户登录ID，用于识别用户
     * @param loginType 用户登录类型，可能影响权限获取逻辑（例如：员工登录、学生登录等）
     * @return 用户所拥有的权限码列表，如果用户没有权限或发生异常，则返回空列表
     * <p>
     * 本方法通过异步方式获取用户权限列表，以提高性能和响应速度
     * 首先获取用户的角色列表，然后根据每个角色获取对应的菜单权限，并筛选出权限码
     * 使用CompletableFuture来处理异步任务，确保在指定时间内完成权限码的收集
     * 如果在指定时间内未完成，抛出超时异常；如果出现其他异常，抛出通用异常
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 获取用户角色列表
        List<String> roleList = getRoleList(loginId, loginType);
        List<String> paths = new ArrayList<>();
        // 使用CompletableFuture异步处理权限码收集任务
        CompletableFuture<List<String>> arrayListCompletableFuture = CompletableFuture.supplyAsync(() -> {
            for (String role : roleList) {
                // 根据角色代码获取菜单列表，并筛选出权限码
                userClient.getMenuListByRoleCode(role).getData().stream().forEach(menu -> {
                    paths.add(menu.getPath());
                });
            }
            return paths;
        });

        try {
            // 尝试获取异步任务结果，如果超时则抛出异常
            if (arrayListCompletableFuture.get(1, TimeUnit.MINUTES) == null) {
                return Collections.emptyList();
            }
            return arrayListCompletableFuture.get(1, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            // 如果获取权限码超时，抛出自定义异常
            throw new LinyiException("获取用户权限超时" + e.getMessage());
        } catch (Exception e) {
            // 如果获取权限码过程中出现其他异常，抛出自定义异常
            throw new LinyiException("获取用户权限失败" + e.getMessage());
        }
    }

    /**
     * 根据登录ID和登录类型获取用户角色列表
     * 此方法使用CompletableFuture异步获取用户角色列表，以提高响应性能
     *
     * @param loginId   用户登录ID，被转换为Long类型以匹配用户角色查询接口
     * @param loginType 用户登录类型，未在本方法中使用，但可能在用户角色查询逻辑中有用途
     * @return 用户角色代码列表如果用户没有角色或角色列表获取失败，返回空列表
     * @throws LinyiException 当角色列表获取超时或其他异常时抛出自定义异常
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        // 将登录ID转换为Long类型，以便与用户角色查询接口兼容
        Integer longLoginId = Integer.parseInt(StpUtil.getLoginId().toString());
        // 使用CompletableFuture异步获取用户角色列表，提高响应性能
        CompletableFuture<List<String>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
            // 从用户角色查询接口获取角色列表，并提取角色代码
            Object o = redisTemplate.opsForValue().get("user:role:" + longLoginId);
            if (Optional.ofNullable(o).isPresent() && o instanceof List) {
                return (List<String>) o;
            }
            return userClient.getUserRoleList(longLoginId).getData().stream().map(Role::getCode).collect(Collectors.toList());
        });
        try {
            // 判断异步获取的角色列表是否为null，如果为null则返回空列表
            if (listCompletableFuture.get() == null) {
                return Collections.emptyList();
            }
            // 尝试获取异步执行结果，并返回角色列表
            return listCompletableFuture.get(1, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            // 如果超时，抛出自定义异常
            throw new LinyiException("获取用户角色超时" + e.getMessage());
        } catch (Exception e) {
            // 如果发生其他异常，抛出自定义异常
            throw new LinyiException("获取用户角色失败" + e.getMessage());
        }
    }


}
