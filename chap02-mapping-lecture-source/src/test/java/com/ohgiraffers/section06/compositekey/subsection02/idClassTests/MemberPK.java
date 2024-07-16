package com.ohgiraffers.section06.compositekey.subsection02.idClassTests;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberPK {
    @Column(name = "member_no")
    private int memberNo;

    @Column(name = "member_id")
    private String memberId;

    // equals
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        MemberPK memberPK = (MemberPK) obj;
        return memberNo == memberPK.memberNo && Objects.equals(memberId, memberPK.memberId);
    }


    // hashCode
    @Override
    public int hashCode() {
        return Objects.hash(memberNo, memberId);

    }


}
