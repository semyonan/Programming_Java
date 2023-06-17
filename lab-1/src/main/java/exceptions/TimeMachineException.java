package exceptions;

public class TimeMachineException extends BankException {
    public TimeMachineException() { }

    public TimeMachineException(String message)
    {
        super(message);
    }
}
