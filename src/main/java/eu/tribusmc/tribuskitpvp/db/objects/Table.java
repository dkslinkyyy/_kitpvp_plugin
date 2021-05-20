package eu.tribusmc.tribuskitpvp.db.objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {

    private final String name;

    private final List<Object> dataList;


    public Table(String paramName) {
        this.name = paramName;
        dataList = new ArrayList<>();
    }


    public void print() {
        dataList.forEach(self -> {
            System.out.println(self + " ");
        });
    }

    public void register(Object object) {
        dataList.add(object);
    }

    public void flush() {
         dataList.clear();
    }
    public String getName() {
        return name;
    }
}
