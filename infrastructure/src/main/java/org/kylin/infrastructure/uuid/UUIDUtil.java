package org.kylin.infrastructure.uuid;

import java.util.UUID;

public class UUIDUtil {
    public static String generateUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
