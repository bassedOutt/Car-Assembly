package program.threadpool;
import program.logFile.Log;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool extends Thread {

    boolean doStop;
    private int poolSize;
    private long delay;
    private ArrayList<Thread> threadList;
    private LinkedBlockingQueue<Runnable> threadsQueue;
    private ArrayList<Integer> availableThreadIndexesList = new ArrayList<>();

    public ThreadPool() {
        this.doStop = false;
        this.delay = 500;
        this.poolSize = 5;
        this.threadsQueue = new LinkedBlockingQueue<>();
        this.threadList = new ArrayList<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            availableThreadIndexesList.add(i);
            threadList.add(null);
        }
    }

    class ThreadTerminatedCommand implements Runnable {

        int terminatedThreadIndex;

        public ThreadTerminatedCommand(int index) {
            terminatedThreadIndex = index;
        }

        @Override
        public void run() {
            try {
                threadList.get(terminatedThreadIndex).join();
                availableThreadIndexesList.add(terminatedThreadIndex);
            } catch (InterruptedException e) {
                doStop = true;
            }
        }
    }

    public void setDoStop() {
        doStop = true;
    }

    public synchronized void addRunnableIntoQueue(Runnable r) {
        synchronized (this.threadsQueue) {
            this.threadsQueue.add(r);
            Log.getObjLog().writeLog("Added runnable into queue");
        }
    }

    public Runnable pollRunnableFromQueue() {
        return this.threadsQueue.poll();
    }

    @Override
    public void run() {
        while (!doStop) {
            Runnable r = pollRunnableFromQueue();
            if (r != null) {
                while (availableThreadIndexesList.isEmpty()) {
                    synchronized (Thread.currentThread()) {
                        try {
                            Thread.currentThread().wait(delay);
                        } catch (InterruptedException e) {
                            doStop = true;
                        }
                    }
                }

                int indexOfAvailableThread = availableThreadIndexesList.get(0);

                threadList.set(indexOfAvailableThread, new Thread(r));
                threadList.get(indexOfAvailableThread).start();

                Thread waitThread = new Thread(new ThreadTerminatedCommand(indexOfAvailableThread));
                waitThread.start();

            } else {
                synchronized (Thread.currentThread()) {
                    try {
                        Thread.currentThread().wait(delay);
                    } catch (InterruptedException e) {
                        doStop = true;
                    }
                }
            }

        }
    }
}
