package com.dlt.server.common.util.encrypt;

import com.dlt.server.common.util.encry.MD5EncryptUtil;
import org.junit.Test;

/**
 * Created by xuliugen on 2017/5/31.
 */
public class MD5EncryptUtilTest {

    private static final String paswword = "123456";
    private static final String userName = "xuliugen";

    @Test
    public void testEncry() {
        String salt = MD5EncryptUtil.encryptPassword(userName);
        System.out.println("salt-------" + salt);
        System.out.println("password---" + MD5EncryptUtil.encryptPassword(paswword, salt));
    }
}
