package com.fb.common.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;

public class SnowFlakeUtils {
   //机器id
//   private static final long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
   private static final long workerId = 2;

   //数据中心id
   private static final long datacenterId = 1;//数据中心ID

   private static final Snowflake snowflake = IdUtil.createSnowflake(workerId,datacenterId);

   //雪花算法获取唯一id
   public static synchronized long snowflakeId(){
      return snowflake.nextId();
   }

   public static void main(String[] args) {
      System.out.println(snowflakeId());
   }
}
