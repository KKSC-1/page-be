package KKSC.page.domain.member.dto.request;

import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Profile;
import jakarta.validation.constraints.Size;

public record ProfileRequest(

        @Size(max = 200, message = "자기소개는 최대 200자까지 가능합니다.")
        String intro,

        @Size(min = 2, max = 10, message = "별명은 최대 10자까지 가능합니다.")
        String nickname,

        @Size(max = 255, message = "프로필 사진 경로는 최대 255자까지 가능합니다.")
        String profilePhotoPath
) {
        public Profile toEntity() {
                return Profile.builder()
                        .intro(intro)
                        .nickname(nickname)
                        .profilePhotoPath(profilePhotoPath) // 추가된 필드
                        .build();
        }

}