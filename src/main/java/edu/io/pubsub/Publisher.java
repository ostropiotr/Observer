package edu.io.pubsub;

import java.util.ArrayList;
import java.util.List;

public class Publisher<T> {
    private final List<Subscriber<T>> subscribers = new ArrayList<>();

    public void subscribe(Subscriber<T> subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber<T> subscriber) {
        subscribers.remove(subscriber);
    }

    public void publish(T data) {
        for (Subscriber<T> subscriber : subscribers) {
            subscriber.update(data);
        }
    }
}