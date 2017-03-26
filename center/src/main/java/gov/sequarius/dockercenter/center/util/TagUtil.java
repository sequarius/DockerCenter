package gov.sequarius.dockercenter.center.util;

import java.util.Random;

/**
 * Created by Sequarius on 2017/3/25.
 */
public class TagUtil {
    private static final int MIN_RANDOM_NUM = 10000;
    private static final int MAX_RANDOM_NUM = 99999;
    public static int gennerateCommandTag(Integer nodeTags){
        Random random=new Random();
        int randomNum = random.nextInt(MAX_RANDOM_NUM) % (MAX_RANDOM_NUM - MIN_RANDOM_NUM + 1) + MIN_RANDOM_NUM;
        return randomNum*10000+nodeTags*1000;
    }
}
