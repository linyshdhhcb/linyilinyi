package com.linyilinyi.gateway.config;


import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson2.JSON;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;

import java.security.AuthProvider;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 注册 Sa-Token全局过滤器
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: SaTokenConfigure
 */
@Slf4j
@Configuration
public class SaTokenConfigure {

    @Resource
    private StpInterfaceImpl stpInterface;

    @Resource
    private  RedisTemplate redisTemplate;


    private static final String USER_LOGIN_PATH = "/*/*/user/login";
    private static final String USER_REGISTER_PATH = "/*/*/user/register";
    private static final String USER_LOGOUT_PATH = "/*/*/user/logout";
    private static final String USER_ISLOGIN_PATH = "/*/*/user/isLogin";

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(

            //"/**",//全部不拦截
            USER_LOGIN_PATH,
            USER_REGISTER_PATH,
            USER_LOGOUT_PATH,
            USER_ISLOGIN_PATH,
            "/*/*/log/**",
            "/favicon.ico",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/v3/**",
            "/webjars/**",
            "/doc.html",
            "/*/*/v3/**",
            "/*/*/swagger-ui/**",
            "/*/*/swagger-resources/**",
            "/*/*/webjars/**",
            "/*/*/doc.html"
    );

    @Bean
    public SaReactorFilter getSaReactorFilter() {
        SaReactorFilter saReactorFilter = new SaReactorFilter()
                .addInclude("/**");

        for (String path : EXCLUDED_PATHS) {
            saReactorFilter.addExclude(path);
        }



        // 配置认证逻辑
        configureAuth(saReactorFilter);
        // 配置错误处理逻辑
       configureErrorHandling(saReactorFilter);


        saReactorFilter
                .setBeforeAuth(r -> {
                    SaHolder.getResponse()
                            .setHeader("Access-Control-Allow-Origin", "*")
                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT")
                            .setHeader("Access-Control-Max-Age", "3600")
                            .setHeader("Access-Control-Allow-Headers", "*");
                    SaRouter.match(SaHttpMethod.OPTIONS)
                            .free(s -> log.debug("OPTIONS预检请求，不做处理"))
                            .back();
                });
        return saReactorFilter;
    }


    /**
     * 配置认证逻辑
     * <p>
     * 此方法用于配置SaReactorFilter的认证逻辑，主要包括全局登录校验、登录接口路径匹配、角色和权限校验等。
     *
     * @param saReactorFilter SaReactorFilter实例，用于设置认证逻辑
     */
    private void configureAuth(SaReactorFilter saReactorFilter) {
        saReactorFilter.setAuth(obj -> {
            try {
                System.out.println("id:" + StpUtil.getLoginId());
                System.out.println("全部角色:" + StpUtil.getRoleList());
                System.out.println("全部权限:" + StpUtil.getPermissionList());
                // 全局登录校验，确保在其他具体路径匹配规则之前执行
                SaRouter.match("/**", () -> StpUtil.isLogin());
                //通过集合的url拦截 为每个 URL 模式设置权限检查
                String path = SaHolder.getRequest().getRequestPath();
                System.out.println("path:" + path);
                SaRouter.match(path, r -> StpUtil.checkRoleOr(String.join(",", StpUtil.getRoleList())));

            } catch (NotLoginException e) {
                log.error("用户未登录，拦截请求信息：{},用户登录信息：{}", e.getMessage(), StpUtil.getTokenInfo());
                throw e; // 直接抛出原始的 NotLoginException
            } catch (NotRoleException e) {
                log.error("用户未授权角色，拦截请求信息：{},用户登录信息：{}", e.getMessage(), StpUtil.getTokenInfo());
                throw e; // 直接抛出原始的 NotRoleException
            } catch (NotPermissionException e) {
                log.error("用户未授权权限，拦截请求信息：{},用户登录信息：{}", e.getMessage(), StpUtil.getTokenInfo());
                throw e; // 直接抛出原始的 NotPermissionException
            } catch (Exception e) {
                log.error("请求失败信息：{}，用户id：{}", e.getMessage(), StpUtil.getTokenInfo());
                log.error("鉴权异常，类型={}", e.getClass().getName());
                throw e; // 直接抛出原始的 Exception
            }
        });
    }

    private Result configureErrorHandling(SaReactorFilter saReactorFilter) {
        saReactorFilter.setError(e -> {
            log.error("发生错误: {}", e.getClass().getName(), e);

            String message = "未经授权";

            if (e instanceof NotLoginException nle) {
                System.out.println("***************************"+((NotLoginException) e).getType());
                switch (nle.getType()) {
                    case NotLoginException.NOT_TOKEN:
                        message = NotLoginException.DEFAULT_MESSAGE + "!!!" + NotLoginException.NOT_TOKEN_MESSAGE;
                        break;
                    case NotLoginException.INVALID_TOKEN:
                        message = NotLoginException.INVALID_TOKEN_MESSAGE;
                        break;
                    case NotLoginException.TOKEN_TIMEOUT:
                        message = NotLoginException.TOKEN_TIMEOUT_MESSAGE;
                        break;
                    case NotLoginException.BE_REPLACED:
                        message = NotLoginException.BE_REPLACED_MESSAGE;
                        break;
                    case NotLoginException.KICK_OUT:
                        message = NotLoginException.KICK_OUT_MESSAGE;
                        break;
                    default:
                        message = NotLoginException.DEFAULT_MESSAGE;
                        break;
                }
                //return JSON.toJSONString(Result.fail(ResultCodeEnum.LOGIN_AUTH));
            } else if (e instanceof NotPermissionException) {
                return JSON.toJSONString(Result.fail(ResultCodeEnum.NO_PERMISSION));
            } else if (e instanceof NotRoleException) {
                return JSON.toJSONString(Result.fail(ResultCodeEnum.NO_ROLE));
            }else if (e instanceof NoSuchMethodError){
                return JSON.toJSONString(Result.fail(ResultCodeEnum.LOGIN_AUTH));
            } else {
                return JSON.toJSONString(Result.fail("没有登录"));
            }
            // 记录更安全的日志信息
            log.error("发生错误: {}", message);
            return JSON.toJSONString(Result.fail(message));
        });
        return Result.ok();
    }

}
