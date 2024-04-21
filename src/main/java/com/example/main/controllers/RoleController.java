package com.example.main.controllers;

import com.example.main.responses.ResponseObject;
import com.example.main.services.role.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/roles")
@RestController
public class RoleController {
    private final IRoleService roleService;

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("")
    public ResponseEntity<ResponseObject> getRoles(){
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Get roles successfully !")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .data(roleService.getRoles())
                        .build()
        );
    }


}
