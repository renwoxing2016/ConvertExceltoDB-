
import java.sql.Connection;     //导入数据库连接对象
import java.sql.DriverManager;  //导入数据库驱动管理对象
import java.sql.ResultSet;      //导入数据记录集对象
import java.sql.SQLException;   //导入数据SQL操作异常对象
import java.sql.Statement;      //导入SQL操作接口对象
import java.sql.PreparedStatement;      //导入SQL操作接口对象
import java.sql.Date;     //导入数据库的数据类型Date
import java.sql.Timestamp;     //导入数据库的数据类型Timestamp

import java.util.List;


/**
 * Created by neusoft on 2016/7/18.
 */
public class DBConnect {
    private String dbDriver ="com.mysql.jdbc.Driver";
    private String dbUrl ="jdbc:mysql://localhost:3306/";////根据实际情况变化+数据库名
    private String dbUser ="root";
    private String dbPass ="";
    private String DBName =""; //="QuakeDB";//DB name
    private String TableName =""; //="QuakeTable";//DB table name

    private Connection getConn(String strDB) {
        Connection conn=null;
        try {
         Class.forName(dbDriver);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("DB Driver link error.");
        }

        try{
            String strURL = dbUrl+strDB;
            conn = DriverManager.getConnection(strURL,dbUser,dbPass);//注意是三个参数
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println(strDB+"DB link error.");
         }
        //System.out.println(strDB+"DB link OK.");
        return conn;
    }

    public int createdb(String stDB){
        int i=1;
        //String tableSql = "create table t_user (username varchar(50) not null primary key,"
        //        + "password varchar(20) not null ); ";

        String databaseSql = "create database " + stDB;
        DBName = stDB;

        Connection cnn=getConn("test");
        try{
            Statement stmt = cnn.createStatement();
            //如果同名数据库存在，删除
            //sql_statement.executeUpdate("drop table if exists student");
            //执行了一个sql语句生成了一个名为student的表
            //stmt.executeUpdate("create table test (id int not null auto_increment, name varchar() not null default 'name', math int not null default , primary key (id) ); ");
            stmt.executeUpdate(databaseSql);//创建数据库
            stmt.close(); //关闭数据记录集
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("创建数据库失败：" + DBName);
            return 0;//返回0为执行失败
        }
        System.out.println("创建数据库成功：" + DBName);
        return i;//返回1为执行成功
    }

    public int createTable(String strtable){
        int i=1;
        //String databaseSql = "create database " + stDB;
        TableName = strtable;

        //创建表
        //注意sql语句的每行前都要预留一个空格,如下形式写sql时候.
        String tableSql = "create table "+TableName
                +" (id int unsigned not null auto_increment primary key"
                +",timestamp datetime not null"//地震日期
                +",magnitude double(10,1) not null"//震级
                +",longitude double(10,2) not null"//震中经度
                +",latitude double(10,2) not null"//震中纬度
                +",depth double(10,2) not null"//震中深度
                +",pointname char(255) not null"//地震位置名称
                +")";

        String testSql = "create table testtable (id int unsigned not null auto_increment primary key,magnitude double(10,1) not null)";

        Connection cnn=getConn(DBName);
        try{
            Statement stmt = cnn.createStatement();
            //如果同名数据库存在，删除
            //sql_statement.executeUpdate("drop table if exists student");
            //执行了一个sql语句生成了一个名为student的表
            //stmt.executeUpdate("create table test (id int not null auto_increment, name varchar() not null default 'name', math int not null default , primary key (id) ); ");
            stmt.executeUpdate(tableSql);//创建表
            //stmt.executeUpdate(testSql);//创建表

            stmt.close(); //关闭数据记录集
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("创建表失败：" + TableName);
            return 0;//返回0为执行失败
        }
        System.out.println("创建表成功：" + TableName);
        return i;//返回1为执行成功
    }

