package gov.sequarius.dockercenter.common.exception;

/**
 * Created by Sequarius on 2017/3/20.
 */
public class CommonException extends RuntimeException {
    public CommonException() {
    }

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String format,Object ... args) {
        super(String.format(format,args));
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonException(Throwable cause) {
        super(cause);
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
