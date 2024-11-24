package com.linyilinyi.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.likes.Likes;
import com.linyilinyi.user.mapper.LikesMapper;
import com.linyilinyi.user.service.LikesService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.json.HTTP;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 点赞表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements LikesService {

    @Resource
    private LikesMapper likesMapper;

    @Resource
    private HttpServletRequest request;

    @Resource
    private RedisTemplate<String,Integer> redisTemplate;
    @Override
    public String addLikes(Integer id, Integer targetType) {
        if (isLikes(id,targetType)){
            LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<Likes>().eq(Likes::getTargetId,id).eq(Likes::getTargetType,targetType);
            int delete = likesMapper.delete(queryWrapper);
            if (delete!=1){
                throw new RuntimeException("删除失败");
            }
            redisTemplate.delete("isLikes:userId:"+request.getHeader("userid")+":"+id.toString()+":"+targetType.toString());
            return "取消点赞成功";
        }else {
            Likes likes = new Likes();
            likes.setCreateTime(LocalDateTime.now());
            likes.setTargetId(id);
            likes.setTargetType(targetType);
            likes.setUserId(Integer.parseInt(request.getHeader("userid")));
            int insert = likesMapper.insert(likes);
            if (insert!=1){
                throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
            }
            redisTemplate.opsForValue().set("isLikes:userId:"+Integer.parseInt(request.getHeader("userid"))+":"+id.toString()+":"+targetType.toString(),id);
            return "点赞成功";
        }
        }

    @Override
    public Boolean isLikes(Integer id, Integer targetType) {
        Integer i = redisTemplate.opsForValue().get("isLikes:userId:"+Integer.parseInt(request.getHeader("userid"))+":"+id.toString()+":"+targetType.toString());
        if (i==id){
            return true;
        }
        LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<Likes>()
                .eq(Likes::getTargetId,id).eq(Likes::getTargetType,targetType)
                .eq(Likes::getUserId,Integer.parseInt(request.getHeader("userid")));
        Likes likes = likesMapper.selectOne(queryWrapper);
        return likes != null ? true : false;
    }
}
