package com.linyilinyi.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.other.Follow;
import com.linyilinyi.model.vo.user.FanVo;
import com.linyilinyi.model.vo.user.FollowVo;
import com.linyilinyi.user.mapper.FollowMapper;
import com.linyilinyi.user.mapper.UserMapper;
import com.linyilinyi.user.service.FollowService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 粉丝表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

    @Resource
    private FollowMapper followMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public List<FanVo> getFansList() {
        List<Follow> follows = followMapper.selectList(new LambdaQueryWrapper<Follow>().eq(Follow::getIdolId, Integer.parseInt(request.getHeader("userid"))));
        List<FanVo> fanVos = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();
        for (Follow follow : follows) {
            ids.add(follow.getFansId());
        }
        userMapper.selectBatchIds(ids).stream().forEach(user -> fanVos.add(new FanVo(user.getId(), user.getNickname(), user.getImage())));
        return fanVos;
    }

    @Override
    public List<FollowVo> getFollowList() {
        List<Follow> follows = followMapper.selectList(new LambdaQueryWrapper<Follow>().eq(Follow::getFansId, Integer.parseInt(request.getHeader("userid"))));
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<FollowVo> followVos = new ArrayList<>();
        for (Follow follow : follows) {
            ids.add(follow.getIdolId());
        }
        userMapper.selectBatchIds(ids).stream().forEach(user -> followVos.add(new FollowVo(user.getId(), user.getNickname(), user.getImage(), user.getIntro())));
        return followVos;
    }

    @Override
    public String addFollow(Integer id) {
        if (isFollow(id)) {
            LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<Follow>().eq(Follow::getIdolId, Integer.parseInt(request.getHeader("userid"))).eq(Follow::getFansId, id);
            int delete = followMapper.delete(queryWrapper);
            if (delete!=1){
                throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
            }
            return "取消关注成功";
        }
        Follow follow = new Follow();
        follow.setCreateTime(LocalDateTime.now());
        follow.setFansId(Integer.parseInt(request.getHeader("userid")));
        follow.setIdolId(id);
        int insert = followMapper.insert(follow);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "关注成功";
    }

    @Override
    public Boolean isFollow(Integer id) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<Follow>().eq(Follow::getIdolId,Integer.parseInt(request.getHeader("userid"))).eq(Follow::getFansId, id);
        Follow follow = followMapper.selectOne(queryWrapper);
        return Optional.ofNullable(follow).isPresent() ? true : false;
    }
}
