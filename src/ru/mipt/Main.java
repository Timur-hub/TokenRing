import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Start Token Ring");
        System.out.println("args[0] - количество нод в Token Ring");
        System.out.println("args[1] - количество токенов");

        launchTokenRing(args[0], args[1]);
    }

    private static void launchTokenRing(String nodes, String tokens) throws InterruptedException {
        PrintWriter out = null;
        PrintWriter out2 = null;
//        try {
//            //out = new PrintWriter(new FileWriter("/Users/eugenegoldyrev/IdeaProjects/NewTokenRing/src/test.txt"));
//            out2 = new PrintWriter(new FileWriter("/Users/eugenegoldyrev/IdeaProjects/NewTokenRing/src/test2.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        int numberOfNodes = Integer.parseInt(nodes);
        int numberOfTokens = Integer.parseInt(tokens);

        Node[] arrayOfNodes = new Node[numberOfNodes];
        fillArrayOfNodes(arrayOfNodes);
        setLinkToNextNode(arrayOfNodes);

        Tokens[] arrayOfTokens = new Tokens[numberOfTokens];
        fillArrayOfTokens(arrayOfTokens);
        //fillFirstQueue(arrayOfNodes[0], arrayOfTokens);

        launcher(arrayOfNodes);

        fillQueues(arrayOfNodes, arrayOfTokens);

//        ПРОГРЕВ
//        Thread.sleep(1000);
//        fillFirstQueue(arrayOfNodes[0], arrayOfTokens);

        Thread.sleep(6000);


        System.out.println("Done");

        for (int i = 0; i < arrayOfNodes.length; i++) {
            System.out.println(arrayOfNodes[i].counterOfToken.get());
        }

//        for (int i = 0; i < arrayOfTokens.length ; i++) {
//            for (int j = 1; j < arrayOfTokens[i].timeStamp.size(); j++) {
//                out2.println(arrayOfTokens[i].timeStamp.get(j) - arrayOfTokens[i].timeStamp.get(j - 1));
//            }
//        }
}

    private static void launcher(Node[] arrayOfNodes) throws InterruptedException {
        for (Node node : arrayOfNodes) {
            Thread t = new Thread(node);
            t.start();
        }
    }

    private static void fillArrayOfTokens(Tokens[] arrayOfTokens) {
        for (int i = 0; i < arrayOfTokens.length; i++) {
            //задаем через конструктор на какую ноду отправляем сообщение
            arrayOfTokens[i] = new Tokens(i);
            arrayOfTokens[i].tokenBody = "token = " + i;
        }
    }


    private static void fillArrayOfNodes(Node[] arrayOfNodes) {
        for (int i = 0; i < arrayOfNodes.length; i++){
            //задаем номер ноды через конструтор и заносим в массив для последующего вызова в потоках
            arrayOfNodes[i] = new Node(i);
        }
    }

    //объединяем ноды в кольцо
    private static void setLinkToNextNode(Node[] arrayOfNodes) {
        for (int i = 0; i < arrayOfNodes.length; i++) {
            if (i + 1 != arrayOfNodes.length) {
                arrayOfNodes[i].setNextNode(arrayOfNodes[i + 1]);
            } else {
                arrayOfNodes[i].setNextNode(arrayOfNodes[0]);
            }
        }
    }

    private static void fillFirstQueue(Node arrayOfNode, Tokens[] arrayOfTokens) throws InterruptedException {
        for (int i = 0; i < arrayOfTokens.length ; i++) {
            arrayOfNode.recieveToken(arrayOfTokens[i]);
        }
    }

    private static void fillQueues(Node[] arrayOfNode, Tokens[] arrayOfTokens) throws InterruptedException {
        for (int j = 0; j < arrayOfNode.length; j++) {
            for (int i = 0; i < arrayOfTokens.length ; i++) {
                arrayOfNode[j].recieveToken(arrayOfTokens[i]);
            }
        }
    }
}
