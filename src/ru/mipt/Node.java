import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Node implements Runnable{
    private Node nextNode;
    private final Queue<Tokens> queue;
    private int nodeNumber;
    static List<Long> listOfTimes = new ArrayList<>();
    AtomicInteger counterOfToken = new AtomicInteger();
    boolean work = true;

    public Node(int nodeNumber) {
        this.nodeNumber = nodeNumber;
        this.queue = new ConcurrentLinkedQueue<>();
        //this.counter = new AtomicInteger(0);
        //this.queue = new ArrayBlockingQueue<>(3);
    }

    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    //добавление сообщения в очередь
    public void recieveToken(Tokens token) {
        this.queue.add(token);
    }

    //основная часть
    @Override
    public void run() {
        while (true) {
            if((!this.queue.isEmpty())){
                this.readToken();
            }
        }
    }

    private void readToken() {
        Tokens token = getToken();
        throughputSpeed();
        if (token.destinationId != nodeNumber) {
            nextNode.recieveToken(token);
        } else {
            if(!token.isRead.get()){
                token.isRead.set(true);
            } else {
                token.timeStamp.add(System.nanoTime());
            }
            nextNode.recieveToken(token);
        }
    }

    private void throughputSpeed() {
        counterOfToken.getAndIncrement();
    }

    private Tokens getToken() {
        return this.queue.poll();
    }
}
