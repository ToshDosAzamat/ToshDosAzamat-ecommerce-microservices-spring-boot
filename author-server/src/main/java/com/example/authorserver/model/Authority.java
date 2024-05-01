package com.example.authorserver.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
@Table(name = "AUTHORITIES")
@Getter
@Setter
@NoArgsConstructor
public class Authority implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;

  @Override
  public String getAuthority() {
    return this.name;
  }
}