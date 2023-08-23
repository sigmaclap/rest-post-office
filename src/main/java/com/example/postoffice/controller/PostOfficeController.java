package com.example.postoffice.controller;

import com.example.postoffice.entity.PostOffice;
import com.example.postoffice.service.PostOfficeSerivce;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/office")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Почтовое отделение")
public class PostOfficeController {
    private final PostOfficeSerivce postOfficeSerivce;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Создание почтового отделения"
    )
    public PostOffice createPostOffice(@Valid @RequestBody PostOffice postOffice) {
        return postOfficeSerivce.createPostOffice(postOffice);
    }

}
