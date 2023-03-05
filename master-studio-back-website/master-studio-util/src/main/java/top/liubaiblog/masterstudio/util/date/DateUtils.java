package top.liubaiblog.masterstudio.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 留白
 * @description 日期工具类
 */
public class DateUtils {

    public static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 解析时间字符串
     */
    public static Date parse(String dateStr) {
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解析时间字符串，自定义
     */
    public static Date parse(String dateStr, String pattern) {
        try {
            SimpleDateFormat tempFormatter = new SimpleDateFormat(pattern);
            return tempFormatter.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 格式化时间对象
     */
    public static String format(Date date) {
        return formatter.format(date);
    }

    /**
     * 格式化时间对象，自定义
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat tempFormatter = new SimpleDateFormat(pattern);
        return tempFormatter.format(date);
    }

}
