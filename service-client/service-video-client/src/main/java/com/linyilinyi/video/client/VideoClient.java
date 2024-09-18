package com.linyilinyi.video.client;

import com.linyilinyi.common.model.PageResult;
import com.linyilinyi.common.model.Result;
import com.linyilinyi.model.vo.video.VideoQueryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/17
 * @ClassName: VideoClinet
 */
@FeignClient(value = "service-video")
public interface VideoClient {

    @PostMapping("/video/list")
     Result<PageResult> list(@RequestParam(required = false ,defaultValue = "1") long pageNo,
                                   @RequestParam(required = false ,defaultValue = "5") long pageSize,
                                   @RequestBody VideoQueryVo videoQueryVo);
}
