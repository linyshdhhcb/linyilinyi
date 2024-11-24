package com.linyilinyi.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyilinyi.model.entity.article.Read;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 阅读记录表 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface ReadMapper extends BaseMapper<Read> {

    @Select("SELECT * FROM read_history WHERE user_id = #{userId} and is_delete = 0")
    List<Read> selectReadList(@Param("userId") Integer userId);
}
