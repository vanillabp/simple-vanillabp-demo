package demo;

import io.vanillabp.spi.process.ProcessService;
import io.vanillabp.spi.service.WorkflowService;
import io.vanillabp.spi.service.WorkflowTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@WorkflowService(workflowAggregateClass = DemoAggregate.class)
@Service
public class DemoWorkflow {

    private static final Logger logger = LoggerFactory.getLogger(DemoWorkflow.class);
    
    @Autowired
    private ProcessService<DemoAggregate> processService;
    
    public void startDemo(final String id) throws Exception {
        
        var demo = new DemoAggregate();
        demo.setId(id);
        processService.startWorkflow(demo);
        
    }
    
    @WorkflowTask
    public void processTask(final DemoAggregate demo) {
        
        logger.info("Run: {}", demo.getId());
        demo.setSuccess(
                Integer.parseInt(demo.getId()) % 2 == 0);
        
    }
    
    @WorkflowTask(taskDefinition = "logError")
    public void logErrorOnFailure() {
        
        logger.info("error");
        
    }
    
}
