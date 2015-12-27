package com.tc.dream.lovecalendar.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.tc.dream.lovecalendar.utils.LunarCalendar;
import com.tc.dream.lovecalendar.dao.ScheduleDAO;
import com.tc.dream.lovecalendar.utils.SpecialCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dream on 15/11/20.
 */
public class CalendarView extends BaseAdapter{

    private ScheduleDAO dao;
    //是否为闰年
    private boolean isLeapyear = false;
    //某月的天数
    private int daysOfMonth = 0;
    //具体某一天是星期几
    private int dayOfWeek = 0;
    //上一个月的总天数
    private int lastDaysOfMonth = 0;
    private Context context;
    //一个gridview的日期存入此数组中
    private String[] dayNumber = new String[49];
    private static String week[] = {"周日","周一","周二","周三","周四","周五","周六"};
    private SpecialCalendar sc;
    private LunarCalendar lc;
    private Resources res;
    private Drawable drawble;

    private String currentYear = "";
    private String currentmonth = "";
    private String currentDay = "";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
    private int currentFlag = -1;     //用于标记当天
    private int[] schDateTagFlag = null;  //存储当月所有的日程日期

    private String showYear = "";   //用于在头部显示的年份
    private String showMonth = "";  //用于在头部显示的月份
    private String animalsYear = "";
    private String leapMonth = "";   //闰哪一个月
    private String cyclical = "";   //天干地支
    //系统当前时间
    private String sysDate = "";
    private String sys_year = "";
    private String sys_month = "";
    private String sys_day = "";

    //日程时间(需要标记的日程日期)
    private String sch_year = "";
    private String sch_month = "";
    private String sch_day = "";

    public CalendarView()
    {
        Date date = new Date();
        sysDate = sdf.format(date);
        sys_year = sysDate.split("-")[0];
        sys_month = sysDate.split("-")[1];
        sys_day = sysDate.split("-")[2];
    }

    public CalendarView(Context context,Resources rs,int jumpMonth,int jumpYear,int year_c,int month_c,int day_c)
    {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
