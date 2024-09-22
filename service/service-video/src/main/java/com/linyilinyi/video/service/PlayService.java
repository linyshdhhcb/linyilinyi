package com.linyilinyi.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.entity.video.Play;
import com.linyilinyi.model.vo.video.PlayAddVo;
import com.linyilinyi.model.vo.video.PlayQueryVo;

import java.util.List;

/**
 * <p>
 * 历史播放表 服务类
 * </p>
 *
 * @author linyi
 * @since 2024-09-22
 */
public interface PlayService extends IService<Play> {

    PageResult<Play> getPlayList(long pageNo, long pageSize, PlayQueryVo playQueryVo);

    List<Play> getPlayListByUser();

    String addPlay(PlayAddVo playAddVo);

    String deletePlay(List<Integer> ids);
}
