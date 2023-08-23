package com.example.mailing.dto;

import com.example.utills.MailingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailingDto {
    private Long id;
    @NotNull
    private MailingType type;
    @NotBlank
    private String indexRecipient;
    @NotBlank
    private String addressRecipient;
    @NotBlank
    private String nameRecipient;
}
