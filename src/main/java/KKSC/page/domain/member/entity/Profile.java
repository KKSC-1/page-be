package KKSC.page.domain.member.entity;

import KKSC.page.domain.member.dto.request.MemberRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "profile")
    private Member member;

    /**
     * photo 추가 예정
     * 08.31 성열 추가 완료
     */

    @Column(name = "intro", length = 200, nullable = true)
    private String intro;

    @Column(name = "nickname", length = 50)
    private String nickname;

    @Column(name = "profile_photo_path", length = 255, nullable = true)
    private String profilePhotoPath;

    private String email;

    // intro 변경 메서드
    public void changeIntro(String intro) {
        this.intro = intro;
    }

    // nickname 변경 메서드
    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    // profilePhotoPath 변경 메서드
    public void changeProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

//    //이건 아마 사용 안할것 같습니다- 성열
//    public void update(MemberRequest memberRequest) {
//        this.intro = memberRequest.intro();
//        this.nickname = memberRequest.nickname();
//        //포토 추가예정
//    }
}
