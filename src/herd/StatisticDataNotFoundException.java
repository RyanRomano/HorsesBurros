package herd;

import java.io.IOException;

/**
 * Created by ryan on 11/17/16.
 */
public class StatisticDataNotFoundException extends Exception {
    public StatisticDataNotFoundException(){
        super("File not found.");
    }

    public StatisticDataNotFoundException(Throwable throwable){
        super(throwable);
    }

    public StatisticDataNotFoundException(String message, Throwable throwable){
        super (message, throwable);
    }

}
