package demo;

import io.vanillabp.springboot.adapter.BpDeploymentConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "demo")
public class DemoProperties implements BpDeploymentConfiguration {

    private String processesLocation;
    
    @Override
    public String getProcessesLocation() {
        return processesLocation;
    }

    public void setProcessesLocation(String processesLocation) {
        this.processesLocation = processesLocation;
    }
    
}
