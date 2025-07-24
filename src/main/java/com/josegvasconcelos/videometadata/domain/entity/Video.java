package com.josegvasconcelos.videometadata.domain.entity;

import com.josegvasconcelos.videometadata.domain.util.ULIDGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "videos")
@Entity(name = "videos")
@EqualsAndHashCode(of = "id")
public class Video {
    @Id
    @GeneratedValue(generator = "ulid-generator")
    @GenericGenerator(name = "ulid-generator", type = ULIDGenerator.class)
    private String id;

    private String title;
    private String description;
    private String source;
    private String url;
    private Long durationInSeconds;
}
