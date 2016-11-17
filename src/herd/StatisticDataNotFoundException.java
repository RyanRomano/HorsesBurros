package herd;

import java.io.FileNotFoundException;

/**
 * Created by ryan on 11/17/16.
 */
public class StatisticDataNotFoundException extends Exception {

    public StatisticDataNotFoundException(){

    }

    public StatisticDataNotFoundException(String s){
        super(s);
    }
}
