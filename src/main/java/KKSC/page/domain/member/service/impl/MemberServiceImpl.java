package KKSC.page.domain.member.service.impl;

import KKSC.page.domain.member.dto.request.MemberLoginRequest;
import KKSC.page.domain.member.dto.request.MemberRequest;
import KKSC.page.domain.member.dto.request.ProfileUpdateRequest;
import KKSC.page.domain.member.dto.request.RetireRequest;
import KKSC.page.domain.member.dto.response.MemberResponse;
import KKSC.page.domain.member.entity.Member;
import KKSC.page.domain.member.entity.Profile;
import KKSC.page.domain.member.exception.MemberException;
import KKSC.page.domain.member.repository.MemberRepository;
import KKSC.page.domain.member.service.MemberService;
import KKSC.page.global.auth.service.JwtService;
import KKSC.page.global.exception.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

//    @Override
//    public String login(MemberLoginRequest memberLoginRequest, HttpServletResponse response) {
//
//        // 존재하는 사용자인지 확인
//        Member member = memberRepository.findByEmail(memberLoginRequest.email())
//                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));
//
//        // 비밀번호 일치 여부 확인
//        if (!passwordEncoder.matches(memberLoginRequest.password(), member.getPassword())) {
//            throw new MemberException(ErrorCode.MISMATCH_PASSWORD);
//        }
//
//        // 토큰 생성
//        String accessToken = jwtService.createAccessToken(member.getEmail());
//
//        jwtService.sendAccessToken(response, accessToken);
//
//        return accessToken;
//    }

    @Override
    public void retire(RetireRequest retireRequest) {

        //RetireRequest객체에서 email,password를 가져와 변수에 저장
        String email = retireRequest.email();
        String password= retireRequest.password();

        // 이메일로 회원을 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        // 비밀번호 검증
        if (!member.getPassword().equals(password)) {
            throw new MemberException(ErrorCode.MISMATCH_PASSWORD);
        }

        // 회원 탈퇴 로직: 회원을 삭제하거나 탈퇴 상태로 업데이트
        memberRepository.delete(member);
    }

    @Override
    public void update(ProfileUpdateRequest profileupdateRequest) {

        Member member = memberRepository.findByEmail(profileupdateRequest.email())
                .orElseThrow(() -> new MemberException(ErrorCode.NOT_FOUND_MEMBER));

        Profile profile = member.getProfile();

        /*
        * 아래 3개 코드는 프로필 최초 입력 + 프로필 추후 수정에 대한 코드를 합친 것임
        * 이해 안될 시 류성열 한테 연락 바람(010-9055-1265)
        * */
        profile.changeIntro(profileupdateRequest.intro());

        profile.changeNickname(profileupdateRequest.nickname());

        profile.changeProfilePhotoPath(profileupdateRequest.profilePhotoPath());

        // 변경된 정보 저장(member와 profile은 일대일 관계이므로 member를 저장시 profile도 함께 저장)
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