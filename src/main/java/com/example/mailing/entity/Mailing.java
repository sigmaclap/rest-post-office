package com.example.mailing.entity;

import com.example.utills.MailingType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "mailing")
@Schema(description = "Сущность почтового отправления")
public class Mailing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    private MailingType type;
    @Column(name = "index_recipient")
    private String indexRecipient;
    @Column(name = "address_recipient")
    private String addressRecipient;
    @Column(name = "name_recipient")
    private String nameRecipient;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mailing mailing = (Mailing) o;
        return Objects.equals(id, mailing.id) && type == mailing.type && Objects.equals(indexRecipient, mailing.indexRecipient) && Objects.equals(addressRecipient, mailing.addressRecipient) && Objects.equals(nameRecipient, mailing.nameRecipient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, indexRecipient, addressRecipient, nameRecipient);
    }
}
