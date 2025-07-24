package com.josegvasconcelos.videometadata.domain.entity;

import com.josegvasconcelos.videometadata.domain.util.ULIDGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "users")
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(generator = "ulid-generator")
    @GenericGenerator(name = "ulid-generator", type = ULIDGenerator.class)
    private String id;

    private String username;
    private String password;
    private String role;

}
