package arithmetic;

/**
 * 两个线程交替打印奇偶数
 */
public class ThreadProject {

    public static Object lock = new Object();
    public static int count = 0;

    public static void main(String[] args) {

        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if (count % 2 == 0) {
                            System.out.println("A线程" + count++);
                            lock.notify();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                while (count < 100) {
                    synchronized (lock) {
                        if (count % 2 == 1) {
                            System.out.println("B线程" + count++);
                            lock.notify();
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
            }
        });

        threadA.start();
        threadB.start();

    }


}
