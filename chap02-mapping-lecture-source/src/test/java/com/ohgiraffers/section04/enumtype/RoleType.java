package com.ohgiraffers.section04.enumtype;

/*
* @Enumerated 어노테이션은 Enum 타입 매핑을 위해 사용된다.
* - EnumType.ORDINAL : Enum 타입을 순서로 매핑한다.
* - EnumType.STRING : Enum 타입을 문자열로 매핑한다.
*
* ORDINAL
* -> enum 객체 안에 넣어준 순서대로 사용하는 방법
* -> 용량을 아낄 수 있다는 장점이 있다.
* -> 순서를 바꾸면 (인덱스)지정된 데이터가 바뀌기 때문에 순서를 꼭 맞춰줘야 하는 단점이 있다.
*
* STRING
* -> enum 객체를 문자열로 사용하는 방법
* -> 문자열로 지정된 키처럼 사용하는 방법
* -> enum의 순서가 바뀌거나 enum이 추가되어도 안전하다.
* */

/*
* Enum -> 서로 연관된 상수들의 집합을 의미하는 데이터 타입
*
* 장점)
* - 코드 가독성 높이기 가능
* - 타입세이프티 보장 가능
* */
public enum RoleType {
    ADMIN, MEMBER

}
