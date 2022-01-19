package malangcute.bellytime.bellytimeCustomer.global.auth.controller;


import malangcute.bellytime.bellytimeCustomer.global.auth.RequireLogin;
import malangcute.bellytime.bellytimeCustomer.global.auth.dto.LoginIdRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
public class Login {

    @PostMapping("/login")
    public ResponseEntity<?> loginController(@Valid @RequestBody @RequireLogin LoginIdRequest loginIdRequest){
        loginIdRequest.getId()



    }
}
