package com.review.totalpractice.member.entity;

import com.review.totalpractice.order.entity.Order;
import com.review.totalpractice.stamp.Stamp;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity // JPA 의 관리대상 엔티티로 설정
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id // Member 엔티티의 기본키 컬럼으로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 IDENTITY 전략으로 자동 생성한다.
    private Long memberId;

    @Column(nullable = false, updatable = false, unique = true) // 멤버 변수를 컬럼과 매핑한다.
    private String email;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 13, nullable = false, unique = true)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    @OneToOne(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Stamp stamp;

    public void setOrder(Order order) {
        orders.add(order);
        if (order.getMember() != this) {
            order.setMember(this);
        }
    }

    public void setStamp(Stamp stamp) {
        this.stamp = stamp;
        if (stamp.getMember() != this) {
            stamp.setMember(this);
        }
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("활동중"),
        MEMBER_SLEEP("휴면 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}