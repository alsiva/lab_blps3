package vasilkov.labbpls2.controller;


import jakarta.validation.Valid;
import nu.xom.ParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vasilkov.labbpls2.api.GrantRequest;
import vasilkov.labbpls2.service.OrderService;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin")
public class AdministratorController {

    @Autowired
    OrderService orderService;

    @PostMapping(value = "/grant")
    public ResponseEntity<?> getAdvertisementById(@Valid @RequestBody GrantRequest grantRequestDto) throws ParsingException, IOException {

        orderService.grantOrderWithEmail(grantRequestDto);

        return ResponseEntity.ok("Order status changed successfully!");
    }
}

