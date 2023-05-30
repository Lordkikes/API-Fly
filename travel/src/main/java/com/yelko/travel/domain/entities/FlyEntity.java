package com.yelko.travel.domain.entities;

import com.yelko.travel.util.AeroLine;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name= "fly")
public class FlyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double originLat;
    private Double originLng;
    private Double destinyLat;
    private Double destinyLng;

    @Column(length = 20)
    private String originName;

    @Column(length = 20)
    private String destinyName;
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private AeroLine aeroLine;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FlyEntity flyEntity = (FlyEntity) o;
        return getId() != null && Objects.equals(getId(), flyEntity.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
