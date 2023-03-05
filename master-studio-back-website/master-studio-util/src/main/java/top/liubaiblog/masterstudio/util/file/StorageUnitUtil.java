package top.liubaiblog.masterstudio.util.file;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ***
 */
public class StorageUnitUtil {
    /**
     * bit、B、KB、MB、GB、TB、PB、EB、ZB、YB、BB、NB、DB、CB……
     */
    public enum Unit{
        // 值必须由小到大递增
        bit,
        B,
        KB,
        MB,
        GB,
        TB,
        PB,
        EB,
        ZB,
        YB,
        BB,
        NB,
        DB,
        CB
    }

    /**
     * 换算基数:例: 1024;1000...
     */
    private final static int basic = 1024;

    /**
     * 自动转换:value>=basic,自动向大单位转换,否则返回value
     * @param value 转换前的值(不能为'0'且小于Double.MAX_VALUE）
     * @param currentUnit 转换前的单位
     * @return 返回转后的值和单位字符
     */
    public static String autoConvert(Number value, Unit currentUnit) {
        if (value.doubleValue() == 0 && value.doubleValue() < Double.MAX_VALUE) {
            throw new IllegalArgumentException("需要转换的值不能为0且小于Double.MAX_VALUE");
        }
        return autoToBig(value, currentUnit, null, null);
    }

    /**
     * 自动转换:value>=basic,自动向大单位转换,否则返回value;可定义分隔符或单位后缀
     * @param value 转换前的值(不能为'0'且小于Double.MAX_VALUE）
     * @param currentUnit 转换前的单位
     * @param separatorChar 值和单位中间的分隔符
     * @param unitSuffix 单位后面的后缀
     * @return 返回转后的值和(分隔符<若有>)单位(后缀<若有>)字符
     */
    public static String autoConvert(Number value, Unit currentUnit, String separatorChar, String unitSuffix) {
        if (value.doubleValue() == 0 && value.doubleValue() < Double.MAX_VALUE) {
            throw new IllegalArgumentException("需要转换的值不能为0且小于Double.MAX_VALUE");
        }
        return autoToBig(value, currentUnit, separatorChar, unitSuffix);
    }

    /**
     * 自动转换:valueUnit中的value>=basic,自动向大单位转换,否则返回value
     * @param valueUnit 转换前的值和单位字符
     * @return 返回转后的值和单位字符
     */
    public static String autoConvert(String valueUnit) {
        Map<String, String> map = extractArgument(valueUnit);
        if (map.isEmpty()) {
            throw new IllegalArgumentException("参数无法解析: " + valueUnit);
        } else {
            Number number = Double.valueOf(map.get("number"));
            if (number.doubleValue() == 0 && number.doubleValue() < Double.MAX_VALUE) {
                throw new IllegalArgumentException("需要转换的值不能为0");
            }
            Unit fromUnit = Unit.valueOf(map.get("unit"));
            return autoToBig(number, fromUnit, null, null);
        }
    }

    /**
     * 自动转换:valueUnit中的value>=basic,自动向大单位转换,否则返回value;可定义分隔符或单位后缀
     * @param valueUnit 转换前的值和单位字符
     * @param separatorChar 值和单位中间的分隔符
     * @param unitSuffix 单位后面的后缀
     * @return 返回转后的值和(分隔符<若有>)单位(后缀<若有>)字符
     */
    public static String autoConvert(String valueUnit, String separatorChar, String unitSuffix) {
        Map<String, String> map = extractArgument(valueUnit);
        if (map.isEmpty()) {
            throw new IllegalArgumentException("参数无法解析: " + valueUnit);
        } else {
            Number number = Double.valueOf(map.get("number"));
            if (number.doubleValue() == 0 && number.doubleValue() < Double.MAX_VALUE) {
                throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
            }
            Unit fromUnit = Unit.valueOf(map.get("unit"));
            return autoToBig(number, fromUnit, separatorChar, unitSuffix);
        }
    }

    /**
     * 指定转换:指定转换后的单位,valueUnit中的value向指定单位换算成对应的值
     * @param valueUnit 转换前的值和单位字符
     * @param toUnit 转换后的单位
     * @return 返回转后的值和单位字符
     */
    public static String customConvert(String valueUnit, Unit toUnit) {
        Map<String, String> map = extractArgument(valueUnit);
        String result;
        if (map.isEmpty()) {
            throw new IllegalArgumentException("参数无法解析: " + valueUnit);
        } else {
            Number number = Double.valueOf(map.get("number"));
            if (number.doubleValue() == 0 && number.doubleValue() < Double.MAX_VALUE) {
                throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
            }
            Unit fromUnit = Unit.valueOf(map.get("unit"));
            int fromOrdinal = fromUnit.ordinal();
            int toOrdinal = toUnit.ordinal();
            if (fromOrdinal == toOrdinal) {
                throw new IllegalArgumentException("当前单位和转换结果的目标单位不能相同");
            }
            // big to small
            if (fromOrdinal > toOrdinal) {
                result = customToSmall(number, fromUnit, toUnit, null, null);
            } else {
                result = autoToBig(number, fromUnit, null, null);
            }
        }
        return result;
    }

