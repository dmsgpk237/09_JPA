package com.ohgiraffers.section03.bidirection;

import jakarta.persistence.*;
import lombok.*;

/*
* 양방향 매핑에서 어느 한 쪽이 연관관계가 주인이 되면,
* 주인이 아닌 쪽에는 속성을 지정 해주어야 한다.
* 이 때 연관관계의 주인이 아닌 객체에 mappedBy를 사용해서
* 연관관계의 주인 객체의 필드명을 매핑 시켜 놓으면 양방향 관계를 적용할 수 있다.
* */
@Entity(name = "bidirection_menu")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "category") // StackOverflowError 발생하지 않게 순환참조를 방지하는 것.
public class Menu {

    @Id
    @Column(name="menu_code")
    private int menuCode;

    @Column(name="menu_name")
    private String menuName;

    @Column(name="menu_price")
    private int menuPrice;

    @JoinColumn(name = "category_code")
    @ManyToOne
    private Category category;

    @Column(name="orderable_status")
    private String orderableStatus;


}