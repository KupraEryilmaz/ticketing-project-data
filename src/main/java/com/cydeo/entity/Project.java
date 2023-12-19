package com.cydeo.entity;

import com.cydeo.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "projects")
@Where(clause = "is_deleted=false")
public class Project extends BaseEntity {
    @Column(unique = true)//database var olan bir projectcode ile farkli bir proje olusturmamiza izin vermez.Yani bir project kodu yalnizca bir kere kullanabiliriz.Bu kod olmadigi taktirde ayni project code ile birden fazla project olusturabiliriz.Bu da field imizi unique yapmaz.Ui da beklentim project kodumun unique olmasi fakat bu kodu yazmazsam database bunu bilemez ve ayni project kod ile farkli projelerde olusturabilir
    private String projectCode;
    private String projectName;
    @Column(columnDefinition = "DATE")
    private LocalDate startDate;
    @Column(columnDefinition = "DATE")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private Status projectStatus;
    private String projectDetail;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private User assignedManager;

}
