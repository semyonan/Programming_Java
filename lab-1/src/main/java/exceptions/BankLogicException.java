package exceptions;

public class BankLogicException extends BankException {
    public BankLogicException() { }

    public BankLogicException(String message)
    {
        super(message);
    }
}
