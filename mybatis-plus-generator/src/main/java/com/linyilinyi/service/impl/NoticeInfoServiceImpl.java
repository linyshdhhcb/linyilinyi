package com.linyilinyi.service.impl;

import com.linyilinyi.entity.po.NoticeInfo;
import com.linyilinyi.mapper.NoticeInfoMapper;
import com.linyilinyi.service.NoticeInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存储各种信息的通用表，包括私信、通知、公告和客服消息 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements NoticeInfoService {

}
