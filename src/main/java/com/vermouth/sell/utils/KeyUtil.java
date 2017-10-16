package com.vermouth.sell.utils;

import java.util.Random;
import java.util.UUID;

public class KeyUtil {
    /**
     * 生成唯一主键
     * @return
     */
    public static String generateUniqueKey() {
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
