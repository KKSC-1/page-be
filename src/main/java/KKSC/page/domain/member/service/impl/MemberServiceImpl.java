package KKSC.page.domain.member.service.impl;

import KKSC.page.domain.member.dto.request.MemberLoginRequest;
import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.request.ProfileRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Profile;
import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.member.service.MemberService;
import KKSC.page.global.auth.service.JwtService;
import KKSC.page.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Long register(MemberRequest memberRequest) {
        Member member = memberRequest.toEntity();

        // 이메일 중복 확인
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new MemberException(ErrorCode.ALREADY_EXIST_MEMBER);
        }

        // 비밀번호 암호화
        member.encodePassword(passwordEncoder);

        return memberRepository.save(member).getId();
    }

    @Override
    public String login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {

        // 존재하는 사용자인지 확인
        Member member = memberRepository.findByEmail(memberLoginRequest.email())
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(memberLoginRequest.password(), member.getPassword())) {
            throw new MemberException(ErrorCode.MISMATCH_PASSWORD);
        }

        // 토큰 생성
        String accessToken = jwtService.createAccessToken(member.getEmail());

        jwtService.sendAccessToken(response, accessToken);

        return accessToken;
    }

    @Override
    public void retire(String email) {

    }

    /*
    * 멤버 리퀘스트로 이메일을 불러와 기존 회원 정보를 가져오고
    * 프로필 리퀘스트 객체로 인트로, 닉네임, 사진 경로를 수정하는 메서드 사용
    * */
    @Override
    public void update(ProfileRequest profileRequest ,MemberRequest memberRequest) {

        // 기존 회원 정보 가져오기
        Member member = memberRepository.findByEmail(memberRequest.email())
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();

        // intro 필드가 존재하면 업데이트
        if (profileRequest.intro() != null) {
            profile.changeIntro(profileRequest.intro());
        }

        // nickname 필드가 존재하면 업데이트
        if (profileRequest.nickname() != null) {
            profile.changeNickname(profileRequest.nickname());
        }

         //profilePhotoPath 필드가 존재하면 업데이트
         if (profileRequest.profilePhotoPath() != null) {
             profile.changeProfilePhotoPath(profileRequest.profilePhotoPath());
         }

         /*
         * member엔티티와 profile 엔티티가 일대일 연관되어 있으므로
         * member엔티티를 저장하면, 수정된 profile 엔티티도 저장되는 것 같다.
         * */
        // 변경된 정보 저장
        memberRepository.save(member);
    }

    @Override
    public MemberResponse getMemberProfile(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();

        return MemberResponse.from(member);
    }

    @Override
    public void logout(HttpServletRequest request) {
        String email = jwtService.extractUsername(request)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        jwtService.destroyRefreshToken(email);
    }
}