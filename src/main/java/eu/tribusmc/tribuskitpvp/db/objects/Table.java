package eu.tribusmc.tribuskitpvp.db.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {

    private final String name;

    private final HashMap<String, Object> dataList;


    public Table(String paramName) {
        this.name = paramName;
        dataList = new HashMap<>();
    }


    public void register(String columnName, Object object) {
        dataList.put(columnName, object);
    }

    public void flush() {
         dataList.clear();
    }
    public String getName() {
        return name;
    }
}
