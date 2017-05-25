package gov.sequarius.dockercenter.center.stratege;

import java.util.Set;

/**
 * Created by Sequarius on 2017/5/7.
 */
public class IncreaseIDNameStrategy implements ContainerNameStrategy {
    private Set<Integer> idSets;

    public IncreaseIDNameStrategy(Set<Integer> idSets) {
        this.idSets = idSets;
    }

    @Override
    public String getNextContainer(String imageName) {
        idSets.add(idSets.size());
        return imageName+idSets.size();
    }
}
