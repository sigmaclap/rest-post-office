package com.example.tracking.service;

import com.example.tracking.TrackingRepository;
import com.example.tracking.dto.TrackingDto;
import com.example.tracking.entity.Tracking;
import com.example.tracking.mapper.TrackingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingServiceImpl implements TrackingService {
    private final TrackingRepository trackingRepository;

    @Override
    public List<Tracking> getInfoByMailingId(Long mailingId) {
        return trackingRepository.findAllByMailingId(mailingId,
                PageRequest.of(0, 10, Sort.Direction.DESC, "created")).getContent();
    }

    @Override
    public TrackingDto getTrackingStatusByMailingId(Long mailingId) {
        List<Tracking> list = trackingRepository.findAllByMailingId(mailingId,
                PageRequest.of(0, 10, Sort.Direction.DESC, "created")).getContent();
        return TrackingMapper.toDto(list.get(0));
    }
}
