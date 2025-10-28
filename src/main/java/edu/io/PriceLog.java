package edu.io;
import edu.io.logic.DataPack;
import edu.io.pubsub.Subscriber;
import java.io.*;

public class PriceLog implements Subscriber {
    @Override
    public void update(Object data) {
        DataPack dataPack = (DataPack) data;
        try (PrintWriter out = new PrintWriter(new FileWriter("price.log", true))) {
            out.printf("Price: %.2f, Timestamp: %d%n", dataPack.price(), dataPack.timestamp());
        } catch (IOException e) {

        }
    }
}