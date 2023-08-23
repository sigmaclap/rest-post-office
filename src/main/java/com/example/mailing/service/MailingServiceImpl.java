package com.example.mailing.service;

import com.example.exceptions.InvalidDataException;
import com.example.exceptions.MailingNotFoundException;
import com.example.exceptions.PostOfficeNotFoundException;
import com.example.exceptions.TrackingNotFoundException;
import com.example.mailing.MailingRepository;
import com.example.mailing.dto.MailingDto;
import com.example.mailing.entity.Mailing;
import com.example.mailing.mapper.MailingMapper;
import com.example.postoffice.PostOfficeRepository;
import com.example.postoffice.entity.PostOffice;
import com.example.tracking.TrackingRepository;
import com.example.tracking.entity.Tracking;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static com.example.utills.TrackingStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MailingServiceImpl implements MailingService {
    private final MailingRepository mailingRepository;
    private final PostOfficeRepository postOfficeRepository;
    private final TrackingRepository trackingRepository;

    @Override
    public MailingDto createMailing(MailingDto dto) {
        Mailing mailing = MailingMapper.toEntity(dto);
        mailing = mailingRepository.save(mailing);
        return MailingMapper.toDto(mailing);
    }

    @Override
    public Tracking approveArrival(Long mailingId, Long officeId) {
        Mailing mailing = validateMailing(mailingId);
        PostOffice postOffice = validatePostOffice(officeId);
        Tracking tracking = Tracking.builder()
                .mailing(mailing)
                .postOffice(postOffice)
                .status(ARRIVED)
                .build();
        trackingRepository.save(tracking);
        return tracking;
    }

    @Override
    public Tracking approveDeparture(Long mailingId, Long officeId) {
        Mailing mailing = validateMailing(mailingId);
        PostOffice postOffice = validatePostOffice(officeId);
        checkMailingRightStatusInOffice(mailingId, officeId);
        Tracking tracking = Tracking.builder()
                .mailing(mailing)
                .postOffice(postOffice)
                .status(DEPARTURE)
                .build();
        trackingRepository.save(tracking);
        return tracking;
    }

    @Override
    public Tracking completionMailing(Long mailingId) {
        Mailing mailing = validateMailing(mailingId);
        List<Tracking> listTracking = trackingRepository
                .findAllByMailingId(mailingId,
                        PageRequest.of(0, 10, Sort.Direction.DESC, "created")).getContent();
        Tracking lastTracking = listTracking.get(0);
        if (!lastTracking.getStatus().equals(ARRIVED)) {
            throw new InvalidDataException("Invalid tracking status");
        }
        Tracking trackingToComplete = Tracking.builder()
                .mailing(mailing)
                .postOffice(lastTracking.getPostOffice())
                .status(DELIVERED)
                .build();
        trackingRepository.save(trackingToComplete);
        return trackingToComplete;
    }

    private void checkMailingRightStatusInOffice(Long mailingId, Long officeId) {
        boolean isCheckRightStatus = trackingRepository
                .existsByMailingIdAndPostOfficeIdAndStatusNotIn(mailingId, officeId, Set.of(DEPARTURE, DELIVERED));
        if (!isCheckRightStatus) {
            throw new TrackingNotFoundException("Tracking not found");
        }
    }

    private PostOffice validatePostOffice(Long officeId) {
        return postOfficeRepository.findById(officeId)
                .orElseThrow(() -> new PostOfficeNotFoundException("PostOffice not found"));
    }

    private Mailing validateMailing(Long mailingId) {
        return mailingRepository.findById(mailingId)
                .orElseThrow(() -> new MailingNotFoundException("Mailing not found"));
    }
}
