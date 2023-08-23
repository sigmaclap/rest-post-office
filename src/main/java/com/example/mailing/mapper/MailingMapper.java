package com.example.mailing.mapper;

import com.example.mailing.dto.MailingDto;
import com.example.mailing.entity.Mailing;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MailingMapper {
    public Mailing toEntity(MailingDto dto) {
        return Mailing.builder()
                .id(dto.getId())
                .type(dto.getType())
                .addressRecipient(dto.getAddressRecipient())
                .nameRecipient(dto.getNameRecipient())
                .indexRecipient(dto.getIndexRecipient())
                .build();
    }

    public MailingDto toDto(Mailing mailing) {
        return MailingDto.builder()
                .id(mailing.getId())
                .type(mailing.getType())
                .addressRecipient(mailing.getAddressRecipient())
                .nameRecipient(mailing.getNameRecipient())
                .indexRecipient(mailing.getIndexRecipient())
                .build();

    }
}
