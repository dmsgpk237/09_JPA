package com.ohgiraffers.section05.access.subsection02.property;

// getter를 사용한다는 것은 직접 접근하지 않고 메소드를 통해 접근한다는 것임(반환값 사용)
// 그래서 getter를 사용할 때는 메소드의 반환값을 사용하기 때문에 중간에 코드를 넣을 수 있음(그래서 처리나 검증이 필요할 때 사용할 것임)

import jakarta.persistence.*;

@Entity(name = "member_section05_sub02")
@Access(AccessType.PROPERTY)
/*
* 클래스 레벨에 @Access(AccessType.PROPERTY)를 선언할 때
* @Id 어노테이션이 필드에 있다면 엔티티를 생성하지 못한다.
*
* */

public class Member {

    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    @Column(name = "member_pwd")
    private String memberPwd;

    @Column(name = "nickname")
    private String nickname;

    @Id
    public int getMemberNo() {
        return memberNo;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getMemberPwd() {
        return memberPwd;
    }

    public String getNickname() {
        System.out.println("🎇🎉✨💎 getNickname() 메소드 확인💎✨🎉🎇");
        return nickname + "님";
    }

    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public void setMemberPwd(String memberPwd) {
        this.memberPwd = memberPwd;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "Member{" +
                "memberNo=" + memberNo +
                ", memberId='" + memberId + '\'' +
                ", memberPwd='" + memberPwd + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }

}
