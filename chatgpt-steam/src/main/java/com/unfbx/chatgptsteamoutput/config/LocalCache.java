package com.unfbx.chatgptsteamoutput.config;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;


public class LocalCache {
    /**
     * 캐시 시간
     */
    public static final long TIMEOUT = 5 * DateUnit.MINUTE.getMillis();
    /**
     * 청소 간격
     */
    private static final long CLEAN_TIMEOUT = 5 * DateUnit.MINUTE.getMillis();
    /**
     * 캐시 대상
     */
    public static final TimedCache<String, Object> CACHE = CacheUtil.newTimedCache(TIMEOUT);

    static {
        //시간 지정 작업을 시작합니다.
        CACHE.schedulePrune(CLEAN_TIMEOUT);
    }
}
