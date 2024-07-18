package com.ohgiraffers.springdatajpa.menu.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CategoryDTO {

    private int categoryCode;

    private String categoryName;

    private Integer refCategoryCode;


}
