package eu.tribusmc.tribuskitpvp.db;

import com.avaje.ebean.validation.NotNull;
import eu.tribusmc.tribuskitpvp.db.objects.Table;
import pro.husk.mysql.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLFactory {

    private final MySQL mySQL;


    public SQLFactory(MySQL mySQL) {
        this.mySQL = mySQL;
    }


    @NotNull
    public void createTable(String table, DataColumn[] dataColumns) {
        String query = "CREATE TABLE " + table + "(";

        StringBuilder str = new StringBuilder();

        for (DataColumn dataColumn : dataColumns) {
            str.append(dataColumn.getInternalName()).append(" ").append(dataColumn.getDataType().getName()).append("(").append(dataColumn.getDataType().getSize()).append("),");
        }

        query = query + removeLastCharRegex(str.toString()) + ");";

        System.out.println(query);

        try {
            int resultCode = mySQL.update(query);
            System.out.println("Created " + table + " table succesfully, result code: " + resultCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();
    }

    public Table fetchTable(String table) {
        Table t = new Table(table);

        try {
            mySQL.query("SELECT * FROM " + table, result -> {
                while (result.next()) {
                    for (int i = 1; i < result.getMetaData().getColumnCount() + 1; i++) {
                        t.register(result.getObject(i));
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return t;
    }

    private String removeLastCharRegex(String s) {
        return (s == null) ? null : s.replaceAll(".$", "");
    }

}
