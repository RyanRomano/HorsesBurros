package herd;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by ryan on 11/17/16.
 */
public class TestDriver {
    public static void main(String[] args) {
        Random r = new Random();
        ArrayList<String> strings = new ArrayList<>();
        strings.add("c");
        strings.add("a");
        strings.add("t");
        System.out.println(r.nextInt(3));
    }
}
