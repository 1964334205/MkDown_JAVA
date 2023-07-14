package top.openfbi.mdnote.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Hash {
    public static String md5(byte [] data) {
        // 使用md5计算文件哈希值
        return DigestUtils.md5Hex(data).toUpperCase();
    }
}
