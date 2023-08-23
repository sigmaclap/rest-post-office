package com.example.tracking.dto;


import com.example.utills.CommonPatterns;
import com.example.utills.TrackingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Статус почтового отправления")
public class TrackingDto {
    private TrackingStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonPatterns.DATE_FORMAT)
    private LocalDateTime created;
}
