package com.myutils.workTest;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ImageExistTest {

    public static void main(String[] args)throws Throwable{
        try {
            int ColumnCount;
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://192.168.99.42:3306/ycbp_zs?serverTimezone=Asia/Shanghai";
            String user = "root";
            String password = "123456";
            Class.forName (driver);
            Connection conn = (Connection) DriverManager.getConnection ( url, user, password );
            if (!conn.isClosed ()) {
                System.out.println("数据库连接成功!");
                String sqls = "SELECT  b.user_name,a.FILE_NAME,a.FILE_PATH,c.unit_name FROM s_attach_list a,s_user b,s_unit c " +
                        "WHERE a.transaction_id = b.user_id  AND transaction_type = 'sp_user_data'  AND c.UNIT_ID=b.UNIT_ID ";
                PreparedStatement ps = conn.prepareStatement ( sqls );
                ResultSet rs = ps.executeQuery ();
                List list = new ArrayList<String> ();

                ResultSetMetaData rsmd = rs.getMetaData ();
                Map<String,Object> Data = new HashMap<String,Object>();
                Map<String,Object> Data1 = new HashMap<String,Object>();

                while (rs.next ()) {
                    ColumnCount = rsmd.getColumnCount ();
                    for (int i = 1; i <= ColumnCount; i++) {
                        String key=rsmd.getColumnName(i);
                        Object value = rs.getObject(i);
                        Data.put(key,value);
                    }
                    if(Data.get("FILE_NAME").equals("")) {
                        Data1.put("user_name", Data.get("user_name"));
                        Data1.put("unit_name", Data.get("unit_name"));
                    }
                }
                list.add(Data1);
                System.out.println ( Data1 );
                if(Data1.size()>0) {
                    try {
                        String line = System.getProperty("line.separator");
                        StringBuffer str = new StringBuffer();
                        FileWriter fw = new FileWriter("D:\\imageIsExist.txt", true);
                        Set set = Data1.entrySet();
                        Iterator iter = set.iterator();
                        while(iter.hasNext()){
                            Map.Entry entry = (Map.Entry)iter.next();
                            str.append(entry.getKey()+" : "+entry.getValue()).append(line);
                        }
                        fw.write(str.toString());
                        System.out.println ("写入成功!");
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                ps.close ();
                conn.close ();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace ();
        } catch (SQLException e) {
            e.printStackTrace ();
        }
    }
}
