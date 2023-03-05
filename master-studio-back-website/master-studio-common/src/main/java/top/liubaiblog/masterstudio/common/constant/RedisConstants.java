package top.liubaiblog.masterstudio.common.constant;

/**
 * @author 留白
 * @description
 */
public class RedisConstants {
    public static final String SEPARATOR = ":";
    public static final String AUTH_PREFIX = "auth";
    public static final long AUTH_EXPIRE_SECONDS = 172800;
    public static final String FORGET_PASSWORD_PREFIX = "forget";
    public static final String FORGET_STEP1_PREFIX = "step1";
    public static final String FORGET_STEP2_PREFIX = "step2";
    public static final long FORGET_STEP1_EXPIRE_SECONDS = 120;
    public static final long FORGET_STEP2_EXPIRE_SECONDS = 240;
}
