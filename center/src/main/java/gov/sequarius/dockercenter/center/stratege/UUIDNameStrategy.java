package gov.sequarius.dockercenter.center.stratege;

import java.util.UUID;

/**
 * Created by Sequarius on 2017/5/7.
 */
public class UUIDNameStrategy implements ContainerNameStrategy {
    @Override
    public String getNextContainer(String imageName) {
        return imageName+UUID.fromString(imageName).toString();
    }
}
