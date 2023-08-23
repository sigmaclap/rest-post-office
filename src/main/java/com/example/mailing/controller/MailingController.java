package com.example.mailing.controller;

import com.example.mailing.dto.MailingDto;
import com.example.mailing.service.MailingService;
import com.example.tracking.entity.Tracking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mailing")
@RequiredArgsConstructor
@Tag(name = "Почтовое отправление")
public class MailingController {
    private final MailingService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Регистрация почтового отправления"
    )
    public MailingDto createMailing(@Valid @RequestBody MailingDto dto) {
        return service.createMailing(dto);
    }

    @PatchMapping("/approve/{mailingId}/office/{officeId}")
    @Operation(
            summary = "Подтверждение прибытия в промежуточное почтовое отделение"
    )
    public Tracking approveArrival(@PathVariable Long mailingId,
                                   @PathVariable Long officeId) {
        return service.approveArrival(mailingId, officeId);
    }

    @PatchMapping("/departure/{mailingId}/office/{officeId}")
    @Operation(
            summary = "Убытие в промежуточном почтовом отделение"
    )
    public Tracking approveDeparture(@PathVariable Long mailingId,
                                     @PathVariable Long officeId) {
        return service.approveDeparture(mailingId, officeId);
    }

    @PatchMapping("/completion/{mailingId}")
    @Operation(
            summary = "Получение адресатом"
    )
    public Tracking completionMailing(@PathVariable Long mailingId) {
        return service.completionMailing(mailingId);
    }
}
