package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> completeUserTask(
            @PathVariable("id") final String id,
            @PathVariable("taskId") final String taskId) throws Exception {
        
        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        String convertedTaskId = taskId;
        if (convertedTaskId.startsWith("0x")) {
            try {
                convertedTaskId = Long.toString(Long.parseLong(convertedTaskId.substring(2), 16));
            } catch (Exception e) {
                throw new RuntimeException("Malformed HEX task id: " + taskId, e);
            }
        }
        
        demoWorkflow.completeUserTask(
                demoAggregate.get(),
                convertedTaskId);
        
        return ResponseEntity.ok().build();
        
    }

    @GetMapping("/request-demo/{id}")
    public void requestDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {
        
        demoWorkflow.requestDemo(id);
        
    }

    @GetMapping("/demo/{id}")
    public void runDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {
        
        demoWorkflow.startDemo(id);
        
    }

    @GetMapping("/continue-demo/{id}")
    public void continueDemoWorkflow(
            @PathVariable("id") final String id) throws Exception {

        final var demoAggregate = demoAggregates.findById(id);
        if (demoAggregate.isEmpty()) {
            throw new RuntimeException("Not found");
        }

        demoWorkflow.continueDemo(demoAggregate.get());
        
    }

}
