package com.security.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="roles")
@NoArgsConstructor
@Data
public class Role extends BaseEntity{

    private String description;
}