    /**
     * 指定转换:指定转换后的单位,valueUnit中的value向指定单位换算成对应的值;可定义分隔符或单位后缀
     * @param valueUnit 转换前的值和单位字符
     * @param toUnit    转换后的单位
     * @param separatorChar 值和单位中间的分隔符
     * @param unitSuffix 单位后面的后缀
     * @return 返回转后的值和(分隔符<若有>)单位(后缀<若有>)字符
     */
    public static String customConvert(String valueUnit, Unit toUnit, String separatorChar, String unitSuffix) {
        Map<String, String> map = extractArgument(valueUnit);
        String result;
        if (map.isEmpty()) {
            throw new IllegalArgumentException("参数无法解析: " + valueUnit);
        } else {
            Number number = Double.valueOf(map.get("number"));
            if (number.doubleValue() == 0 && number.doubleValue() < Double.MAX_VALUE) {
                throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
            }
            Unit fromUnit = Unit.valueOf(map.get("unit"));
            int fromOrdinal = fromUnit.ordinal();
            int toOrdinal = toUnit.ordinal();
            if (fromOrdinal == toOrdinal) {
                throw new IllegalArgumentException("当前单位和转换结果的目标单位不能相同");
            }
            // big to small
            if (fromOrdinal > toOrdinal) {
                result = customToSmall(number, fromUnit, toUnit, separatorChar, unitSuffix);
            } else {
                result = autoToBig(number, fromUnit, separatorChar, unitSuffix);
            }
        }
        return result;
    }

    /**
     * 指定转换:指定转换后的单位,value向指定单位换算成对应的值
     * @param value 转换前的值
     * @param currentUnit 转换前的单位
     * @param toUnit 转换后的单位
     * @return 返回转后的值和单位字符
     */
    public static String customConvert(Number value,Unit currentUnit, Unit toUnit) {
        String result;
        if (value.doubleValue() == 0 && value.doubleValue() < Double.MAX_VALUE) {
            throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
        }
        int fromOrdinal = currentUnit.ordinal();
        int toOrdinal = toUnit.ordinal();
        if (fromOrdinal == toOrdinal) {
            throw new IllegalArgumentException("当前单位和转换结果的目标单位不能相同");
        }
        // big to small
        if (fromOrdinal > toOrdinal) {
            result = customToSmall(value, currentUnit, toUnit, null, null);
        } else {
            result = autoToBig(value, currentUnit, null, null);
        }
        return result;
    }

    /**
     *
     * 指定转换:指定转换后的单位,value向指定单位换算成对应的值;可定义分隔符或单位后缀
     * @param value 转换前的值
     * @param currentUnit 转换前的单位
     * @param toUnit 转换后的单位
     * @param separatorChar 值和单位中间的分隔符
     * @param unitSuffix 单位后面的后缀
     * @return 返回转换后的值和(分隔符<若有>)单位(后缀<若有>)字符
     */
    public static String customConvert(Number value, Unit currentUnit, Unit toUnit, String separatorChar, String unitSuffix) {
        String result;
        if (value.doubleValue() == 0 && value.doubleValue() < Double.MAX_VALUE) {
            throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
        }
        int fromOrdinal = currentUnit.ordinal();
        int toOrdinal = toUnit.ordinal();
        if (fromOrdinal == toOrdinal) {
            throw new IllegalArgumentException("当前单位和转换结果的目标单位不能相同");
        }
        // big to small
        if (fromOrdinal > toOrdinal) {
            result = customToSmall(value, currentUnit, toUnit, separatorChar, unitSuffix);
        } else {
            result = autoToBig(value, currentUnit, separatorChar, unitSuffix);
        }
        return result;
    }

    /**
     * 指定转换:指定转换后的单位,value向指定单位换算成对应的值并返回
     * @param value 转换前的值
     * @param currentUnit 转换前的单位
     * @param toUnit 转换后的单位
     * @return 返回转换后的值 java.lang.Number
     */
    public static Number getNumberConvert(Number value,Unit currentUnit, Unit toUnit) {
        Number number;
        if (value.doubleValue() == 0 && value.doubleValue() < Double.MAX_VALUE) {
            throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
        }
        int fromOrdinal = currentUnit.ordinal();
        int toOrdinal = toUnit.ordinal();
        if (fromOrdinal == toOrdinal) {
            throw new IllegalArgumentException("当前单位和转换结果的目标单位不能相同");
        }
        // big to small
        if (fromOrdinal > toOrdinal) {
            number = customToSmall(value, currentUnit, toUnit);
        } else {
            number = autoToBig(value, currentUnit);
        }
        return number;
    }

