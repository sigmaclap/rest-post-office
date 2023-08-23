package com.example.tracking.entity;

import com.example.mailing.entity.Mailing;
import com.example.postoffice.entity.PostOffice;
import com.example.utills.TrackingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "tracking")
@Schema(description = "Сущность отслеживания отправления")
public class Tracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "mailing_id", nullable = false)
    private Mailing mailing;
    @ManyToOne
    @JoinColumn(name = "post_office_id", nullable = false)
    private PostOffice postOffice;
    @Enumerated(EnumType.STRING)
    private TrackingStatus status = TrackingStatus.NO_DATA;
    @CreationTimestamp
    @Column(name = "created_date", updatable = false)
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tracking tracking = (Tracking) o;
        return Objects.equals(id, tracking.id) && Objects.equals(mailing, tracking.mailing) && Objects.equals(postOffice, tracking.postOffice) && status == tracking.status && Objects.equals(created, tracking.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, mailing, postOffice, status, created);
    }
}
