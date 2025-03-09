package atv2025_02_26.model.exceptions;

public class WithdrawalExceedsLimitException extends Exception{

    public WithdrawalExceedsLimitException(String msg) {
        super(msg);
    }
}
