package com.example.community_service.community.presentation;

import com.example.community_service.community.application.CommunityMemberServiceImpl;
import com.example.community_service.community.application.CommunityServiceImpl;
import com.example.community_service.community.application.YoutubeService;
import com.example.community_service.community.dto.RejoinCommunityDto;
import com.example.community_service.community.dto.request.*;
import com.example.community_service.community.dto.response.*;
import com.example.community_service.community.vo.request.*;
import com.example.community_service.community.vo.response.*;
import com.example.community_service.global.base.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityServiceImpl communityService;
    private final CommunityMemberServiceImpl communityMemberService;
    private final YoutubeService youtubeService;

    /**
     * 서버간 상호작용 시에 요구되는 API이기 때문에 게시판, 게시물 서버와 나중에 얘기해봐야함.
     */
    //todo: 모든 엔티티에 createdAt, updatedAt 추가하기 / 밴 해제 로직 다시 짜기
    // 밴 된 유저가 권한이 요구되는 작업을 수행하려고 할 때 ->
    // 1. 해당 유저가 밴 테이블에 존재하는지 확인 -> 존재할 경우 2.로 이동 / 존재하지 않을 경우 작업 수행 허가
    // 2. 존재한다면 현재 시간과 밴 종료일을 비교 -> 현재일이 밴 종료일이 더 늦다면 밴 해제(밴 데이터 자체를 삭제)
    // 만약 현재 시간이 밴 종료일보다 더 빠르다면 게시물 작성 등 기본적인 커뮤니티 활동을 할 수 없도록 해야함.

    // todo: requestDto와 responseDto inner class 활용하여 하나로 합치기.
    //todo: 크리에이터 가입 로직 정의.
    // 1. 크리에이터 전환(크리에이터 전환 api)
    // 2. 크리에이터 커뮤니티 존재여부 확인 api
    // 3. 크리에이터 커뮤니티 생성(크리에이터 커뮤니티 생성 api, 크리에이터 여부도 같이 넘겨줘야함)
    @Tag(name = "미분류상태/테스트중") @Operation(summary = "해당 커뮤니티 가입 이력 조회")
    @PostMapping("/{communityId}/users/me/join-history")
    public ApiResponse<Object> checkUserJoinHistory(
            @Valid @RequestBody RequestCheckVo requestVo, @PathVariable Long communityId) {

        // 커뮤니티 가입 이력 존재 여부 확인
        Boolean joinHistoryExists = communityMemberService.checkMemberJoinHistory(communityId, requestVo.getUserUuid());

        ResponseCheckVo.JoinHistoryExistsVo responseVo =
                ResponseCheckVo.JoinHistoryExistsVo.formResponseVo(joinHistoryExists);

        return ApiResponse.ofSuccess(responseVo);
    }

    //todo: 커뮤니티 생성 로직
    // 1. 크리에이터 등록 api(user 서버)
    // 2. 커뮤니티 생성 api(커뮤니티 서버)

    @Tag(name = "미분류상태/테스트중") @Operation(summary = "해당 커뮤니티 재가입")
    @PutMapping("/{communityId}/users/me/rejoin")
    public ApiResponse<Object> rejoinCommunity(
            @Valid @RequestBody RequestRejoinCommunityVo requestVo, @PathVariable Long communityId) {

        RejoinCommunityDto.RequestRejoinCommunityDto requestDto =
                RejoinCommunityDto.RequestRejoinCommunityDto.formRequestDto(requestVo.getUserUuid());

        // 커뮤니티 재가입 처리(가입한 회원 수 반환)
        Integer memberCount = communityMemberService.rejoinCommunity(communityId, requestDto.getUserUuid());

        // 커뮤니티 회원수 변경
        communityService.updateCommunityMemberCount(communityId, memberCount);

        RejoinCommunityDto.ResponseRejoinCommunityDto responseDto =
                RejoinCommunityDto.ResponseRejoinCommunityDto.formResponseDto(
                        requestDto.getUserUuid(), communityId);

        ResponseRejoinCommunityVo responseVo = ResponseRejoinCommunityVo.formResponseVo(
                responseDto.getUserUuid(), responseDto.getCommunityId());

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "미분류상태/테스트중") @Operation(summary = "커뮤니티 이름 중복확인")
    @GetMapping("{communityName}/duplicate")
    public ApiResponse<Object> checkDuplicateCommunityName(@PathVariable String communityName) {

        Boolean duplicateCheck = communityService.isCommunityNameDuplicate(communityName);

        ResponseCheckVo.CommunityNameDuplicateVo responseVo =
                ResponseCheckVo.CommunityNameDuplicateVo.formResponseVo(duplicateCheck);

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "미분류상태/테스트중") @Operation(summary = "유저 소유의 커뮤니티 존재 여부 확인")
    @PostMapping("/users/me/creator-community")
    public ApiResponse<Object> checkCommunityExistence(
            @Valid @RequestBody RequestCheckVo requestVo) {

        Boolean existenceCheck = communityService.checkCommunityExistence(requestVo.getUserUuid());

        ResponseCheckVo.CheckCommunityExistenceVo responseVo =
                ResponseCheckVo.CheckCommunityExistenceVo.formResponseVo(existenceCheck);

        return ApiResponse.ofSuccess(responseVo);
    }

    // todo: 커뮤니티 가입/탈퇴 시 해당 커뮤니티 회원수 증감은 스케줄러로 처리
    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "커뮤니티 가입")
    @PostMapping("/{communityId}/users/me")
    public ApiResponse<Object> joinCommunity(
            @Valid @RequestBody RequestJoinCommunityVo requestVo, @PathVariable Long communityId) {

        RequestJoinCommunityDto requestDto = RequestJoinCommunityDto.formRequestDto(requestVo.getUserUuid());

        // 커뮤니티 가입 처리(가입한 회원 수 반환)
        Integer memberCount = communityMemberService.joinCommunity(communityId, requestDto.getUserUuid());

        // 커뮤니티 회원수 변경
        communityService.updateCommunityMemberCount(communityId, memberCount);

        ResponseJoinCommunityVo responseVo = ResponseJoinCommunityVo.formResponseVo(
                requestDto.getUserUuid(), communityId);

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "커뮤니티 탈퇴")
    @PutMapping("/{communityId}/users/me")
    public ApiResponse<Object> leaveCommunity(
            @Valid @RequestBody RequestLeaveCommunityVo requestVo, @PathVariable Long communityId) {

        RequestLeaveCommunityDto requestDto = RequestLeaveCommunityDto.formRequestDto(requestVo.getUserUuid());

        // 커뮤니티 탈퇴 처리
        Integer memberCount = communityMemberService.leaveCommunity(communityId, requestDto.getUserUuid());

        // 커뮤니티 회원수 변경
        communityService.updateCommunityMemberCount(communityId, memberCount);

        ResponseLeaveCommunityVo responseVo = ResponseLeaveCommunityVo.formResponseVo(
                communityId, requestDto.getUserUuid());

        return ApiResponse.ofSuccess(responseVo);
    }

    // todo: 추가 테스트 필요
    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "가입한 커뮤니티 조회")
    @PostMapping("/users/me")
    public ApiResponse<Object> getJoinedCommunityList(
            @Valid @RequestBody RequestGetJoinedCommunityListVo requestVo,
            @RequestParam(defaultValue = "10") Integer size, @RequestParam(defaultValue = "1") Integer page) {

        RequestGetJoinedCommunityListDto requestDto = RequestGetJoinedCommunityListDto.formRequestDto(
                requestVo.getUserUuid());

        // 유저가 가입한 커뮤니티 목록 조회
        ResponseGetJoinedCommunityListDto responseDto =
                communityService.getJoinedCommunityList(requestDto, size, page);

        ResponseGetJoinedCommunityListVo responseVo = ResponseGetJoinedCommunityListVo.formResponseVo(
                responseDto.getCommunityList(), responseDto.getTotalPageCount());

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "커뮤니티 상세 조회")
    @GetMapping("/{communityId}/info")
    public ApiResponse<Object> getCommunityInfo(@PathVariable Long communityId) {

        // 커뮤니티id에 해당하는 커뮤니티 정보 조회
        ResponseGetCommunityInfoDto responseDto = communityService.getCommunityInfo(communityId);

        ResponseGetCommunityInfoVo responseVo = ResponseGetCommunityInfoVo.formResponseVo(
                responseDto.getCommunityId(), responseDto.getOwnerUuid(), responseDto.getBannerImage(),
                responseDto.getProfileImage(), responseDto.getYoutubeName(), responseDto.getCommunityName(),
                responseDto.getDescription(), responseDto.getCommunityMemberCount(), responseDto.getCreatedDate(),
                responseDto.getUpdatedDate());

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 관리") @Operation(summary = "커뮤니티 생성")
    @PostMapping("")
    public ApiResponse<Object> createCommunity(
            @Valid @RequestBody RequestCreateCommunityVo requestVo) throws JsonProcessingException {

        // 유튜브 API로 배너/프로필이미지/채널 이름 불러오기 기능 추가하기
        HashMap<String, String> youtubeData = youtubeService.getMyChannelInfo(requestVo.getToken());

        RequestCreateCommunityDto requestDto = RequestCreateCommunityDto.formRequestDto(
                requestVo.getOwnerUuid(), requestVo.getCommunityName(),
                requestVo.getDescription(), youtubeData.get("bannerImageUrl"), youtubeData.get("profileImageUrl"),
                youtubeData.get("youtubeName")
        );

        // 커뮤니티 생성하고 저장하기
        ResponseCreateCommunityDto responseDto = communityService.createCommunity(requestDto);

        ResponseCreateCommunityVo responseVo = ResponseCreateCommunityVo.formResponseVo(responseDto.getCommunityId());

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 관리") @Operation(summary = "커뮤니티 정보 수정")
    @PutMapping("/{communityId}/info")
    public ApiResponse<Object> updateCommunity(
            @Valid @RequestBody RequestUpdateCommunityVo requestVo, @PathVariable Long communityId) {

        RequestUpdateCommunityDto requestDto = RequestUpdateCommunityDto.formRequestDto(
                requestVo.getBannerImage(), requestVo.getProfileImage(),
                requestVo.getDescription(), requestVo.getCommunityName()
        );

        // 커뮤니티id에 해당하는 커뮤니티 정보 수정
        ResponseUpdateCommunityDto responseDto =
                communityService.updateCommunity(requestDto, communityId);

        ResponseUpdateCommunityVo responseVo = ResponseUpdateCommunityVo.formResponseVo(responseDto.getCommunityId());

        return ApiResponse.ofSuccess(responseVo);
    }

    // todo: 무한스크롤로 할지, 페이징으로 처리할지 결정
//    @Tag(name = "커뮤니티 관리") @Operation(summary = "커뮤니티 가입 회원 조회")
//    @PostMapping("/{communityId}/members/list")
//    public ApiResponse<Object> getCommunityMemberList(@PathVariable Long communityId) {
//
//        // 커뮤니티id에 해당하는 커뮤니티 가입 회원 목록 조회
//        ResponseGetCommunityMemberListDto responseDto = communityMemberService.getCommunityMemberList(communityId);
//
//        ResponseGetCommunityMemberListVo responseVo = ResponseGetCommunityMemberListVo.formResponseVo(
//                responseDto.getCommunityMemberList());
//
//        return ApiResponse.ofSuccess(responseVo);
//    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴")
    @PutMapping("/{communityId}/ban-users")
    public ApiResponse<Object> banUser(
            @Valid @RequestBody RequestBanUserVo requestVo, @PathVariable Long communityId) {

        RequestBanUserDto requestDto = RequestBanUserDto.formRequestDto(
                requestVo.getTargetUuid(), requestVo.getBanEndDate());

        // uuid 해당 유저 밴 처리
        ResponseBanUserDto responseDto = communityMemberService.banUser(communityId, requestDto);

        ResponseBanUserVo responseVo = ResponseBanUserVo.formResponseVo(
                responseDto.getCommunityId(), responseDto.getBannedUserUuid(), responseDto.getBanEndDate()
        );

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴 종료일 수정")
    @PutMapping("/{communityId}/ban-users-date")
    public ApiResponse<Object> updateBanEndDate(
            @Valid @RequestBody RequestUpdateBanEndDateVo requestVo, @PathVariable Long communityId) {

        RequestUpdateBanEndDateDto requestDto = RequestUpdateBanEndDateDto.formRequestDto(
                requestVo.getTargetUuid(), requestVo.getBanEndDate()
        );

        // uuid에 해당 하는 유저의 밴 종료일 수정
        ResponseUpdateBanEndDateDto responseDto = communityMemberService.updateBanEndDate(communityId, requestDto);

        ResponseUpdateBanEndDateVo responseVo = ResponseUpdateBanEndDateVo.formResponseVo(
                responseDto.getBannedUserUuid(), responseDto.getBanEndDate(), responseDto.getCommunityId()
        );

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴 해제")
    @PutMapping("/{communityId}/unban-users")
    public ApiResponse<Object> unbanUser(
            @Valid @RequestBody RequestUnbanUserVo requestVo, @PathVariable Long communityId) {

        RequestUnbanUserDto requestDto = RequestUnbanUserDto.formRequestDto(requestVo.getTargetUuid());

        // uuid에 해당 하는 유저의 밴 해제
        ResponseUnbanUserDto responseDto = communityMemberService.unbanUser(communityId, requestDto);

        ResponseUnbanUserVo responseVo = ResponseUnbanUserVo.formResponseVo(responseDto.getUnbannedUuid());

        return ApiResponse.ofSuccess(responseVo);
    }

//    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 밴 유저 목록 조회")
//    @PostMapping("/{communityId}/ban-users/list")
//    public ApiResponse<Object> getBannedUserList(
//            @Valid @RequestBody RequestGetBannedUserListVo requestVo, @PathVariable Long communityId) {
//
//        RequestGetBannedUserListDto requestDto = RequestGetBannedUserListDto.builder()
//                .managerUuid(requestVo.getManagerUuid())
//                .build();
//
//        ResponseGetBannedUserListDto responseDto = communityService.getBannedUserList(requestDto, communityId);
//
//        removed responseVo = removed.builder()
//                .bannedUserList(responseDto.getBannedUserList())
//                .build();
//
//        return ApiResponse.ofSuccess(responseVo);
//    }
//
    @Tag(name = "커뮤니티 매니저 관리") @Operation(summary = "커뮤니티 매니저 등록")
    @PutMapping("/{communityId}/new-managers")
    public ApiResponse<Object> registerManager(
            @Valid @RequestBody RequestRegisterManagerVo requestVo, @PathVariable Long communityId) {

        RequestRegisterManagerDto requestDto = RequestRegisterManagerDto.formRequestDto(requestVo.getTargetUuid());

        ResponseRegisterManagerDto responseDto = communityMemberService.registerManager(communityId, requestDto);

        ResponseRegisterManagerVo responseVo = ResponseRegisterManagerVo.formResponseVo(
                responseDto.getCommunityId(), responseDto.getManagerUuid());

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 매니저 관리") @Operation(summary = "커뮤니티 매니저 삭제")
    @PutMapping("/{communityId}/managers")
    public ApiResponse<Object> deleteManager(
            @Valid @RequestBody RequestDeleteManagerVo requestVo, @PathVariable Long communityId) {

        RequestDeleteManagerDto requestDto = RequestDeleteManagerDto.formRequestDto(requestVo.getTargetUuid());

        ResponseDeleteManagerDto responseDto = communityMemberService.deleteManager(communityId, requestDto);

        ResponseDeleteManagerVo responseVo = ResponseDeleteManagerVo.formResponseVo(
                responseDto.getManagerUuid(), responseDto.getCommunityId());

        return ApiResponse.ofSuccess(responseVo);
    }

//    @Tag(name = "커뮤니티 매니저 관리") @Operation(summary = "커뮤니티 매니저 목록 조회")
//    @PostMapping("/{communityId}/managers/list")
//    public ApiResponse<Object> getManagerList(
//            @Valid @RequestBody RequestGetManagerListVo requestVo, @PathVariable Long communityId) {
//
//        RequestGetManagerListDto requestDto = RequestGetManagerListDto.builder()
//                .creatorUuid(requestVo.getCreatorUuid())
//                .build();
//
//        ResponseGetManagerListDto responseDto = communityService.getManagerList(requestDto, communityId);
//
//        ResponseGetManagerListVo responseVo = ResponseGetManagerListVo.builder()
//                .managerList(responseDto.getManagerList())
//                .build();
//
//        return ApiResponse.ofSuccess(responseVo);
//    }
}