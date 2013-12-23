package db.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StdinReader implements IReader {
    BufferedReader bufferedReader;

    public StdinReader() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public String getRawCommand() {
        try {
            return bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
