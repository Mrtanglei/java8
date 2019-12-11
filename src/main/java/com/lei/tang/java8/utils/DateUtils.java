package com.lei.tang.java8.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.Assert;

/**
 * @author tanglei
 * @date 2019/12/10
 */
public final class DateUtils {

    /**
     * 获取目标时间年
     *
     * @param date 目标时间
     * @return
     */
    public static int getYear(Date date) {
        return instanceCalendar(date).get(Calendar.YEAR);
    }

    /**
     * 获取目标时间月
     *
     * @param date 目标时间
     * @return
     */
    public static int getMonth(Date date) {
        return instanceCalendar(date).get(Calendar.MONTH);
    }

    /**
     * 获取目标时间日
     *
     * @param date 目标时间
     * @return
     */
    public static int getDay(Date date) {
        return instanceCalendar(date).get(Calendar.DATE);
    }

    /**
     * 获取目标时间当天开始、结束时间
     *
     * @param date
     * @param dayTimeType
     * @return
     */
    public static Date getTimeOfDay(Date date, DayTimeType dayTimeType) {
        Calendar calendar = instanceCalendar(date);
        buildTime(calendar, dayTimeType);
        return calendar.getTime();
    }

    /**
     * 获取目标时间当周开始、结束时间
     *
     * @param date        目标时间
     * @param dayTimeType
     * @return
     */
    public static Date getTimeOfWeek(Date date, DayTimeType dayTimeType) {
        Calendar calendar = instanceCalendar(date);
        //将周一设置为每个星期开始的第一天
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, dayTimeType == DayTimeType.START ? Calendar.MONDAY : Calendar.SUNDAY);
        buildTime(calendar, dayTimeType);
        return calendar.getTime();
    }

    /**
     * 获取目标时间当月开始、结束时间
     *
     * @param date        目标时间
     * @param dayTimeType
     * @return
     */
    public static Date getTimeOfMonth(Date date, DayTimeType dayTimeType) {
        Calendar calendar = instanceCalendar(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if (DayTimeType.END == dayTimeType) {
            calendar.add(Calendar.MONTH, 1);
            calendar.add(Calendar.DATE, -1);
        }
        buildTime(calendar, dayTimeType);
        return calendar.getTime();
    }

    /**
     * 获取目标时间多少天之前、之后的时间
     *
     * @param date 目标时间
     * @param day  正数代表之后
     * @return
     */
    public static Date getTimeOfAfterDay(Date date, int day) {
        Calendar calendar = instanceCalendar(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 比较两个时间相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenDay(Date date1, Date date2) {
        Assert.isTrue(date1 != null && date2 != null, "比较时间为空");
        int compareResult = date1.compareTo(date2);
        if (compareResult != 0) {
            Calendar d1 = new GregorianCalendar();
            d1.setTime(compareResult < 0 ? date1 : date2);
            Calendar d2 = new GregorianCalendar();
            d2.setTime(compareResult < 0 ? date2 : date1);
            int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
            int y2 = d2.get(Calendar.YEAR);
            if (d1.get(Calendar.YEAR) != y2) {
                do {
                    days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                    d1.add(Calendar.YEAR, 1);
                } while (d1.get(Calendar.YEAR) != y2);
            }
            return days;
        }
        return 0;
    }

    private static Calendar instanceCalendar(Date date) {
        Assert.notNull(date, "操作时间为空");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    private static void buildTime(Calendar calendar, DayTimeType dayTimeType) {
        switch (dayTimeType) {
            case START:
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 00000);
                break;
            case END:
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 99999);
                break;
            default:
                break;
        }
    }

    @Getter
    @AllArgsConstructor
    public enum DayTimeType {

        START("开始"), END("结束");

        private final String desc;
    }
}
