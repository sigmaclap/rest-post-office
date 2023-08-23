package com.example.tracking.controller;

import com.example.tracking.dto.TrackingDto;
import com.example.tracking.entity.Tracking;
import com.example.tracking.service.TrackingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracking")
@RequiredArgsConstructor
@Tag(name = "Отслеживание почтового отправления")
public class TrackingController {
    private final TrackingService trackingService;

    @GetMapping("/{mailingId}")
    @Operation(
            summary = "Полная история движения почтового отправления отсортированная по последнему событию"
    )
    public List<Tracking> getInfoByMailingId(@PathVariable Long mailingId) {
        return trackingService.getInfoByMailingId(mailingId);
    }

    @GetMapping("/{mailingId}/status")
    @Operation(
            summary = "Просмотр статуса почтового отправления"
    )
    public TrackingDto getTrackingStatusByMailingId(@PathVariable Long mailingId) {
        return trackingService.getTrackingStatusByMailingId(mailingId);
    }
}
