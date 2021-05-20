package eu.tribusmc.tribuskitpvp.db;

public enum DataType {

    VARCHAR("varchar", 0),
    INT("int", 0),
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

    public void setSize(int size) {
        this.size = size;
    }
}
