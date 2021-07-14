package utils;

/**
 *
 * libs的配置类.
 * @author wangheng
 */
public class LibraryConfig {
    
    private LibraryConfig(){
        
    }
    /**
     * 
     * 仅仅为了单例而存在.
     * @author wangheng
     */
    private static final class Singleton{
        private static final LibraryConfig INSTANCE = new LibraryConfig();
    }
    /**
     * 
     * getInstance:得到Configuration的单例对象. <br/>
     *
     * @return 得到単例
     */
    public static LibraryConfig getInstance(){
        return Singleton.INSTANCE;
    }
    
    /** 是否是Debug模式 **/
    private boolean isDebug = true;
    
    public boolean isDebug() {
        return isDebug;
    }

    
    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

}
