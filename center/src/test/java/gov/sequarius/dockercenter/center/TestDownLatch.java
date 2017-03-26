package gov.sequarius.dockercenter.center;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sequarius on 2017/3/26.
 */
@Slf4j
public class TestDownLatch {
    @Test
    public void test(){
        final CountDownLatch latch = new CountDownLatch(1);

        Thread asyncTask = new Thread(){

            @Override
            public void run(){
                // do something
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("here");

                latch.countDown();
            }
        };
        asyncTask.start();
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("down");
    }
}
