package KKSC.page.domain.member.service.impl;

import KKSC.page.domain.member.dto.request.ProfileUpdateRequest;
import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Profile;
import KKSC.page.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//Member 목 유저를 만든다.
//prfile 목 유저를 만든다.
//수정 메서드를 사용해서 수정한다 - 메서드의 이름은 update()이다.
//update() 메서드는 프로필 수정 메서드이다.
//profile 안에는 intro,nickname,ProfilePhotoPath가 있다.
//update() 메서드는 intro,nickname,ProfilePhotoPath를 수정해준다.
//입력한 것과 수정되어 나온 값을 비교하여 검증한다.
//updateTest()

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberServiceImpl memberServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private Member member;

    @Test
    public void updatetest(){

        // given
        ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest(
                "test@example.com",
                "Updated Intro",
                "Updated Nickname",
                "UpdatedPath/To/ProfilePhoto"
        );

        // Member 목 유저 생성
        Member mockMember = mock(Member.class);
        // Profile 목 유저 생성
        Profile mockProfile = mock(Profile.class);

        when(mockProfile.getIntro()).thenReturn("Intro");
        when(mockProfile.getNickname()).thenReturn("Nickname");
        when(mockProfile.getProfilePhotoPath()).thenReturn("SamplePath/To/ProfilePhoto");

        when(mockMember.getProfile()).thenReturn(mockProfile);
        when(memberRepository.findByEmail("test@example.com")).thenReturn(Optional.of(mockMember));

        //when
        memberServiceImpl.update(profileUpdateRequest);

        //then
        assertEquals("Updated Intro", mockProfile.getIntro());
        assertEquals("Updated Nickname", mockProfile.getNickname());
        assertEquals("UpdatedPath/To/ProfilePhoto", mockProfile.getProfilePhotoPath());

        //then
        verify(mockProfile).changeIntro("Updated Intro");
        verify(mockProfile).changeNickname("Updated Nickname");
        verify(mockProfile).changeProfilePhotoPath("UpdatedPath/To/ProfilePhoto");
        verify(memberRepository).save(mockMember);

    }


}
