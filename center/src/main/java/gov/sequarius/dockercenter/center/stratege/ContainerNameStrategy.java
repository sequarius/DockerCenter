package gov.sequarius.dockercenter.center.stratege;

/**
 * Created by Sequarius on 2017/5/7.
 */
public interface  ContainerNameStrategy {
     String getNextContainer(String imageName);
}
