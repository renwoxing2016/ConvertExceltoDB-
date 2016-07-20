//excel api import
import jxl.Sheet;
import jxl.Workbook;

//import com.main.java.EarthQuakeEntity;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author duan
 * @Email xxx@gmail.com
 * Created by neusoft on 2016/7/13.
 */
public class ExcelService {

    /**
     * 查询指定目录中电子表格中所有的数据
     * @param file 文件完整路径
     * @return
     */
    public static List<EarthQuakeEntity> getAllByExcel(String file){
        List<EarthQuakeEntity> list=new ArrayList<EarthQuakeEntity>();
        try {
            Workbook rwb=Workbook.getWorkbook(new File(file));
            //Sheet rs=rwb.getSheet("Test Shee 1");//或者rwb.getSheet(0)
            Sheet rs=rwb.getSheet(0);
            int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行

            System.out.println(" clos:"+clos+" rows:"+rows);
            for (int i = 1; i < rows; i++) { //行数从1开始
                for (int j = 0; j < clos; j++) {
                    //第一个是列数，第二个是行数
                    String strdate=rs.getCell(j++, i).getContents();//默认最左边编号也算一列 所以这里得j++
                    String strscale=rs.getCell(j++, i).getContents();
                    String strlat=rs.getCell(j++, i).getContents();
                    String strlon=rs.getCell(j++, i).getContents();
                    String strdepth=rs.getCell(j++, i).getContents();
                    String strname=rs.getCell(j++, i).getContents();

                    //System.out.println("QuakeEntity [Date=" + strdate + ", Scale=" + strscale+ ", name=" + strname + ", Depth=" + strdepth
                    //+ ", longitude=" + strlon + ", latitude=" + strlat + "]");

                    EarthQuakeEntity ptempquake = new EarthQuakeEntity();
                    ptempquake.setquake_date(strdate);
                    ptempquake.setmagnitude(strscale);
                    ptempquake.setepicenter_latitude(strlat);
                    ptempquake.setepicenter_longitude(strlon);
                    ptempquake.setepicenter_depth(strdepth);
                    ptempquake.setquake_pointname(strname);

                    list.add(ptempquake);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return list;

    }


}



