package com.example.mailing.service;

import com.example.mailing.dto.MailingDto;
import com.example.tracking.entity.Tracking;

public interface MailingService {
    MailingDto createMailing(MailingDto dto);

    Tracking approveArrival(Long mailingId, Long officeId);

    Tracking approveDeparture(Long mailingId, Long officeId);

    Tracking completionMailing(Long mailingId);
}
