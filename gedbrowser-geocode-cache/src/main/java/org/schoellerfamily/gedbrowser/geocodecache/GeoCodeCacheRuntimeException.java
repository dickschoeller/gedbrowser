/**
 * 
 */
package org.schoellerfamily.gedbrowser.geocodecache;

/**
 * @author Dick Schoeller
 */
public class GeoCodeCacheRuntimeException extends RuntimeException {
    /** */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public GeoCodeCacheRuntimeException() {
    }

    /**
     * @param arg0
     */
    public GeoCodeCacheRuntimeException(String arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     */
    public GeoCodeCacheRuntimeException(Throwable arg0) {
        super(arg0);
    }

    /**
     * @param arg0
     * @param arg1
     */
    public GeoCodeCacheRuntimeException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * @param arg0
     * @param arg1
     * @param arg2
     * @param arg3
     */
    public GeoCodeCacheRuntimeException(String arg0, Throwable arg1, boolean arg2,
            boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }
}
