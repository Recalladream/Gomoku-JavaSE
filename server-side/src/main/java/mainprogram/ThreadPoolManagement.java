package mainprogram;

import java.util.concurrent.*;

public class ThreadPoolManagement {
    private static ThreadPoolExecutor threadPool;

    public ThreadPoolManagement(){
        threadPool=new ThreadPoolExecutor(
                100,
                10000,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

    public void submit(Runnable thread){
        threadPool.submit(thread);
    }
}