    /**
     * 指定转换:指定转换后的单位,valueUnit中的value向指定单位换算成对应的值并返回
     * @param valueUnit 转换前的值和单位字符
     * @param toUnit 转换后的单位
     * @return 返回转换后的值 java.lang.Number
     */
    public static Number getNumberConvert(String valueUnit, Unit toUnit) {
        Map<String, String> map = extractArgument(valueUnit);
        Number number;
        if (map.isEmpty()) {
            throw new IllegalArgumentException("参数无法解析: " + valueUnit);
        } else {
            number = Double.valueOf(map.get("number"));
            if (number.doubleValue() == 0 && number.doubleValue() < Double.MAX_VALUE) {
                throw new IllegalArgumentException("需要转换的值不能为0或超出计算范围");
            }
            Unit fromUnit = Unit.valueOf(map.get("unit"));
            int fromOrdinal = fromUnit.ordinal();
            int toOrdinal = toUnit.ordinal();
            if (fromOrdinal == toOrdinal) {
                throw new IllegalArgumentException("当前单位和转换结果的目标单位不能相同");
            }
            // big to small
            if (fromOrdinal > toOrdinal) {
                number = customToSmall(number, fromUnit, toUnit);
            } else {
                number = autoToBig(number, fromUnit);
            }
        }
        return number;
    }

    private static Number customToSmall(Number number, Unit fromUnit, Unit toUnit){
        final int enumIdx = 0;
        final int special = 8;
        final int resultScale = 2;
        int fromOrdinal = fromUnit.ordinal();
        int toOrdinal = toUnit.ordinal();
        int level = fromOrdinal - toOrdinal;
        BigDecimal value = BigDecimal.valueOf(number.doubleValue());
        BigDecimal basicDec = BigDecimal.valueOf(basic);
        BigDecimal specialDec = BigDecimal.valueOf(special);
        if (toOrdinal == enumIdx) {
            level -= 1;
            value = value.multiply(specialDec);
        }
        for (int i = 1; i <= level; i++) {
            value = value.multiply(basicDec);
        }
        value = value.setScale(resultScale, RoundingMode.HALF_UP);
        return formatToLong(value);
    }

    private static String customToSmall(Number number, Unit fromUnit, Unit toUnit, String separatorChar, String unitSuffix) {
        final int enumIdx = 0;
        final int special = 8;
        final int resultScale = 2;
        int fromOrdinal = fromUnit.ordinal();
        int toOrdinal = toUnit.ordinal();
        int level = fromOrdinal - toOrdinal;
        BigDecimal value = BigDecimal.valueOf(number.doubleValue());
        BigDecimal basicDec = BigDecimal.valueOf(basic);
        BigDecimal specialDec = BigDecimal.valueOf(special);
        if (toOrdinal == enumIdx) {
            level -= 1;
            value = value.multiply(specialDec);
        }
        for (int i = 1; i <= level; i++) {
            value = value.multiply(basicDec);
        }
        value = value.setScale(resultScale, RoundingMode.HALF_UP);
        Number resultVal = formatToLong(value);
        String result = String.valueOf(resultVal);
        if (separatorChar != null && !"".equals(separatorChar) && separatorChar.length() > 0) {
            result = result.concat(separatorChar);
        }
        result = result.concat(toUnit.name());
        if (unitSuffix != null && !"".equals(unitSuffix) && unitSuffix.length() > 0) {
            result = result.concat(unitSuffix);
        }
        return result;
    }

    private static Map<String, String> extractArgument(String valUnit) {
        Map<String, String> map = new HashMap<>();
        Unit[] units = Unit.values();
//        String unitsStr = Arrays.stream(units).map(Enum::name).collect(Collectors.joining("|"));
        StringBuilder builder = new StringBuilder();
        for (Unit unit : units) {
            String name = unit.name();
            builder.append(name);
            if (unit.ordinal() < (units.length - 1)) {
                builder.append("|");
            }
        }
        final String regex = "^(\\d+[.]?\\d*)\\s*(" + builder + ")$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(valUnit);
        while (matcher.find()) {
            map.put("number", matcher.group(1));
            map.put("unit", matcher.group(2));
        }
        return map;
    }

    private static Unit getUnitByOrdinal(int ordinal) {
        Unit[] units = Unit.values();
        for (Unit unit : units) {
            if (unit.ordinal() == ordinal) {
                return unit;
            }
        }
        throw new IllegalArgumentException("未录入该存储单位");
    }

