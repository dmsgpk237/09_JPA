package com.ohgiraffers.section03.projection;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "embedded_menu")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class EmbeddedMenu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Embedded
    private MenuInfo menuInfo;

    @Column(name = "cateogry_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

}
