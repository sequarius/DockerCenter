package gov.sequarius.dockercenter.center.domain;

/**
 * Created by Sequarius on 2017/4/20.
 */
public enum  JobStatus {
    RUNNING("RUNNING"),STOP("STOP"),CREATE("CREATE"),UNKNOWN("UNKNOWN");
    private String name;
    JobStatus(String name) {
        this.name=name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
