package eu.tribusmc.tribuskitpvp.db;

import com.avaje.ebean.validation.NotNull;
import eu.tribusmc.tribuskitpvp.db.objects.Table;
import pro.husk.mysql.MySQL;

import java.sql.*;
import java.util.concurrent.atomic.AtomicReference;

public class SQLFactory {

    private final MySQL mySQL;

    public SQLFactory(MySQL mySQL) {
        this.mySQL = mySQL;
    }


    @NotNull
    public void createTable(String table, DataColumn[] dataColumns) {
        String query = "CREATE TABLE " + table + "(";

        StringBuilder str = new StringBuilder();

        for(DataColumn dataColumn : dataColumns) {
            str.append(dataColumn.getInternalName()).append(" ").append(dataColumn.getDataType().getName()).
                    append("(").append(dataColumn.getDataType().getSize()).append("),");
        }

        query = query + removeLastCharRegex(str.toString()) + ");";

        try {
            int resultCode = mySQL.update(query);
            System.out.println("Created " + table + " table succesfully, result code: " + resultCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void readColumn(String table, String column) {
        String query = "SELECT * FROM "+ table +" WHERE 1";

        try {
            mySQL.query(query, result -> {
                if(result.next()) {
                    System.out.println(result.getObject(result.findColumn(column)));

                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Object readRow(String table, String column) {
        String query = "SELECT * FROM "+ table +" WHERE 1";

        AtomicReference<Object> object = null;

        try {
            mySQL.query(query, result -> {
                assert false;
                object.set(result.getObject(result.findColumn(column)));

            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assert false;
        return object.get();
    }

    public Table fetchTable(String table) {
        return null;
    }




    public static String removeLastCharRegex(String s) {
        return (s == null) ? null : s.replaceAll(".$", "");
    }

}
