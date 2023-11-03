package com.example.community_service.community.presentation;

import com.example.community_service.community.application.CommunityServiceImpl;
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

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/communities")
public class CommunityController {

    private final CommunityServiceImpl communityService;

    /**
     * 서버간 상호작용 시에 요구되는 API이기 때문에 게시판, 게시물 서버와 나중에 얘기해봐야함.
     */
    //todo: 모든 엔티티에 createdAt, updatedAt 추가하기 / 밴 해제 로직 다시 짜기
    // 밴 된 유저가 권한이 요구되는 작업을 수행하려고 할 때 ->
    // 1. 해당 유저가 밴 테이블에 존재하는지 확인 -> 존재할 경우 2.로 이동 / 존재하지 않을 경우 작업 수행 허가
    // 2. 존재한다면 현재 시간과 밴 종료일을 비교 -> 현재일이 밴 종료일이 더 늦다면 밴 해제(밴 데이터 자체를 삭제)
    // 만약 현재 시간이 밴 종료일보다 더 빠르다면 게시물 작성 등 기본적인 커뮤니티 활동을 할 수 없도록 해야함.

    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "커뮤니티 가입")
    @PostMapping("/{communityId}/users/me")
    public ApiResponse<Object> joinCommunity(
            @Valid @RequestBody RequestJoinCommunityVo requestVo, @PathVariable Long communityId) {

        RequestJoinCommunityDto requestDto = RequestJoinCommunityDto.builder()
                .userUuid(requestVo.getUserUuid())
                .build();

        ResponseJoinCommunityDto responseDto = communityService.joinCommunity(requestDto, communityId);

        ResponseJoinCommunityVo responseVo = ResponseJoinCommunityVo.builder()
                .communityId(responseDto.getCommunityId())
                .userUuid(responseDto.getUserUuid())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "커뮤니티 탈퇴")
    @DeleteMapping("/{communityId}/users/me")
    public ApiResponse<Object> leaveCommunity(
            @Valid @RequestBody RequestLeaveCommunityVo requestVo, @PathVariable Long communityId) {

        RequestLeaveCommunityDto requestDto = RequestLeaveCommunityDto.builder()
                .userUuid(requestVo.getUserUuid())
                .build();

        ResponseLeaveCommunityDto responseDto = communityService.leaveCommunity(requestDto, communityId);

        ResponseLeaveCommunityVo responseVo = ResponseLeaveCommunityVo.builder()
                .communityId(responseDto.getCommunityId())
                .userUuid(responseDto.getUserUuid())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 가입/탈퇴/조회") @Operation(summary = "가입한 커뮤니티 조회")
    @PostMapping("/users/me")
    public ApiResponse<Object> getJoinedCommunityList(
            @Valid @RequestBody RequestGetJoinedCommunityListVo requestVo,
            @RequestParam(defaultValue = "10") Integer count, @RequestParam(defaultValue = "0") Integer page) {

        RequestGetJoinedCommunityListDto requestDto = RequestGetJoinedCommunityListDto.builder()
                .userUuid(requestVo.getUserUuid())
                .build();

        ResponseGetJoinedCommunityListDto responseDto =
                communityService.getJoinedCommunityList(requestDto, count, page);

        ResponseGetJoinedCommunityListVo responseVo = ResponseGetJoinedCommunityListVo.builder()
                .communityList(responseDto.getCommunityList())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 관리") @Operation(summary = "커뮤니티 생성")
    @PostMapping("")
    public ApiResponse<Object> createCommunity(
            @Valid @RequestBody RequestCreateCommunityVo requestVo) throws JsonProcessingException {

        RequestCreateCommunityDto requestDto = RequestCreateCommunityDto.builder()
                .communityName(requestVo.getCommunityName())
                .description(requestVo.getDescription())
                .token(requestVo.getToken())
                .ownerUuid(requestVo.getOwnerUuid())
                .isCreator(requestVo.getIsCreator())
                .build();

        ResponseCreateCommunityDto responseDto = communityService.createCommunity(requestDto);

        ResponseCreateCommunityVo responseVo = ResponseCreateCommunityVo.builder()
                .communityId(responseDto.getCommunityId())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 관리") @Operation(summary = "커뮤니티 정보 수정")
    @PutMapping("/{communityId}/info")
    public ApiResponse<Object> updateCommunity(
            @Valid @RequestBody RequestUpdateCommunityVo requestVo, @PathVariable Long communityId) {

        RequestUpdateCommunityDto requestDto = RequestUpdateCommunityDto.builder()
                .communityName(requestVo.getCommunityName())
                .description(requestVo.getDescription())
                .profileImage(requestVo.getProfileImage())
                .bannerImage(requestVo.getBannerImage())
                .ownerUuid(requestVo.getOwnerUuid())
                .build();

        ResponseUpdateCommunityDto responseDto =
                communityService.updateCommunity(requestDto, communityId);

        ResponseUpdateCommunityVo responseVo = ResponseUpdateCommunityVo.builder()
                .communityId(responseDto.getCommunityId())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 관리") @Operation(summary = "커뮤니티 가입 회원 조회")
    @PostMapping("/{communityId}/members/list")
    public ApiResponse<Object> getCommunityMemberList(
            @Valid @RequestBody RequestGetCommunityMemberListVo requestVo, @PathVariable Long communityId) {

        RequestGetCommunityMemberListDto requestDto = RequestGetCommunityMemberListDto.builder()
                .managerUuid(requestVo.getManagerUuid())
                .build();

        ResponseGetCommunityMemberListDto responseDto =
                communityService.getCommunityMemberList(requestDto, communityId);

        ResponseGetCommunityMemberListVo responseVo = ResponseGetCommunityMemberListVo.builder()
                .communityMemberList(responseDto.getCommunityMemberList())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴")
    @PostMapping("/{communityId}/ban-users")
    public ApiResponse<Object> banUser(
            @Valid @RequestBody RequestBanUserVo requestVo, @PathVariable Long communityId) {

        RequestBanUserDto requestDto = RequestBanUserDto.builder()
                .targetUuid(requestVo.getTargetUuid())
                .banEndDate(requestVo.getBanEndDate())
                .managerUuid(requestVo.getManagerUuid())
                .build();

        ResponseBanUserDto responseDto = communityService.banUser(requestDto, communityId);

        ResponseBanUserVo responseVo = ResponseBanUserVo.builder()
                .bannedUserUuid(responseDto.getBannedUserUuid())
                .banEndDate(responseDto.getBanEndDate())
                .communityId(responseDto.getCommunityId())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴 종료일 수정")
    @PutMapping("/{communityId}/ban-users")
    public ApiResponse<Object> updateBanEndDate(
            @Valid @RequestBody RequestUpdateBanEndDateVo requestVo, @PathVariable Long communityId) {

        RequestUpdateBanEndDateDto requestDto = RequestUpdateBanEndDateDto.builder()
                .targetUuid(requestVo.getTargetUuid())
                .banEndDate(requestVo.getBanEndDate())
                .managerUuid(requestVo.getManagerUuid())
                .build();

        ResponseUpdateBanEndDateDto responseDto = communityService.updateBanEndDate(requestDto, communityId);

        ResponseUpdateBanEndDateVo responseVo = ResponseUpdateBanEndDateVo.builder()
                .bannedUserUuid(responseDto.getBannedUserUuid())
                .banEndDate(responseDto.getBanEndDate())
                .communityId(responseDto.getCommunityId())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴 해제")
    @DeleteMapping("/{communityId}/ban-users")
    public ApiResponse<Object> unbanUser(
            @Valid @RequestBody RequestUnbanUserVo requestVo, @PathVariable Long communityId) {

        RequestUnbanUserDto requestDto = RequestUnbanUserDto.builder()
                .targetUuid(requestVo.getTargetUuid())
                .managerUuid(requestVo.getManagerUuid())
                .build();

        ResponseUnbanUserDto responseDto = communityService.unbanUser(requestDto, communityId);

        ResponseUnbanUserVo responseVo = ResponseUnbanUserVo.builder()
                .unbannedUuid(responseDto.getUnbannedUuid())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 밴 유저 목록 조회")
    @PostMapping("/{communityId}/ban-users/list")
    public ApiResponse<Object> getBannedUserList(
            @Valid @RequestBody RequestGetBannedUserListVo requestVo, @PathVariable Long communityId) {

        RequestGetBannedUserListDto requestDto = RequestGetBannedUserListDto.builder()
                .managerUuid(requestVo.getManagerUuid())
                .build();

        ResponseGetBannedUserListDto responseDto = communityService.getBannedUserList(requestDto, communityId);

        ResponseGetBannedUserListVo responseVo = ResponseGetBannedUserListVo.builder()
                .bannedUserList(responseDto.getBannedUserList())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 매니저 관리") @Operation(summary = "커뮤니티 매니저 등록")
    @PostMapping("/{communityId}/managers")
    public ApiResponse<Object> registerManager(
            @Valid @RequestBody RequestRegisterManagerVo requestVo, @PathVariable Long communityId) {

        RequestRegisterManagerDto requestDto = RequestRegisterManagerDto.builder()
                .creatorUuid(requestVo.getCreatorUuid())
                .targetUuid(requestVo.getTargetUuid())
                .build();

        ResponseRegisterManagerDto responseDto = communityService.registerManager(requestDto, communityId);

        ResponseRegisterManagerVo responseVo = ResponseRegisterManagerVo.builder()
                .managerUuid(responseDto.getManagerUuid())
                .communityId(responseDto.getCommunityId())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 매니저 관리") @Operation(summary = "커뮤니티 매니저 삭제")
    @DeleteMapping("/{communityId}/managers")
    public ApiResponse<Object> deleteManager(
            @Valid @RequestBody RequestDeleteManagerVo requestVo, @PathVariable Long communityId) {

        RequestDeleteManagerDto requestDto = RequestDeleteManagerDto.builder()
                .creatorUuid(requestVo.getCreatorUuid())
                .targetUuid(requestVo.getTargetUuid())
                .build();

        ResponseDeleteManagerDto responseDto = communityService.deleteManager(requestDto, communityId);

        ResponseDeleteManagerVo responseVo = ResponseDeleteManagerVo.builder()
                .managerUuid(responseDto.getManagerUuid())
                .communityId(responseDto.getCommunityId())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }

    @Tag(name = "커뮤니티 매니저 관리") @Operation(summary = "커뮤니티 매니저 목록 조회")
    @PostMapping("/{communityId}/managers/list")
    public ApiResponse<Object> getManagerList(
            @Valid @RequestBody RequestGetManagerListVo requestVo, @PathVariable Long communityId) {

        RequestGetManagerListDto requestDto = RequestGetManagerListDto.builder()
                .creatorUuid(requestVo.getCreatorUuid())
                .build();

        ResponseGetManagerListDto responseDto = communityService.getManagerList(requestDto, communityId);

        ResponseGetManagerListVo responseVo = ResponseGetManagerListVo.builder()
                .managerList(responseDto.getManagerList())
                .build();

        return ApiResponse.ofSuccess(responseVo);
    }
}