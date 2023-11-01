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

    @Tag(name = "커뮤니티 밴 유저 관리") @Operation(summary = "커뮤니티 유저 밴")
    @PostMapping("/{communityId}/ban-users")
    public ApiResponse<Object> banUser(
            @Valid @RequestBody RequestBanUserVo requestVo, @PathVariable Long communityId) {

        RequestBanUserDto requestDto = RequestBanUserDto.builder()
                .targetUuid(requestVo.getTargetUuid())
                .banDays(requestVo.getBanDays())
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
}