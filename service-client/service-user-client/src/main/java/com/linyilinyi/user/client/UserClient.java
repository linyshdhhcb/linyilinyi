package com.linyilinyi.user.client;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.model.entity.user.Role;
import com.linyilinyi.model.entity.user.User;
import com.linyilinyi.model.vo.user.LoginVo;
import com.linyilinyi.model.vo.user.MenuAdd;
import com.linyilinyi.model.vo.user.UserQueryVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/17
 * @ClassName: UserClient
 */
@FeignClient(value = "service-user")
public interface UserClient {

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/user/getUserById/{id}")
    public Result<User> getUserById(@PathVariable Integer id);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping("/user/getByUsername/{username}")
    public Result<User> getByUsername(@PathVariable String username);

    /**
     * 用户登录
     * @param loginVo
     * @return
     */
    @PostMapping("/user/login")
    public Result<String> login(@RequestBody LoginVo loginVo);

    /**
     * 获取用户角色列表
     * @param userId
     * @return
     */
    @GetMapping("/userRole/getUserRoleList")
    public Result<List<Role>> getUserRoleList(@RequestParam Integer userId);

    /**
     * 根据角色id获取菜单列表
     * @param roleId
     * @return
     */
    @GetMapping("/menu/getMenuListByRoleId/{roleId}")
    public Result<List<Menu>> getMenuListByRoleId(@PathVariable Long roleId);

    @GetMapping("/menu/getMenuListByRoleCode/{roleCode}")
    public Result<List<Menu>> getMenuListByRoleCode(@PathVariable String roleCode);

    @PostMapping("/user/getUserList")
    public Result<PageResult<User>> getUserList(@RequestParam(required = false,defaultValue = "1") long pageNo,
                                                @RequestParam(required = false,defaultValue = "5") long pageSize,
                                                @RequestBody UserQueryVo userQueryVo);

    @PostMapping("/menu/addMenuList")
    public Result addMenuList(@RequestBody List<Menu> menuList);

    @PostMapping("/menu/addMenu")
    public Result addMenu(@RequestBody MenuAdd menuAdd);

    @GetMapping("/menu/getMenuByName/{name}")
    public Result<Menu> getMenuByName(@PathVariable String name);

    @GetMapping("/menu/getMenuList")
    public Result<List<Menu>> getMenuList();

    @GetMapping("/user/getToken")
    public Result<String> tokenInfo();
}
