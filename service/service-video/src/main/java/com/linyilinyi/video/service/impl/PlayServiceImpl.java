package com.linyilinyi.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.video.Play;
import com.linyilinyi.model.vo.video.PlayAddVo;
import com.linyilinyi.model.vo.video.PlayQueryVo;
import com.linyilinyi.video.mapper.PlayMapper;
import com.linyilinyi.video.service.PlayService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 历史播放表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class PlayServiceImpl extends ServiceImpl<PlayMapper, Play> implements PlayService {

    @Resource
    private PlayMapper playMapper;

    @Override
    public PageResult<Play> getPlayList(long pageNo, long pageSize, PlayQueryVo playQueryVo) {
        LambdaQueryWrapper<Play> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Optional.ofNullable(playQueryVo.getVideoId()).isPresent(), Play::getVideoId,playQueryVo.getVideoId());
        queryWrapper.eq(Optional.ofNullable(playQueryVo.getUserId()).isPresent(), Play::getUserId,playQueryVo.getUserId());
        queryWrapper.gt(Optional.ofNullable(playQueryVo.getStartTime()).isPresent(), Play::getCreateTime,playQueryVo.getStartTime());
        queryWrapper.lt(Optional.ofNullable(playQueryVo.getEndTime()).isPresent(), Play::getCreateTime,playQueryVo.getEndTime());
        Page<Play> playPage = new Page<>(pageNo, pageSize);
        Page<Play> page = playMapper.selectPage(playPage, queryWrapper);
        return new PageResult<>(page.getRecords(),page.getTotal(),pageNo,pageSize);
    }

    @Override
    public List<Play> getPlayListByUser() {
        Integer userId = AuthContextUser.getUserId();
        if (Optional.ofNullable(userId).isEmpty()){
            throw new LinyiException(ResultCodeEnum.LOGIN_AUTH);
        }
        return playMapper.selectList(new LambdaQueryWrapper<Play>().eq(Play::getUserId,userId).orderByDesc(Play::getCreateTime));
    }

    @Override
    public String addPlay(PlayAddVo playAddVo) {
        if (Optional.ofNullable(playAddVo.getVideoId()).isEmpty()){
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
       Play play = playMapper.selectOne(new LambdaQueryWrapper<Play>().eq(Play::getVideoId, playAddVo.getVideoId()).eq(Play::getUserId, AuthContextUser.getUserId()));
        if (Optional.ofNullable(play).isEmpty()){
            Play playNew = new Play();
            playNew.setCreateTime(LocalDateTime.now());
            int insert = playMapper.insert(playNew);
            if (insert != 1){
                throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
            }
            return "添加成功";
        }else {
            play.setUpdateTime(LocalDateTime.now());
            int update = playMapper.updateById(play);
            if (update != 1){
                throw new LinyiException(ResultCodeEnum.UPDATE_FAIL);
            }
            return "更新成功";
        }
    }

    @Override
    public String deletePlay(List<Integer> ids) {
        if (Optional.ofNullable(ids).isEmpty()){
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        for (Integer id : ids){
            if (id<=0){
                throw new LinyiException(ResultCodeEnum.DATA_ERROR);
            }
        }
        int i = playMapper.deleteBatchIds(ids);
        if (i!=ids.size()){
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
        return "删除成功";
    }
}
