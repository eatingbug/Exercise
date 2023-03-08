package com.review.totalpractice.stamp;

import com.review.totalpractice.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Stamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stampId;

    @Column(nullable = false)
    private int stampCount;

    @OneToOne
    @JoinColumn(name = "MEMEBER_ID")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        if (member.getStamp() != this) {
            member.setStamp(this);
        }
    }
}
