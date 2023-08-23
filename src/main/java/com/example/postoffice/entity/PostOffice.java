package com.example.postoffice.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "post_office")
@Schema(description = "Сущность почтового отделения")
public class PostOffice {
    @ToString.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "index_office")
    @NotBlank
    private String indexOffice;
    @Column(name = "name_office")
    @NotBlank
    private String nameOffice;
    @Column(name = "address_office")
    @NotBlank
    private String addressOffice;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostOffice that = (PostOffice) o;
        return Objects.equals(id, that.id) && Objects.equals(indexOffice, that.indexOffice) && Objects.equals(nameOffice, that.nameOffice) && Objects.equals(addressOffice, that.addressOffice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, indexOffice, nameOffice, addressOffice);
    }
}
