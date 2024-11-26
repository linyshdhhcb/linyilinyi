package com.linyilinyi.system.service.impl;

import com.linyilinyi.common.exception.LinyiException;
import com.linyilinyi.common.model.ResultCodeEnum;
import com.linyilinyi.common.utils.AuthContextUser;
import com.linyilinyi.model.entity.dictionary.DictionaryType;
import com.linyilinyi.system.mapper.DictionaryTypeMapper;
import com.linyilinyi.system.service.DictionaryTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 数据字典类型表 服务实现类
 * </p>
 *
 * @author linyi
 */
@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class DictionaryTypeServiceImpl extends ServiceImpl<DictionaryTypeMapper, DictionaryType> implements DictionaryTypeService {

    @Resource
    private DictionaryTypeMapper dictionaryTypeMapper;

    @Resource
    private HttpServletRequest request;
    @Override
    public String addDictionaryType(String type, String name) {
        DictionaryType dictionaryType = new DictionaryType();
        dictionaryType.setType(type);
        dictionaryType.setName(name);
        dictionaryType.setCreateTime(LocalDateTime.now());
        dictionaryType.setCreateUserId(Integer.parseInt(request.getHeader("userid")));
        int insert = dictionaryTypeMapper.insert(dictionaryType);
        if (insert != 1) {
            throw new LinyiException(ResultCodeEnum.INSERT_FAIL);
        }
        return "新增字典类型成功";
    }

    @Override
    public String deleteDictionaryType(List<Integer> ids) {
        ids = ids.stream().filter(id -> id > 0).toList();
        int i = dictionaryTypeMapper.deleteBatchIds(ids);
        if (i<=0){
            throw new LinyiException("删除失败");
        }
        return i+"条数据删除成功";
    }

    @Override
    public String updateDictionaryType(DictionaryType dictionaryType) {
        dictionaryType.setUpdateTime(LocalDateTime.now());
        dictionaryType.setUpdateUserId(Integer.parseInt(request.getHeader("userid")));
        int i = dictionaryTypeMapper.updateById(dictionaryType);
        if (i!=1){
            throw new LinyiException("修改失败");
        }
        return "修改成功";
    }

    @Override
    public List<DictionaryType> getDictionaryTypeList() {
        return dictionaryTypeMapper.selectList(null);
    }


}
