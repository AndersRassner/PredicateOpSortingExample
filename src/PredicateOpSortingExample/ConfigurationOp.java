package PredicateOpSortingExample;

public class ConfigurationOp {
    static int  CREATED = 1;
    static int  DELETED = 2;
    static int  MODIFIED = 3; 
    static int  MOVED = 4;
    static int  VALUE_SET = 5;
    
    private boolean op_done = false;
    private int type;
    private String path;
    private String value;
    
    ConfigurationOp(int type, String path, String value) {
        this.type = type;
        this.path = path;
        this.value = value;
    }
    
    public boolean getOpDone() {
        return op_done;
    }
    
    public int getOperation() {
        return type;
    }
    
    public String getPath() {
        return path;
    
    }
    public String getValue() {
        return value;
    }
    
    public void setOpDone() {
        op_done = true;
    }
    
    public String toString() {
        return "type: " + getTypeAsString() + " path: " + getPath() + " value: " + getValue();
    }
    public String getTypeAsString() {
        String typeString = "";
        if(type == CREATED) {
            typeString = "CREATED";
        } else if(type == DELETED) {
            typeString = "DELETED";
        } else if(type == MODIFIED) {
            typeString = "MODIFIED";
        } else if(type == MOVED) {
            typeString = "MOVED";
        } else if(type == VALUE_SET) {
            typeString = "VALUE_SET";
        } else {
            typeString = "UNKNOWN TYPE";
        }
        return typeString;  
    }
}
