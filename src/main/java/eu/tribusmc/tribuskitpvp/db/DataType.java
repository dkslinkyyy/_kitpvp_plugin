package eu.tribusmc.tribuskitpvp.db;

public enum DataType {

    VARCHAR("varchar", 255),
    INT("int", 255),
    FLOAT("float", 25),
    BYTE("byte", 0);


    private String name;
    private int size;

    DataType(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public DataType setSize(int size) {
        this.size = size;
        return this;
    }
}
