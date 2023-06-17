package exceptions;

public class BankValidationException extends BankException {
    public BankValidationException() { }

    public BankValidationException(String message)
    {
        super(message);
    }
}
