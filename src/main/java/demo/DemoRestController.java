package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoRestController {

    @Autowired
    private DemoWorkflow demoWorkflow;
    
    @Autowired
    private DemoAggregateRepository demoAggregates;

    @GetMapping("/demo/{id}/process-task-completed/{taskId}")
    public void runDemoWorkflow(
            @PathVariable("id") final String id,
            @PathVariable("taskId") final String taskId) throws Exception {
        
        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            throw new RuntimeException("Not found");
        }
        
        demoWorkflow.completeUserTask(
                demoAggregate.get(),
                taskId);
        
    }

    @GetMapping("/demo/{id}")
    public void runDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {
        
        demoWorkflow.startDemo(id);
        
    }
    
}