    private static Number autoToBig(Number val, Unit fromUnit) {
        final int enumIdx = 0;
        int fromOrdinal = fromUnit.ordinal();
        if (enumIdx == fromOrdinal) {
            val = bitToByte(val);
            fromOrdinal += 1;
        }
        // 计算时尽量保持精确度 保留10位小数s
        final int scale = 10;
        // 返回时保留2位小数
        final int resultScale = 2;
        BigDecimal smallVal = BigDecimal.valueOf(val.doubleValue());
        BigDecimal basicVal = BigDecimal.valueOf(basic);
        while (smallVal.compareTo(basicVal) >= 0 && (fromOrdinal < Unit.values().length - 1)) {
            smallVal = smallVal.divide(basicVal, scale, RoundingMode.HALF_UP);
            fromOrdinal += 1;
        }
        smallVal = smallVal.setScale(resultScale, RoundingMode.HALF_UP);
        return formatToLong(smallVal);
    }

    private static String autoToBig(Number val, Unit fromUnit, String separatorChar, String unitSuffix) {
        final int enumIdx = 0;
        int fromOrdinal = fromUnit.ordinal();
        if (enumIdx == fromOrdinal) {
            val = bitToByte(val);
            fromOrdinal += 1;
        }
        // 计算时尽量保持精确度 保留10位小数s
        final int scale = 10;
        // 返回时保留2位小数
        final int resultScale = 2;
        BigDecimal smallVal = BigDecimal.valueOf(val.doubleValue());
        BigDecimal basicVal = BigDecimal.valueOf(basic);
        while (smallVal.compareTo(basicVal) >= 0 && (fromOrdinal < Unit.values().length - 1)) {
            smallVal = smallVal.divide(basicVal, scale, RoundingMode.HALF_UP);
            fromOrdinal += 1;
        }
        Unit toUnit = getUnitByOrdinal(fromOrdinal);
        smallVal = smallVal.setScale(resultScale, RoundingMode.HALF_UP);
        Number number = formatToLong(smallVal);
        String result = String.valueOf(number);
        if (separatorChar != null && !"".equals(separatorChar) && separatorChar.length() > 0) {
            result = result.concat(separatorChar);
        }
        result = result.concat(toUnit.name());
        if (unitSuffix != null && !"".equals(unitSuffix) && unitSuffix.length() > 0) {
            result = result.concat(unitSuffix);
        }
        return result;
    }

    private static Number bitToByte(Number bitSize) {
        final int basic = 8;
        final int scale = 10;
        double bitDouble = bitSize.doubleValue();
        if (bitDouble == 0) {
            return bitSize;
        }
        BigDecimal bitDec = BigDecimal.valueOf(bitDouble);
        BigDecimal basicDec = BigDecimal.valueOf(basic);
        BigDecimal byteDec = bitDec.divide(basicDec, scale, RoundingMode.HALF_UP);
        return formatToLong(byteDec);
    }

    private static Number formatToLong(Number val) {
        double d = val.doubleValue();
        long l = val.longValue();
        if (d == l) {
            return l;
        }
        return val;
    }

    public static void main(String[] args) {

        String s1 = autoConvert(10240, Unit.KB);
        System.out.println("s1 = " + s1);
        //s1 = 10MB

        String s2 = autoConvert(8192, Unit.KB, " ", "/s");
        System.out.println("s2 = " + s2);
        //s2 = 8 MB/s

        String s3 = autoConvert("10240TB");
        System.out.println("s3 = " + s3);
        //s3 = 10PB

        String s4 = autoConvert("1023GB", " ", "/每天");
        System.out.println("s4 = " + s4);
        //s4 = 1023 GB/每天

        String s5 = customConvert("1TB", Unit.bit);
        System.out.println("s5 = " + s5);
        //s5 = 8796093022208bit

        String s6 = customConvert(1, Unit.TB, Unit.bit);
        System.out.println("s6 = " + s6);
        //s6 = 8796093022208bit

        String s7 = customConvert("1CB", Unit.YB);
        System.out.println("s7 = " + s7);
        //s7 = 1099511627776YB

        String s8 = autoConvert(9.8938489077261e+27,Unit.bit);
        System.out.println("s8 = " + s8);
        //s8 = 1023YB

        String s9 = customConvert(1024, Unit.B, Unit.bit, " ", null);
        System.out.println("s9 = " + s9);
        //s9 = 8192 bit

        String s10 = customConvert("1024KB", Unit.MB, " ", "/s");
        System.out.println("s10 = " + s10);
        //s10 = 1 MB/s

        Number n1 = getNumberConvert("1024GB", Unit.MB);
        System.out.println("n1 = " + n1);
        //n1 = 1048576

        Number n2 = getNumberConvert(1024, Unit.KB, Unit.MB);
        System.out.println("n2 = " + n2);
        //n2 = 1
    }
}