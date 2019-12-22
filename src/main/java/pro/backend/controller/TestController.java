package pro.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import pro.backend.model.RoleNames;


@RestController
@RequestMapping("/test")
@Secured({RoleNames.ROLE_BOSS, RoleNames.ROLE_EMPLOYEE, RoleNames.ROLE_CLIENT})
public class TestController {

    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok().body("Yay");
    }
}