package com.linyilinyi.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PageResult 分页结果")
public class PageResult<T> implements Serializable {

    @Schema(title = "数据列表")
    private List<T> items;

    @Schema(title = "数据总数")
    private long counts;

    @Schema(title = "当前页码")
    private long pageNo;

    @Schema(title = "每页数据条数")
    private long pageSize;
}