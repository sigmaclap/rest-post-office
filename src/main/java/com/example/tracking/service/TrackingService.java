package com.example.tracking.service;

import com.example.tracking.dto.TrackingDto;
import com.example.tracking.entity.Tracking;

import java.util.List;

public interface TrackingService {
    List<Tracking> getInfoByMailingId(Long mailingId);

    TrackingDto getTrackingStatusByMailingId(Long mailingId);
}
