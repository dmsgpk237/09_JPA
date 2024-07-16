package com.ohgiraffers.section02.onetomany;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "category_and_menu")
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CategoryAndMenu {

    /*
    * fetch
    *
    *  - @OneToMany(fetch = FetchType.LAZY)
    *  - @ManyToMany        -- 지연로딩이 default
    *  - @ManyToOne(fetch = FetchType.EAGER)
    *  - @OneToOne          -- 이른 로딩이 default
    * */

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @JoinColumn(name = "category_code")
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Menu> menuList;
    // 얘도 같이 영속화를 시켜줘야 메뉴에 우리가 작성한 메뉴가 들어가게 됨


}
