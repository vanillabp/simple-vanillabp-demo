package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoRestController {

    private static final Logger logger = LoggerFactory.getLogger(DemoRestController.class);
    
    @Autowired
    private DemoWorkflow demoWorkflow;
    
    @GetMapping("/demo/{id}")
    public void runDemoWorkflow(@PathVariable("id") final String id) throws Exception {
        logger.info("Run demo");
        demoWorkflow.startDemo(id);
    }
    
}
