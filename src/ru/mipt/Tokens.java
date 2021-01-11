import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Tokens {
    String tokenBody;
    int destinationId;
    List<Long> timeStamp = new ArrayList<>();

    AtomicBoolean isRead = new AtomicBoolean(false);

    public Tokens(int destinationId) {
        this.destinationId = destinationId;
        this.timeStamp.add((long) 0);
    }
}
