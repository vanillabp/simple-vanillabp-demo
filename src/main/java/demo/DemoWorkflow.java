package demo;

import io.vanillabp.spi.process.ProcessService;
import io.vanillabp.spi.service.TaskId;
import io.vanillabp.spi.service.WorkflowService;
import io.vanillabp.spi.service.WorkflowTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@WorkflowService(workflowAggregateClass = DemoAggregate.class)
public class DemoWorkflow {

    private static final Logger logger = LoggerFactory.getLogger(DemoWorkflow.class);
    
    @Autowired
    private ProcessService<DemoAggregate> processService;
        
    public void startDemo(final String id) throws Exception {
        
        var demo = new DemoAggregate();
        demo.setId(id);
        demo = processService.startWorkflow(demo);

        logger.info("Run: {}", demo.getId());

    }
    
    public void requestDemo(final String id) throws Exception {
        
        var demo = new DemoAggregate();
        demo.setId(id);
        demo = processService.correlateMessage(demo, "Start");

        logger.info("Run: {}", demo.getId());

    }
    
    public void continueDemo(final DemoAggregate demo) throws Exception {
        
        processService.correlateMessage(demo, "Continue");

        logger.info("Continue: {}", demo.getId());

    }
    
    @WorkflowTask
    public void processTask(
            final DemoAggregate demo,
            @TaskId final String taskId) {
        
        logger.info("UserTask ID: '{}'", taskId);
        
    }
    
    public void completeUserTask(
            final DemoAggregate demo,
            final String taskId) {

        demo.setSuccess(
                Integer.parseInt(demo.getId()) % 2 == 0);
        
        processService.completeUserTask(demo, taskId);
        
    }
    
    @WorkflowTask(taskDefinition = "logError")
    public void logErrorOnFailure() {
        
        logger.info("error");
        
    }
    
}
