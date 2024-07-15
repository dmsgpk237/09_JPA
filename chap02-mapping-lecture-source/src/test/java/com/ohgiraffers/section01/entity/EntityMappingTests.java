package com.ohgiraffers.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityMappingTests {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    @DisplayName("테이블 만들기 테스트")
    @Test
    public void createTableTest() {

        // given
        Member member = new Member();
        member.setMemberNo(1);
        member.setMemberId("user01");
        member.setMemberPwd("pass01");
        member.setNickname("홍길동");
        member.setPhone("010-2456-7555");
        member.setEmail("email01@gmail.com");
        member.setAddress("서울시 강서구");
        member.setEnrollDate(LocalDate.now());
        member.setMemberRole("ROLE_MEMBER");
        member.setStatus("Y");

        // test 구조 맞추려고 넣어놓은 건데 안 넣어놔도 실행됨, 설정 해놔서.
        // when
        // 영속성 컨텍스트에 넣어주기
        entityManager.persist(member);

        // then
        Member foundMember = entityManager.find(Member.class, member.getMemberNo());
        assertEquals(member.getMemberNo(), foundMember.getMemberNo());
    }
}