    public int insertToTable(List<EarthQuakeEntity> quakelist){
        int i=0;
        int total=0;

        Connection cnn=getConn(DBName);
        try{
            int autoInckey = -1;
            String countsql="select max(id) from "+TableName;

            try{
                //String countsql1="select count(*) as num from "+TableName;
                //PreparedStatement pre =cnn.prepareStatement(countsql1);
                //ResultSet rs2 =pre.executeQuery();
                //autoInckey = rs2.getInt("num");
                //System.out.println("table count value is" + autoInckey);
                //pre.close(); //关闭数据记录集

                PreparedStatement pre1 =cnn.prepareStatement(countsql);
                ResultSet rs1 = pre1.executeQuery();
                if (rs1.next()) {
                    autoInckey = rs1.getInt(1);//取得ID
                } else {
                    // throw an exception from here
                    autoInckey = 20000;
                }
                System.out.println("table count value1 is " + autoInckey);
                pre1.close(); //关闭数据记录集
            }
            catch (SQLException e){
                e.printStackTrace();
                System.out.println("table count fail from" + TableName);
                return 0;//返回0为执行失败
            }

            autoInckey++;//id add+1

            //插入值到表中
            //注意sql语句的每行前都要预留一个空格,如下形式写sql时候.
            String insertsql="insert into "+TableName
                    +" (id,timestamp,magnitude,longitude,latitude,depth,pointname) values(?,?,?,?,?,?,?)";

            //List<EarthQuakeEntity>
            for(int j=0; j<quakelist.size(); j++){
                //System.out.println(quakelist.get(i).toString());
                PreparedStatement preStmt =cnn.prepareStatement(insertsql);
                preStmt.setInt(1,autoInckey);

                //java date -> sql timestamp
                Timestamp tt=new Timestamp(quakelist.get(j).getquake_date().getTime());
                preStmt.setTimestamp(2,tt);

                preStmt.setDouble(3,quakelist.get(j).getmagnitude());
                preStmt.setDouble(4,quakelist.get(j).getepicenter_longitude());
                preStmt.setDouble(5,quakelist.get(j).getepicenter_latitude());
                preStmt.setDouble(6,quakelist.get(j).getepicenter_depth());
                preStmt.setString(7,quakelist.get(j).getquake_pointname());

                i=preStmt.executeUpdate();
                //ResultSet rs = preStmt.getGeneratedKeys(); //获取结果
                //if (rs.next()) {
                //    autoInckey = rs.getInt(1);//取得ID
                //} else {
                //    // throw an exception from here
                //    autoInckey = autoInckey + 2;
                //}
                System.out.println("insert table：" + TableName+ "ID; "+ autoInckey);
                autoInckey++;//id add+1

                total +=i;
            }
            //cnn.commit();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("插入表失败：" + TableName+ "row number;"+ total);
            return 0;//返回0为执行失败
        }
        System.out.println("插入表成功：" + TableName+ "row number;"+ total);
        return total;//返回影响的行数，1为执行成功
    }

/*
    public int delete(String strDB){
        String sql = "delete from "+strDB+" where (列名)=(值)";
        int i=0;
        Connection conn = getConn(strDB);//此处为通过自己写的方法getConn()获得连接
        try{
           Statement stmt = conn.createStatement();
           i = stmt.executeUpdate(sql);
           stmt.close(); //关闭数据记录集
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("delete value fail from" + strDB);
        }
        System.out.println("delete value ok from" + strDB);
        return i;//如果返回的是1，则执行成功;
    }

    public int update(String strDB){
        int i=0;
        String sql="update (表名) set  (列名1)=?,列明2=? where (列名)=？";//注意要有where条件
        Connection cnn=getConn(strDB);

        try{
            PreparedStatement preStmt =cnn.prepareStatement(sql);
            preStmt.setString(1,(值));
            preStmt.setString(2,(值));//或者：preStmt.setInt(1,值);
            preStmt.setInt(3,(值));
            i=preStmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            System.out.println("update value fail from" + strDB);
        }
        System.out.println("update value ok from" + strDB);
        return i;//返回影响的行数，1为执行成功
    }
*/

/*
    public String select(String strDB){
        String sql = "select * from (表名) where (列名)=(值)";
        Connection cnn = getConn(strDB);//此处为通过自己写的方法getConn()获得连接
        try{
            Statement stmt = cnn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) {
                int m1 = rs.getInt(1);//或者为rs.getString(1)，根据数据库中列的值类型确定，参数为第一列
                String m2 = rs.getString(2);
            }
            //可以将查找到的值写入类，然后返回相应的对象
            stmt.close(); //关闭数据记录集
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return (相应的值的变量);
    }
*/


}
