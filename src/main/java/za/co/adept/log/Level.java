package za.co.adept.log;

/**
 * A logging level 
 * @author gideon
 */
public enum Level {
    
    
    CRITICAL(0), ERROR(1), WARNING(2), INFO(3), DEBUG(4);
    
    public int code() {
        return code;
    }
    
    private Level(int code) {
        this.code = code;
    }

    private final int code;
    
}
