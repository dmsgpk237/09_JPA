package com.ohgiraffers.section04.enumtype;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//@Entity(name = "member_section04") // 엔티티명 지정
@Table(name = "tbl_member_section04") // 데이터 베이스에 생성되는 테이블명
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Member {

    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd", length = 100)
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "enroll_date")
    private LocalDate enrollDate;

    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    private RoleType memberRole;

    @Column(name = "status")
    private String status;
}
