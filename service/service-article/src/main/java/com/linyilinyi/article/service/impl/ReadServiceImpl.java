package com.linyilinyi.article.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.linyilinyi.article.mapper.ReadMapper;
import com.linyilinyi.article.service.ReadService;
import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.article.Read;
import com.linyilinyi.model.vo.article.ReadAddVo;
import com.linyilinyi.model.vo.article.ReadQueryVo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 阅读记录表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ReadServiceImpl extends ServiceImpl<ReadMapper, Read> implements ReadService {

    @Resource
    private ReadMapper readMapper;

    @Resource
    private HttpServletRequest request;

    @Override
    public PageResult<Read> getReadList(long pageNo, long pageSize, ReadQueryVo readQueryVo) {
        LambdaQueryWrapper<Read> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Optional.ofNullable(readQueryVo.getArticleId()).isPresent(), Read::getArticleId, readQueryVo.getArticleId());
        queryWrapper.eq(Optional.ofNullable(readQueryVo.getUserId()).isPresent(), Read::getUserId, readQueryVo.getUserId());
        queryWrapper.gt(Optional.ofNullable(readQueryVo.getStartTime()).isPresent(), Read::getCreateTime, readQueryVo.getStartTime());
        queryWrapper.lt(Optional.ofNullable(readQueryVo.getEndTime()).isPresent(), Read::getCreateTime, readQueryVo.getEndTime());
        queryWrapper.eq(Read::getIsDelete, 0); // 添加 is_delete 条件
        Page<Read> readPage = new Page<>(pageNo, pageSize);
        Page<Read> page = readMapper.selectPage(readPage, queryWrapper);
        return new PageResult<>(page.getRecords(), page.getTotal(), pageNo, pageSize);
    }

    @Override
    public List<Read> getReadListByUserId() {
        return readMapper.selectReadList(Integer.parseInt(request.getHeader("userid")));
    }

    @Override
    public String addRead(ReadAddVo readAddVo) {
        if (Optional.ofNullable(readAddVo).isEmpty()) {
            throw new LinyiException(ResultCodeEnum.DATA_NULL);
        }
        Read read = new Read();
        BeanUtils.copyProperties(readAddVo, read);
        read.setCreateTime(LocalDateTime.now());
        int insert = readMapper.insert(read);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "添加成功";
    }

    @Override
    public String deleteRead(List<Integer> ids) {
        for (Integer id : ids) {
            if (id <= 0) {
                throw new LinyiException(ResultCodeEnum.DATA_ERROR);
            }
        }
        int delete = readMapper.deleteBatchIds(ids);
        if (delete != ids.size()) {
            throw new LinyiException(ResultCodeEnum.DELETE_FAIL);
        }
        return "删除成功";
    }
}
