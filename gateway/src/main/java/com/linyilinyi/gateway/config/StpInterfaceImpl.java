package com.linyilinyi.gateway.config;

import cn.dev33.satoken.stp.StpInterface;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.user.client.UserClient;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
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


    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        Long longLoginId =Long.valueOf((String) loginId);
        CompletableFuture<List<String>> listCompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        return userClient.getUserRoleList(longLoginId).getData().stream().map(Role::getCode).collect(Collectors.toList());
                    } catch (Exception e) {
                        throw new LinyiException("获取用户角色列表失败"+ e.getMessage());
                    }
                });
        try {
            //判断是否为null
            if (listCompletableFuture.get() == null) {
                return Collections.emptyList();
            }
            return listCompletableFuture.get(1, TimeUnit.MINUTES);
        } catch (TimeoutException e) {
            throw new LinyiException("获取用户角色超时"+e.getMessage());
        }catch (Exception e){
            throw new LinyiException("获取用户角色失败"+e.getMessage());
        }
    }


}
