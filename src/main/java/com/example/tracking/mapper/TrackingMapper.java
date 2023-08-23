package com.example.tracking.mapper;

import com.example.tracking.dto.TrackingDto;
import com.example.tracking.entity.Tracking;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TrackingMapper {
    public TrackingDto toDto(Tracking tracking) {
        return TrackingDto.builder()
                .status(tracking.getStatus())
                .created(tracking.getCreated())
                .build();
    }
}
