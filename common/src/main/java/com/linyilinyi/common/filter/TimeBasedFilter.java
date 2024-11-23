package com.linyilinyi.common.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.spi.FilterReply;
import ch.qos.logback.core.filter.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;

/**
 * @Description
 * @Author linyi
 * @Date 2024/9/13
 * @ClassName: TimeBasedFilter
 */
@Slf4j
public class TimeBasedFilter extends Filter<ILoggingEvent> {

    private static final long FIVE_MINUTES_MILLIS = 5 * 60 * 1000;

    private static long lastLogTimestamp;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        long currentTime = event.getTimeStamp();
        if(lastLogTimestamp==0){
            lastLogTimestamp = currentTime;
        }
        if(currentTime - lastLogTimestamp > FIVE_MINUTES_MILLIS) {
            log.info("\n\n\n\n\n\n");
            lastLogTimestamp = currentTime;
        }
        return FilterReply.NEUTRAL;
    }

}

