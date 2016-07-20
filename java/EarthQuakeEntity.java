import org.apache.commons.lang.time.DateFormatUtils;

import java.io.*;

import java.util.Date;

/**
 * Created by neusoft on 2016/7/13.
 */
public class EarthQuakeEntity {

    private Date quake_date;        //地震日期
    //private DateFormatUtils quake_date;        //地震日期
    private double magnitude;      //震级
    private double epicenter_longitude;     //震中经度,单位度
    private double epicenter_latitude;      //震中纬度,单位度
    private double epicenter_depth;          //震中深度,单位千米
    private String quake_pointname;           //地震位置名称

    private String strquake_date;        //地震日期
    private String strmagnitude;      //震级
    private String strepicenter_longitude;     //震中经度
    private String strepicenter_latitude;      //震中纬度
    private String strepicenter_depth;          //震中深度

    private int id;
    private int richter_scale;  //里氏震级
    private int death_toll;     //死亡人数
    private int survivors;      //幸存者 数量
    private int victims;        //受灾者 数量

    public EarthQuakeEntity() {
    }

    @Override
    public String toString() {
        //return convertoString2();
        return convertoString1();
    }
    private String convertoString1() {
        return "QuakeEntity [Date=" + quake_date + ", Scale=" + magnitude+ ", name=" + quake_pointname + ", Depth=" + epicenter_depth
                + ", longitude=" + epicenter_longitude + ", latitude=" + epicenter_latitude + "]";
    }
    private String convertoString2() {
        return "QuakeEntity [Date=" + strquake_date + ", Scale=" + strmagnitude+ ", name=" + quake_pointname + ", Depth=" + strepicenter_depth
                + ", longitude=" + strepicenter_longitude + ", latitude=" + strepicenter_latitude + "]";
    }

    public Date getquake_date() {
        return quake_date;
    }
    public void setquake_date(Date day) {
        this.quake_date = day;
    }
    public void setquake_date(String day) {
        this.strquake_date = day;
        this.quake_date = StringToXUtil.StringTodate(day);
    }

    public double getmagnitude() {
        return magnitude;
    }
    public void setmagnitude(double ma) {
        this.magnitude = ma;
    }
    public void setmagnitude(String ma) {
        this.strmagnitude = ma;
        this.magnitude = StringToXUtil.StringTodouble(ma);
    }

    public double getepicenter_longitude() {
        return epicenter_longitude;
    }
    public void setepicenter_longitude(double ma) {
        this.epicenter_longitude = ma;
    }
    public void setepicenter_longitude(String ma) {
        this.strepicenter_longitude = ma;
        this.epicenter_longitude = StringToXUtil.StringTodouble(ma);
    }

    public double getepicenter_latitude() {
        return epicenter_latitude;
    }
    public void setepicenter_latitude(double ma) {
        this.epicenter_latitude = ma;
    }
    public void setepicenter_latitude(String ma) {
        this.strepicenter_latitude = ma;
        this.epicenter_latitude =StringToXUtil.StringTodouble(ma);
    }

    public double getepicenter_depth() {
        return epicenter_depth;
    }
    public void setepicenter_depth(double ma) {
        this.epicenter_depth = ma;
    }
    public void setepicenter_depth(String ma) {
        this.strepicenter_depth = ma;
        this.epicenter_depth = StringToXUtil.StringTodouble(ma);
    }

    public String getquake_pointname() {
        return quake_pointname;
    }
    public void setquake_pointname(String ma) {
        this.quake_pointname = ma;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getRichter_scale() {
        return richter_scale;
    }
    public void setRichter_scale(int scale) {
        this.richter_scale = scale;
    }

    public int getdeath_toll() {
        return death_toll;
    }
    public void setdeath_toll(int toll) {
        this.death_toll = toll;
    }

    public int getsurvivors() {
        return survivors;
    }
    public void setsurvivors(int num) {
        this.survivors = num;
    }

    public int getvictims() {
        return victims;
    }
    public void setvictims(int num) {
        this.victims = num;
    }

}
