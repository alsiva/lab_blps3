//package vasilkov.labbpls2.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import vasilkov.labbpls2.security.JwtAuthentication;
//import vasilkov.labbpls2.service.AuthService;
//
//@RestController
//@RequestMapping("api")
//@RequiredArgsConstructor
//public class Controller {
//
//    private final AuthService authService;
//
//    @GetMapping("/user")
//    public ResponseEntity<String> helloUser() {
//        final JwtAuthentication authInfo = authService.getAuthInfo();
//        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
//    }
//
//    @GetMapping("/admin")
//    public ResponseEntity<String> helloAdmin() {
//        final JwtAuthentication authInfo = authService.getAuthInfo();
//        return ResponseEntity.ok("Hello admin " + authInfo.getPrincipal() + "!");
//    }
//
//}