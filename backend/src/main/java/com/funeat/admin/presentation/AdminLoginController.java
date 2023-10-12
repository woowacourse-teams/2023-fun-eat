package com.funeat.admin.presentation;

import com.funeat.admin.application.AdminChecker;
import com.funeat.admin.domain.AdminAuthInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminLoginController {

    private final AdminChecker adminChecker;

    public AdminLoginController(final AdminChecker adminChecker) {
        this.adminChecker = adminChecker;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody final AdminAuthInfo adminAuthInfo,
                                      final HttpServletRequest request) {
        adminChecker.check(adminAuthInfo);

        request.getSession().setAttribute("authId", adminAuthInfo.getId());
        request.getSession().setAttribute("authKey", adminAuthInfo.getKey());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/logged-check")
    public ResponseEntity<Boolean> validLoggedInAdmin() {
        return ResponseEntity.ok(true);
    }
}
