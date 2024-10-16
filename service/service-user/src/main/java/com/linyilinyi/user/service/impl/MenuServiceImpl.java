package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.model.entity.user.Menu;
import com.linyilinyi.user.mapper.MenuMapper;
import com.linyilinyi.user.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

}
