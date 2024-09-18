package com.linyilinyi.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "PageParams 分页参数")
public class PageParams{
    // 默认起始页码
    public static final long DEFAULT_PAGE_CURRENT = 1;
    // 默认每页记录数
    public static final long DEFAULT_PAGE_SIZE = 8;

    public PageParams(){
    }

    // 当前页码
    @Schema(title = "当前页码")
    private long pageNo = DEFAULT_PAGE_CURRENT;

    // 当前每页记录数
    @Schema(title = "每页记录数")
    private long pageSize = DEFAULT_PAGE_SIZE;
}
