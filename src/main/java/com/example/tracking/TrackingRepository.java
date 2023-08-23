package com.example.tracking;

import com.example.tracking.entity.Tracking;
import com.example.utills.TrackingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface TrackingRepository extends JpaRepository<Tracking, Long> {
    boolean existsByMailingIdAndPostOfficeIdAndStatusNotIn(Long mailingId, Long postOfficeId, Set<TrackingStatus> status);

    Page<Tracking> findAllByMailingId(Long mailingId, Pageable pageable);
}