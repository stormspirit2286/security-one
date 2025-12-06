package vn.duynv.secutityone.exception;

public class NotFoundException extends ApiException{
    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
