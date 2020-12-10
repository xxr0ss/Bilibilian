package DB.Visualization;

import DB.DBManager;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

import java.util.ArrayList;
import java.util.List;

public class demo {
    public static void demoFunc() {
        DBManager dbManager = DBManager.getDBManager();

        String[] datalines = dbManager.readDataLine();

        List<String> trimedDatalines = new ArrayList<>();
        for( String s: datalines) {
            String t;
            if (s.length() > 20) {
                t = s.substring(0, 16) + " ...";
            } else {
                t = s;
            }
            trimedDatalines.add(t);
        }

        Table t = Table.create("raw data")
                .addColumns(StringColumn.create("data", datalines));
        if (t.rowCount() == 0) {
            System.out.println("No data right now");
        } else {
            System.out.println(t.first(t.rowCount()));
        }
    }
}
