package DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 维护爬虫模块爬取到的数据，只使用一个数据表，用一个标志列来标记爬取数据所属task类型
 */
public class DBManager {
    private static DBManager instance;

    private DBManager() {
        int tryTimes = 0;
        while (conn == null) {
            tryTimes ++;
            connectDB("root", "sql123");
            if (tryTimes == 3) {
                System.out.println("cannot connect to database");
                break;
            }
            if (conn == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static DBManager getDBManager() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }


    private Connection conn;

    private boolean connectDB(String username, String password){
        String URL = "jdbc:mysql://localhost:3306/bilibilian?useSSL=false&serverTimezone=Asia/Shanghai&autoReconnect=true";
        try {
            conn = DriverManager.getConnection(URL, username, password);
        } catch (SQLException throwables) {
            System.out.println("[ERROR] cannot connect database");
            return false;
        }
        return true;
    }


    public void saveDataLine(DataLine dataline) {
        Statement stmt = null;
        try {
            stmt = conn.createStatement();

            String prefix = DataType.name[dataline.type];
            String sql = "insert into bilibilian.data_lines values(" +
                    "'" +prefix + dataline.id + "' ," +
                    dataline.type + "," +
                    "'" + dataline.data + "'" +
                    ")";
            stmt.executeUpdate(sql);
        } catch (SQLException throwables) {
            System.out.println("Save data line error");
            throwables.printStackTrace();
        }
    }


    public String[] readDataLines() {
        List<String> resultList = new ArrayList<>();

        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String sql = "select * from bilibilian.data_lines";
            ResultSet rs =  stmt.executeQuery(sql);

            while (rs.next()) {
                int type = rs.getInt("type");
                String id = rs.getString("id");
                String data = rs.getString("data");

                resultList.add(String.format("%s::%d::%s", id, type, data));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resultList.toArray(new String[0]);
    }

    public void setupDB() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("delete from bilibilian.data_lines");
            System.out.println("[warning] previous data stored in database has been deleted");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

