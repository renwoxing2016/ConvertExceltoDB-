
import org.apache.commons.lang.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by neusoft on 2016/7/15.
 */
public class StringToXUtil {
    private static StringToXUtil ourInstance = new StringToXUtil();

    public static StringToXUtil getInstance() {
        return ourInstance;
    }

    private StringToXUtil() {
    }

    //---------------------------------------------------------------------


    ///**
    // * 把日期的string(yyyy/mm/dd hh:mm:ss形式) 转换成 日期yyyy/mm/dd hh:mm:ss
    // */
    public static Date StringTodate(String strday){
        if(isEmpty(strday))
            return new Date(0);

        //SimpleDateFormat conversdf = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
        SimpleDateFormat conversdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        try {
            Date day = conversdf.parse(strday);
            return day;
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return new Date(0);
    }

    ///**
    // * 把日期的string(yyyy/mm/dd形式) 转换成 日期yyyy/mm/dd
    // */
    public static Date StringTodate2(String strday){
        if(isEmpty(strday))
            return new Date(0);

        SimpleDateFormat conversdf = new SimpleDateFormat("yyyy/mm/dd");
        try {
            Date day = conversdf.parse(strday);
            return day;
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return new Date(0);
    }

    ///**
    // * 把日期的string(yyyy-mm-dd形式) 转换成 日期yyyy-mm-dd
    // */
    public static Date StringTodate3(String strday){
        if(isEmpty(strday))
            return new Date(0);

        SimpleDateFormat conversdf = new SimpleDateFormat("yyyy-mm-dd");
        try {
            Date day = conversdf.parse(strday);
            return day;
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        return new Date(0);
    }

    ///**
    // * 把浮点数string 转换成 double
    // */
    public static double StringTodouble(String strd){
        if(isEmpty(strd))
            return 0;
        double d = Double.parseDouble(strd);
        return d;
    }

    ///**
    // * 把浮点数string 转换成 int
    // */
    public static int StringToint(String strd){
        if(isEmpty(strd))
            return 0;
        int i = Integer.parseInt(strd);
        return i;
    }

    ///**
    // * 把浮点数string 转换成 float
    // */
    public static float StringTofloat(String strd){
        if(isEmpty(strd))
            return 0;
        float f = Float.parseFloat(strd);
        return f;
    }

    ///**
    // * 把浮点数string 转换成 Long
    // */
    public static long StringTolong(String strd){
        if(isEmpty(strd))
            return 0;
        long l = Long.parseLong(strd);
        return l;
    }

    ///**
    // * 把浮点数string 转换成 short
    // */
    public static short StringToshort(String strd){
        if(isEmpty(strd))
            return 0;
        short s = Short.parseShort(strd);
        return s;
    }

    ///**
    // * 把浮点数string 转换成 boolean
    // */
    public static boolean StringToboolean(String strd){
        if(isEmpty(strd))
            return false;
        boolean b = Boolean.parseBoolean(strd);
        return b;
    }

    //---------------------------------------------------------------------
    ///**
    // * 判断输入的字符串参数是否为空
    // * @return boolean 空则返回true,非空则flase
    // */
    public static boolean isEmpty(String input) {
        return null==input || 0==input.length() || 0==input.replaceAll("\\s", "").length();
    }

    ///**
    // * 判断输入的字节数组是否为空
    // * @return boolean 空则返回true,非空则flase
    // */
    public static boolean isEmpty(byte[] bytes){
        return null==bytes || 0==bytes.length;
    }

    ///**
    // * 字符串转为字节数组
    /// * @see 该方法默认以ISO-8859-1转码
    // * @see 若想自己指定字符集,可以使用<code>getBytes(String str, String charset)</code>方法
    // */
    public static byte[] getBytes(String data){
        return getBytes(data, "ISO-8859-1");
    }

    ///**
    // * 字符串转为字节数组
    // * @see 如果系统不支持所传入的<code>charset</code>字符集,则按照系统默认字符集进行转换
    // */
    public static byte[] getBytes(String data, String charset){
        data = (data==null ? "" : data);
        if(isEmpty(charset)){
            return data.getBytes();
        }
        try {
            return data.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            System.out.println("将字符串[" + data + "]转为byte[]时发生异常:系统不支持该字符集[" + charset + "]");
            return data.getBytes();
        }
    }

    ///**
    // * 截取字符串中的从后向前数、X个字符。
    // * @see 该方法截取字符串中的从后向前数、X个字符。
    // */
    public static String getsubstringfromXtoEnd(String yuanstring,int x){
        return yuanstring.substring(yuanstring.length()-x,yuanstring.length());
    }

}
