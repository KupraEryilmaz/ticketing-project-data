package com.cydeo.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//Aslinda all arg constructor a ihtiyacimiz yok.Fakat ozzy kalabilir dedi.Cunku bu bilgileri kullanici girmiyor.
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isDeleted = false;
    @Column(nullable = false,updatable = false)
    private LocalDateTime insertDateTime;
    @Column(nullable = false,updatable = false)
    private Long insertUserId;
    @Column(nullable = false)
    private LocalDateTime lastUpdateDateTime;
    private Long lastUpdateUserId;

    @PrePersist
    private void onPrePersist() {//bu method yeni bir user task project olusturdugumuz zaman calistirlmali
        this.insertDateTime = LocalDateTime.now();
        this.lastUpdateDateTime = LocalDateTime.now();
        this.insertUserId = 1L;//Security suan henuz baglanmadi o nedenle hardcode olarak manuel yaziyorum.Aslinda sisteme giriyorsa onun id si olacak ama biz suan icin 1L yaziyoruz.Fakat security kismina geldigimiz zaman baglanti yapacak kendi alacak.Dinamik olacak
        this.lastUpdateUserId = 1L;
    }

    @PreUpdate
    private void onPreUpdate() {//bu method user, task ve project i update etmek istedigimiz zaman calistirlmali

        this.lastUpdateDateTime = LocalDateTime.now();
        this.lastUpdateUserId = 1L;

    }

}
