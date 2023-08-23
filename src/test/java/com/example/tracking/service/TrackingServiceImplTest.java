package com.example.tracking.service;

import com.example.tracking.TrackingRepository;
import com.example.tracking.dto.TrackingDto;
import com.example.tracking.entity.Tracking;
import com.example.tracking.mapper.TrackingMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackingServiceImplTest {
    @InjectMocks
    TrackingServiceImpl trackingService;
    @Mock
    TrackingRepository trackingRepository;

    @Test
    void testGetInfoByMailingId_whenValidData_thenReturnedSuccess() {
        long mailingId = 1L;
        List<Tracking> list = List.of(new Tracking());
        when(trackingRepository.findAllByMailingId(mailingId,
                PageRequest.of(0, 10, Sort.Direction.DESC, "created")))
                .thenReturn(new PageImpl<>(list));

        List<Tracking> actual = trackingService.getInfoByMailingId(mailingId);

        assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void testGetTrackingStatusByMailingId_whenValidData_ThenReturnedSuccess() {
        long mailingId = 1L;
        List<Tracking> list = List.of(new Tracking());
        when(trackingRepository.findAllByMailingId(mailingId,
                PageRequest.of(0, 10, Sort.Direction.DESC, "created")))
                .thenReturn(new PageImpl<>(list));
        TrackingDto expected = TrackingMapper.toDto(list.get(0));

        TrackingDto actual = trackingService.getTrackingStatusByMailingId(mailingId);

        assertEquals(expected, actual);
    }
}