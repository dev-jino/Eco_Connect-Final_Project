package com.kosta268.eco_connect.entity.member;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kosta268.eco_connect.entity.chat.ChatMessage;
import com.kosta268.eco_connect.entity.chat.ChatRoomMember;
import com.kosta268.eco_connect.entity.gathering.Gathering;
import com.kosta268.eco_connect.entity.gathering.MemberGathering;
import com.kosta268.eco_connect.entity.gifticon.MemberGifticon;
import com.kosta268.eco_connect.entity.mission.MemberMission;
import com.kosta268.eco_connect.entity.mission.MissionLike;
import com.kosta268.eco_connect.entity.point.Point;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@SuperBuilder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"chatMessageList", "chatRoomMemberList"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;
    private String id;
    private String password;
    private String email;
    private String profile;
    private String address;
    private String zoneCode;
    private String fullAddress;
    private String subAddress;
    private LocalDateTime createdIn;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Role role = Role.ROLE_USER;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "point_id")
    private Point point;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MemberGathering> memberGatherings = new ArrayList<>();

    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Gathering> createGatherings = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MemberMission> memberMissions = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<MissionLike> likeMissions = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ChatMessage> chatMessageList = new ArrayList<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<ChatRoomMember> chatRoomMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    @Builder.Default
    private List<MemberGifticon> memberGifticons = new ArrayList<>();


    // 양방향 연관관계 설정 메서드
    public void addMemberMission(MemberMission memberMission) {
        this.memberMissions.add(memberMission);
        memberMission.setMember(this);
    }


    // 포인트 증가
    public void increasePoint(int point) {
        if (this.point == null) {
            this.point = new Point(point, point);
        } else {
            this.point.increasePoint(point);
        }
    }

    public void addChatRoomUserList(ChatRoomMember chatRoomMember) {
        chatRoomMemberList.add(chatRoomMember);
        chatRoomMember.setMember(this);
    }

    @PrePersist
    public void prePersist() {
        this.createdIn = LocalDateTime.now();
    }
}
