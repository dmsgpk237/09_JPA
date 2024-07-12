package com.ohgiraffers.section02.crud;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "select02_menu") // 엔티티 객체로 만들기 위한 어노테이션(다른 패키지에 동일 이름의 클래스가 존재하면 name을 필수로 지정해줘야 한다.)
@Table(name = "tbl_menu") // 데이터베이스에 매핑될 테이블 이름을 설정한다.

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

// setter : 어디에서든지 setter 필드값을 변경 가능하게 만들어주는 역할
// setter 사용을 지양함, 어디에서든지 값을 변경할 수 있으면 혼돈을 줄 수 있기 때문. 대신 빌드를 사용함.
public class Menu {
    // 데이터베이스에서 가져오는 친구들 : 엔티티, 이 친구들이 엔티티 역할을 할 것임

    @Id //pk에 해당하는 속성에 지정
    // 오토인텔리먼트 해주는 거
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본키 값을 데이터 베이스에서 생성ㅎ
    @Column(name = "menu_code") // 데이터베이스에 대응되는 컬럼명 지정
                                // 데이터 베이스에 있는 컬럼명이랑 필드명이랑 매핑해서 jpa한테 알려주는 것
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;





}
