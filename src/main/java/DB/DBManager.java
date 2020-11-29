package DB;

import java.sql.*;

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
        String URL = "jdbc:mysql://localhost:3306/bilibilian?useSSL=false&serverTimezone=Asia/Shanghai";
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

    public void setupDB() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("delete from bilibilian.data_lines");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

