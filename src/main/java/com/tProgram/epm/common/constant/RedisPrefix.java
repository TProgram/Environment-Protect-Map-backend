package com.tProgram.epm.common.constant;

public class RedisPrefix {
    /**
     * 感悟ID 自增器 redis KEY前缀
     */
    public static String ThoughtIDIndex = "thought:index:%s";

    /**
     * 感悟ID 自增KEY 过期时间
     * 7 * 24 * 3600 一周
     */
    public static long ThoughtIndexExpireTime = 604800;
}
