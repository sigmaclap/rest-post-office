package com.example.exceptions.handler;

import com.example.utills.CommonPatterns;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Generated
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private String status;
    private String reason;
    private String message;
    @JsonFormat(pattern = CommonPatterns.DATE_FORMAT)
    private LocalDateTime timestamp;
}
