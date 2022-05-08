package aie.plotter.guiplotter.excepations;

public class CompilerException extends Exception{

    public CompilerException() {
    }

    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, Throwable cause) {
        super(message, cause);
    }
}
