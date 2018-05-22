package lab.application.api.web.rest;

import lab.application.api.web.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private static final Logger logger = LoggerFactory.getLogger(PublicController.class);

    @GetMapping("/test")
    public ResponseEntity<?> authenticateUser() {


        logger.info("/test public zone GET");

        return ResponseEntity.ok(new ApiResponse(true,"Public zone access free"));
    }
}
