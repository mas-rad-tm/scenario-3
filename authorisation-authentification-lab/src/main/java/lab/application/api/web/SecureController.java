package lab.application.api.web;

import lab.model.Roles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/secure")
public class SecureController {


    private static final Logger logger = LoggerFactory.getLogger(SecureController.class);

    @GetMapping("/test")
    public ResponseEntity<?> test() {

        logger.info("/test secure zone GET");

        return ResponseEntity.ok(
                new ApiResponse(Boolean.TRUE,"Secure zone access identified")
        );
    }

    @GetMapping("/testAdminRole")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> testAdminRole() {

        logger.info("/test secure zone admin role GET");

        return ResponseEntity.ok(
                new ApiResponse(Boolean.TRUE,"Secure zone access with ROLE_ADMIN identified")
        );
    }

    @GetMapping("/testWtithPermission")
    @PreAuthorize("hasAuthority('WRITE_PERMISSION')")
    public ResponseEntity<?> testWritePermission() {

        logger.info("/test secure zone write permission GET");

        return ResponseEntity.ok(
                new ApiResponse(Boolean.TRUE,"Secure zone access with WRITE_PERMISSION identified")
        );
    }
}
