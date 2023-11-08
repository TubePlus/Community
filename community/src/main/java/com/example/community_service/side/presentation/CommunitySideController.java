package com.example.community_service.side.presentation;

import com.example.community_service.global.base.ApiResponse;
import com.example.community_service.side.application.CommunitySideService;
import com.example.community_service.side.application.DropdownService;
import com.example.community_service.side.application.ImageLinkService;
import com.example.community_service.side.dto.CommunitySideDto;
import com.example.community_service.side.vo.request.*;
import com.example.community_service.side.vo.response.GetCommunitySideResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/communities")
public class CommunitySideController {
    private final CommunitySideService communitySideService;
    private final DropdownService dropdownService;
    private final ImageLinkService imageLinkService;
    @Transactional
    @Tag(name = "커뮤니티 사이드") @Operation(summary = "커뮤니티 사이드 드롭다운 생성")
    @PostMapping("/{communityId}/side/r/dropdown")
    public ApiResponse<Object> createCommunitySideDropdown(
            @RequestParam Long communityId,
            @RequestBody CommunitySideCreateDropdownRequest request){
        CommunitySideDto communitySideDto = CommunitySideDto.builder()
                .sideOrder(request.getSideOrder())
                .sideType(request.getSideType())
                .title(request.getTitle())
                .communityId(communityId)
                .build();
        Long communitySideId = communitySideService.createCommunitySide(communitySideDto);
        dropdownService.createDropdownContent(request.getDropdown(), communitySideId);
        return ApiResponse.ofSuccess();
    }
    @Transactional
    @Tag(name = "커뮤니티 사이드") @Operation(summary = "커뮤니티 사이드 이미지 생성")
    @PostMapping("/{communityId}/side/r/image")
    public ApiResponse<Object> createCommunitySideImage(
            @PathVariable Long communityId,
            @RequestBody CommunitySideCreateImageRequest request){
        CommunitySideDto communitySideDto = CommunitySideDto.builder()
                .sideOrder(request.getSideOrder())
                .sideType(request.getSideType())
                .title(request.getTitle())
                .communityId(communityId)
                .build();
        Long communitySideId = communitySideService.createCommunitySide(communitySideDto);
        imageLinkService.createImageData(request.getImageLink(), communitySideId);
        return ApiResponse.ofSuccess();
    }
//    수정 기능 추가할지 추후 결정
//    @Tag(name = "커뮤니티 사이드") @Operation(summary = "커뮤니티 사이드 드롭다운 수정")
//    @PutMapping("/{communityId}/side/{communitySideId}/r/dropdown")
//    public ApiResponse<Object> updateCommunitySideDropdown(
//            @PathVariable Long communityId,
//            @PathVariable Long communitySideId,
//            @RequestBody CommunitySideUpdateDropdownRequest request){
//        CommunitySideDto communitySideDto = CommunitySideDto.builder()
//                .sideOrder(request.getSideOrder())
//                .sideType(request.getSideType())
//                .title(request.getTitle())
//                .communityId(communityId)
//                .build();
//        communitySideService.updateCommunitySide(communitySideId, communitySideDto, communityId);
//
//        return ApiResponse.ofSuccess();
//    }
    @Tag(name = "커뮤니티 사이드") @Operation(summary = "커뮤니티 사이드 드롭다운 삭제")
    @DeleteMapping("/{communityId}/side/{communitySideId}/r/dropdown")
    public ApiResponse<Object> deleteCommunitySideDropdown(
            @PathVariable Long communityId,
            @PathVariable Long communitySideId,
            @RequestBody DropdownSideDeleteRequest request
    ){
        communitySideService.deleteCommunitySide(communitySideId, communityId);
        dropdownService.deleteDropdownContent(request.getDropdownContentId());
        return ApiResponse.ofSuccess();
    }
    @Tag(name = "커뮤니티 사이드") @Operation(summary = "커뮤니티 사이드 이미지 삭제")
    @DeleteMapping("/{communityId}/side/{communitySideId}/r/image")
    public ApiResponse<Object> deleteCommunitySideImage(
            @PathVariable Long communityId,
            @PathVariable Long communitySideId,
            @RequestBody ImageLinkDeleteRequest request
    ){
        communitySideService.deleteCommunitySide(communitySideId, communityId);
        imageLinkService.deleteImageData(request.getImageLinkId());
        return ApiResponse.ofSuccess();
    }

    @Tag(name = "커뮤니티 사이드") @Operation(summary = "커뮤니티 사이드 정보 조회")
    @GetMapping("/{communityId}/side/r")
    public ApiResponse<Object> getCommunitySide(
            @PathVariable Long communityId
    ){
        return ApiResponse.ofSuccess(communitySideService.getCommunitySide(communityId));
    }
}
