package cn.itcast.common.util.makehtml;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 
* <p>多线程工具类</p>
* @ClassName: MultiTaskUtils
*
 */
public class MultiTaskUtils {
    ExecutorService e;

    public MultiTaskUtils(int num) {
        e = Executors.newFixedThreadPool(num);
    }


    public void runTask(final List<? extends Callable> tasks) {
        CompletionService ecs = new ExecutorCompletionService(e);
        for (Callable task : tasks) {
            ecs.submit(task);
        }
        //
        for (Callable task : tasks) {
            try {
                Future f = ecs.take();
                if (f != null) {
                    f.get();
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            } catch (ExecutionException e1) {
                e1.printStackTrace();
            }
        }
    }




    public void shutDownPool() {
        e.shutdown();
    }
}
