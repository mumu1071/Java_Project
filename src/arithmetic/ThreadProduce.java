package arithmetic;

import java.util.ArrayList;

/**
 * 生产者消费者模式
 */
public class ThreadProduce {


    public static ArrayList arrayList = new ArrayList();

    public static void main(String[] args) {



        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

        Thread Producer = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });


    }
}
