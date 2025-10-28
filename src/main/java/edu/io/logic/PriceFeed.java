package edu.io.logic;

import edu.io.pubsub.Publisher;
import java.util.concurrent.*;

public class PriceFeed {
    public final Publisher<DataPack> publisher;
    private final DataSource dataSource;

    public PriceFeed() {
        this(new HttpDataSource());
    }

    public PriceFeed(DataSource dataSource) {
        this.dataSource = dataSource;
        this.publisher = new Publisher<>();
    }

    public void refresh() {
        publisher.publish(dataSource.getData());
    }

    public void startScheduler() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(this::refresh, 0, 3, TimeUnit.SECONDS);
    }
}
