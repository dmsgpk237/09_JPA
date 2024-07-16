package com.ohgiraffers.section03.bidirection;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "bidirection_category")
@Table(name = "tbl_category")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Category {

    @Id
    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "ref_category_code")
    private Integer refCategoryCode;

    @OneToMany(mappedBy = "category")
    private List<Menu> menuList;
}
