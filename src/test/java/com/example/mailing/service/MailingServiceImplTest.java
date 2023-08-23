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
import com.example.utills.TrackingStatus;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailingServiceImplTest {
    @InjectMocks
    MailingServiceImpl mailingService;
    @Mock
    MailingRepository mailingRepository;
    @Mock
    PostOfficeRepository postOfficeRepository;
    @Mock
    TrackingRepository trackingRepository;
    private final EasyRandom generator = new EasyRandom();

    @Test
    void createMailing_whenValidData_ThenSave() {
        MailingDto dto = generator.nextObject(MailingDto.class);
        Mailing mailing = MailingMapper.toEntity(dto);
        when(mailingRepository.save(mailing)).thenReturn(mailing);
        MailingDto expected = MailingMapper.toDto(mailing);

        MailingDto actual = mailingService.createMailing(dto);

        assertEquals(expected.getIndexRecipient(), actual.getIndexRecipient());
        verify(mailingRepository, times(1)).save(mailing);
    }


    @Test
    void approveArrival_whenValidData_thenReturnedTracking() {
        long mailingId = 1L;
        long officeId = 1L;
        Tracking tracking = new Tracking();
        tracking.setMailing(new Mailing());
        tracking.setPostOffice(new PostOffice());
        tracking.setStatus(TrackingStatus.ARRIVED);
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.of(new Mailing()));
        when(postOfficeRepository.findById(officeId)).thenReturn(Optional.of(new PostOffice()));
        when(trackingRepository.save(tracking)).thenReturn(tracking);

        Tracking result = mailingService.approveArrival(mailingId, officeId);

        assertEquals(tracking, result);
        verify(trackingRepository, times(1)).save(tracking);
    }

    @Test
    void testApproveArrival_whenNotFoundMailing_thenReturnedThrows() {
        long mailingId = 1L;
        long officeId = 1L;
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.empty());

        MailingNotFoundException expectedException = new MailingNotFoundException("Mailing not found");

        Exception exception = assertThrows(MailingNotFoundException.class, () -> {
            mailingService.approveArrival(mailingId, officeId);
        });

        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void testApproveArrival_whenNotFoundOffice_thenReturnedThrows() {
        long mailingId = 1L;
        long officeId = 1L;
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.of(new Mailing()));
        when(postOfficeRepository.findById(officeId)).thenReturn(Optional.empty());

        PostOfficeNotFoundException expectedException = new PostOfficeNotFoundException("PostOffice not found");

        Exception exception = assertThrows(PostOfficeNotFoundException.class, () -> {
            mailingService.approveArrival(mailingId, officeId);
        });

        assertEquals(expectedException.getMessage(), exception.getMessage());
    }

    @Test
    void testApproveDeparture_whenInvalidDataTrackingNotFound_thenReturnedThrows() {
        long mailingId = 1L;
        long officeId = 1L;
        Tracking tracking = new Tracking();
        tracking.setMailing(new Mailing());
        tracking.setPostOffice(new PostOffice());
        tracking.setStatus(TrackingStatus.ARRIVED);
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.of(new Mailing()));
        when(postOfficeRepository.findById(officeId)).thenReturn(Optional.of(new PostOffice()));
        when(trackingRepository.existsByMailingIdAndPostOfficeIdAndStatusNotIn(mailingId, officeId,
                Set.of(TrackingStatus.DEPARTURE, TrackingStatus.DELIVERED))).thenReturn(false);

        TrackingNotFoundException expected = new TrackingNotFoundException("Tracking not found");

        Exception exception = assertThrows(TrackingNotFoundException.class,
                () -> mailingService.approveDeparture(mailingId, officeId));

        assertEquals(expected.getMessage(), exception.getMessage());
        verify(trackingRepository, never()).save(tracking);
    }

    @Test
    void testApproveDeparture_whenValidData_thenReturnedTracking() {
        long mailingId = 1L;
        long officeId = 1L;
        Tracking tracking = new Tracking();
        tracking.setMailing(new Mailing());
        tracking.setPostOffice(new PostOffice());
        tracking.setStatus(TrackingStatus.DEPARTURE);
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.of(new Mailing()));
        when(postOfficeRepository.findById(officeId)).thenReturn(Optional.of(new PostOffice()));
        when(trackingRepository.existsByMailingIdAndPostOfficeIdAndStatusNotIn(mailingId, officeId,
                Set.of(TrackingStatus.DEPARTURE, TrackingStatus.DELIVERED))).thenReturn(true);
        when(trackingRepository.save(tracking)).thenReturn(tracking);


        Tracking actual = mailingService.approveDeparture(mailingId, officeId);

        assertEquals(tracking, actual);
        verify(trackingRepository, times(1)).save(tracking);
    }


    @Test
    void completionMailing_whenValidData_Success() {
        Long mailingId = 1L;
        Mailing mailing = new Mailing();
        Tracking lastTracking = new Tracking();
        lastTracking.setMailing(mailing);
        lastTracking.setPostOffice(new PostOffice());
        lastTracking.setStatus(TrackingStatus.ARRIVED);
        List<Tracking> listTracking = new ArrayList<>();
        listTracking.add(lastTracking);
        Page<Tracking> page = new PageImpl<>(listTracking);
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.of(new Mailing()));
        when(trackingRepository.findAllByMailingId(mailingId,
                PageRequest.of(0, 10, Sort.Direction.DESC, "created")))
                .thenReturn(page);

        Tracking actual = mailingService.completionMailing(mailingId);

        assertEquals(mailing, actual.getMailing());
        assertEquals(lastTracking.getPostOffice(), actual.getPostOffice());
        assertEquals(TrackingStatus.DELIVERED, actual.getStatus());

        verify(trackingRepository, times(1)).save(actual);
    }

    @Test
    void completionMailing_whenInvalidStatusTracking_thenReturnedThrows() {
        Long mailingId = 1L;
        Mailing mailing = new Mailing();
        Tracking lastTracking = new Tracking();
        lastTracking.setMailing(mailing);
        lastTracking.setPostOffice(new PostOffice());
        lastTracking.setStatus(TrackingStatus.DEPARTURE);
        List<Tracking> listTracking = new ArrayList<>();
        listTracking.add(lastTracking);
        Page<Tracking> page = new PageImpl<>(listTracking);
        when(mailingRepository.findById(mailingId)).thenReturn(Optional.of(new Mailing()));
        when(trackingRepository.findAllByMailingId(mailingId,
                PageRequest.of(0, 10, Sort.Direction.DESC, "created")))
                .thenReturn(page);

        assertThrows(InvalidDataException.class, () -> mailingService.completionMailing(mailingId));

        verify(trackingRepository, never()).save(lastTracking);
    }
}