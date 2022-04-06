package com.rookie.submit.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public final class DateTimeUtil {
    private DateTimeUtil() {
    }

    public static final Pattern DATE_TIME_DAY = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    public static final Pattern DATE_TIME_TZ = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{0,}Z");

    public static final Pattern DATE_TIME_TZS = Pattern.compile("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d*Z");

    public static final Pattern DATE_TIME_S = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}(.\\d{0,})?");

    public static final Pattern DATE_TIME_S_MS = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}:\\d{3}");

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS_TZ = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static final String YYYY_MM_DD_HH_MM_SS_TZS = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final String YYYY_MM_DD_HH_MM_SS_1 = "yyyy/MM/dd HH:mm:ss";

    public static final String HH_MM_SS = "HH:mm:ss";

    private static HashMap<String, ThreadLocal<SimpleDateFormat>> factory = new HashMap<>();

    static {
        factory.put(YYYY_MM_DD_HH_MM_SS_TZ, ThreadLocal.withInitial(new SupplierSimpleDateFormat(YYYY_MM_DD_HH_MM_SS_TZ)));
        factory.put(YYYY_MM_DD_HH_MM_SS_TZS, ThreadLocal.withInitial(new SupplierSimpleDateFormat(YYYY_MM_DD_HH_MM_SS_TZS)));
        factory.put(YYYY_MM_DD_HH_MM_SS, ThreadLocal.withInitial(new SupplierSimpleDateFormat(YYYY_MM_DD_HH_MM_SS)));
        factory.put(YYYY_MM_DD_HH_MM_SS_MS, ThreadLocal.withInitial(new SupplierSimpleDateFormat(YYYY_MM_DD_HH_MM_SS_MS)));
        factory.put(YYYY_MM_DD, ThreadLocal.withInitial(new SupplierSimpleDateFormat(YYYY_MM_DD)));
        factory.put(YYYY_MM_DD_HH_MM_SS_1, ThreadLocal.withInitial(new SupplierSimpleDateFormat(YYYY_MM_DD_HH_MM_SS_1)));
        factory.put(HH_MM_SS, ThreadLocal.withInitial(new SupplierSimpleDateFormat(HH_MM_SS)));
    }

    private static class SupplierSimpleDateFormat implements Supplier<SimpleDateFormat> {
        private String format = null;

        SupplierSimpleDateFormat(String format) {
            this.format = format;
        }

        @Override
        public SimpleDateFormat get() {
            return new SimpleDateFormat(format);
        }
    }

    public static Date parse(String source) throws ParseException {

        if (StringUtils.isBlank(source)) {
            return null;
        }

        Date date = null;
        String value = source.trim();
        if (DATE_TIME_S.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD_HH_MM_SS).get().parse(value);
        } else if (DATE_TIME_TZ.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD_HH_MM_SS_TZ).get().parse(value);
        } else if (DATE_TIME_TZS.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD_HH_MM_SS_TZS).get().parse(value);
        } else if (DATE_TIME_S_MS.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD_HH_MM_SS_MS).get().parse(value);
        } else if (DATE_TIME_DAY.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD).get().parse(value);
        } else if (DATE_TIME_DAY.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD_HH_MM_SS_1).get().parse(value);
        } else if (DATE_TIME_DAY.matcher(value).matches()) {
            date = factory.get(YYYY_MM_DD_HH_MM_SS_1).get().parse(value);
        }

        return date;
    }

    public static String formatMillis(Long millis, String format) {
        return factory.get(format).get().format(millis);
    }

    public static String format(Date date, String format) {
        return factory.get(format).get().format(date);
    }

    /**
     * 计算某日期加减几天后的日期
     *
     * @param date 某日期
     * @param num  加减几天
     * @return 加减后日期
     */
    public static Date plusDay(Date date, int num) {
        if (date == null) {
            return null;
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.DATE, num);
        return ca.getTime();
    }

    /**
     * 获取该日期零点零分的日期
     *
     * @param date 日期
     * @return date
     */
    public static Date zeroDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取时间月份
     *
     * @param date
     * @return 月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.MONTH);
    }

    /**
     * 获取时间月内天
     *
     * @param date
     * @return 天
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取时间周内星期几
     *
     * @param date
     * @return 星期
     */
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int betweenDay(Date startDate, Date endDate) {

        return (int) ((endDate.getTime() - startDate.getTime()) / 86400000);
    }

    public static String utc2iso8601(long utc) {
        Locale loc = new Locale("en");
        SimpleDateFormat fm = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS_TZ, loc);
        return fm.format(utc * 1000);
    }

}
