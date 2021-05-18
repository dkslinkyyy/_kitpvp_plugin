package eu.tribusmc.tribuskitpvp.db;

import com.avaje.ebean.validation.NotNull;
import pro.husk.mysql.MySQL;

import java.sql.*;

public class SQLFactory {

    private final MySQL mySQL;


    public SQLFactory(MySQL mySQL) throws SQLException {
        if(mySQL.getConnection() == null) throw new SQLException("Could not connect..");
        this.mySQL = mySQL;
    }


    @NotNull
    public void createTable(String table, DataColumn[] dataColumns) {
        String query = "CREATE TABLE " + table + "(";

        StringBuilder str = new StringBuilder();

        for(DataColumn dataColumn : dataColumns) {
            str.append(dataColumn.getInternalName()).append(" ").append(dataColumn.getDataType().getName()).append("(").append(dataColumn.getDataType().getSize()).append("),");
        }

        query = query + removeLastCharRegex(str.toString()) + ");";

        try {
            int resultCode = mySQL.update(query);
            System.out.println("Created " + table + " table succesfully, result code: " + resultCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void readColumn(String table, String column) {
        String query = "SELECT * FROM "+ table +" WHERE 1";

        try {
            mySQL.query(query, result -> {
                if(result.next()) {
                    System.out.println(result.getFloat(result.findColumn(column)));

                }
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static String removeLastCharRegex(String s) {
        return (s == null) ? null : s.replaceAll(".$", "");
    }

}
