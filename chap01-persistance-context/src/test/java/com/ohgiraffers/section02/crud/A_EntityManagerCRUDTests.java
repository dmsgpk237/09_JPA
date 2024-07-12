package com.ohgiraffers.section02.crud;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class A_EntityManagerCRUDTests {
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

    @DisplayName("메뉴코드로 메뉴 조회 테스트")
    @Test
    public void selectMenuByMenuCodeTest() {

        // given
        int menuCode = 2;

        // when
        Menu menu = entityManager.find(Menu.class, menuCode); // 어디서 가져올 거냐, 어떤 걸 가져올 거냐를 담아주면 찾아온다.

        // then
        Assertions.assertNotNull(menu);

        assertEquals(menuCode, menu.getMenuCode());
        System.out.println("menu : " + menu);

        Menu menu2 = entityManager.find(Menu.class, 2); // 어디서 가져올 거냐, 어떤 걸 가져올 거냐를 담아주면 찾아온다.
        System.out.println("menu2 : " + menu2);

    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @Test
    public void insertNewMenuTest() {

        // given
        Menu menu = new Menu();
        menu.setMenuName("JPA 테스트용 신규 메뉴");
        menu.setMenuPrice(50000);
        menu.setCategoryCode(4);
        menu.setOrderableStatus("Y");

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin(); // 트렌젝션 시작을 선언
        try {
            entityManager.persist(menu);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        // then
        // 영속성 컨텍스트에 menu가 있는지 확인
        Assertions.assertTrue(entityManager.contains(menu));

    }

    @DisplayName("메뉴 이름 수정 테스트")
    @Test
    public void modifyMenuNameTest() {

        // given
        Menu menu = entityManager.find(Menu.class, 23);
        System.out.println("menu : " + menu);

        String menuNameChange = "생갈치 스무디";

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            menu.setMenuName(menuNameChange); //생갈치 스무디로 변경
            transaction.commit(); // 커밋
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        // then
        assertEquals(menuNameChange, menu.getMenuName());
        assertEquals(menuNameChange, entityManager.find(Menu.class, 23).getMenuName());

    }

    @DisplayName("메뉴 삭제하기 테스트")
    @Test
    public void deleteMenu() {

        // given
        Menu menuToRemove = entityManager.find(Menu.class, 23); // 영속화
        System.out.println("menuToRemove : " + menuToRemove);

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.remove(menuToRemove);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        // then
        Menu removeMenu = entityManager.find(Menu.class, 23);
        assertEquals(null, removeMenu);

    }

    @DisplayName("준영속 detach 테스트") // 거의 안 씀
    @Test
    public void test5() {

        // given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        // when
        entityManager.detach(foundMenu2); // 특정 엔티티만 준영속 상태로 만든다

        System.out.println("foundMenu1 ===> " + foundMenu1);
        foundMenu1.setMenuPrice(40000);
        foundMenu2.setMenuPrice(40000);

        // then
        assertEquals(40000, entityManager.find(Menu.class, 11).getMenuPrice());
        assertEquals(40000, entityManager.find(Menu.class, 12).getMenuPrice());

    }

    @DisplayName("준영속 clear 테스트")
    @Test
    public void test6() {

        // given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        // when
        entityManager.clear(); // 영속성 컨텍스트를 초기화

        System.out.println("foundMenu1 ===> " + foundMenu1);
        foundMenu1.setMenuPrice(40000);
        foundMenu2.setMenuPrice(40000);

        // then
        assertEquals(40000, entityManager.find(Menu.class, 11).getMenuPrice());
        assertEquals(40000, entityManager.find(Menu.class, 12).getMenuPrice());

    }

    @DisplayName("close 테스트")
    @Test
    public void test7() {

        // given
        Menu foundMenu1 = entityManager.find(Menu.class, 11);
        Menu foundMenu2 = entityManager.find(Menu.class, 12);

        // when
        entityManager.close(); // close는 영속성 컨텍스트를 종료한다.

        foundMenu1.setMenuPrice(40000);
        foundMenu2.setMenuPrice(40000);

        // then
        assertEquals(40000, entityManager.find(Menu.class, 11).getMenuPrice());
        assertEquals(40000, entityManager.find(Menu.class, 12).getMenuPrice());

    }

    @DisplayName("삭제 remove 테스트")
    @Test
    public void 삭제_테스트8() {

        // given
        Menu foundMenu = entityManager.find(Menu.class, 50);

        // when
        entityManager.remove(foundMenu);
        Menu reFoundMenu = entityManager.find(Menu.class, 50);

        // then
        assertEquals(null, reFoundMenu);

    }

}
