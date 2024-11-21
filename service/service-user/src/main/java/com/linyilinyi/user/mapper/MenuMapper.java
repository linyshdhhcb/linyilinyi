package com.linyilinyi.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyilinyi.model.entity.user.Menu;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface MenuMapper extends BaseMapper<Menu> {

    void deleteNeMenuType(Integer i);
}
