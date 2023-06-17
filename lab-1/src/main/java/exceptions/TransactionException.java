package exceptions;

public class TransactionException extends BankException {
    public TransactionException() { }

    public TransactionException(String message)
    {
        super(message);
    }
}
