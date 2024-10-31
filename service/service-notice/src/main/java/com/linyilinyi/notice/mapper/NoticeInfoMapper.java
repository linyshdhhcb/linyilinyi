package com.linyilinyi.notice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.linyilinyi.model.entity.notice.NoticeInfo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 存储各种信息的通用表，包括私信、通知、公告和客服消息 Mapper 接口
 * </p>
 *
 * @author linyi
 */
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo> {

}
