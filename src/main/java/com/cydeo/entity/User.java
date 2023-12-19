package com.cydeo.entity;

import com.cydeo.enums.Gender;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
//@Where(clause = "is_deleted=false") //user entity i kullanan herhangi bir repository icinde bulunan querylere where clause i dahil eder
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String userName;
    private String passWord;
    private boolean enabled;
    private String phone;
    @ManyToOne//spring foreign key i nerede olusturacagini bilemez.O nedenle bu annotation u kullanmaliyiz.
    private Role role;
    @Enumerated(EnumType.STRING)
    private Gender gender;

//    public User(Long id, LocalDateTime insertDateTime, Long insertUserId, LocalDateTime lastUpdateDateTime, Long lastUpdateUserId, String firstName, String lastName, String userName, String passWord, boolean enabled, String phone, Role role, Gender gender) {
//        super(id, insertDateTime, insertUserId, lastUpdateDateTime, lastUpdateUserId);
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.userName = userName;
//        this.passWord = passWord;
//        this.enabled = enabled;
//        this.phone = phone;
//        this.role = role;
//        this.gender = gender;
//    }
    //Constructor a ihtiyacimiz yok Cunku MVC partta bu bilgileri boostrap teki DataGeneretorden aliyorduk.Data Generetorde obje olusturuyorduk.Bu nedenle constructor a ihtiyacimiz vardi.Fakat suan bu bilgiler UI dan Database e gelecek.Oradanda biz alacagiz.Yani bizim obje olusturmamaiza gerek kalmayacak.

}
