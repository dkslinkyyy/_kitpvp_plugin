package eu.tribusmc.tribuskitpvp.db;

public class DataColumn {

    private final String internalName;
    private final DataType dataType;

    public DataColumn(String paramInternalName, DataType paramDataType) {
        this.internalName = paramInternalName;
        this.dataType = paramDataType;
    }

    public String getInternalName() {
        return internalName;
    }

    public DataType getDataType() {
        return dataType;
    }
}
