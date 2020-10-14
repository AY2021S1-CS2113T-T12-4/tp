package seedu.duke.command.repeatExceptions;

public class WrongNumberOfArgumentsException extends Exception{

    private String message;

    public WrongNumberOfArgumentsException(String errorMessage) {
        message = errorMessage;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}