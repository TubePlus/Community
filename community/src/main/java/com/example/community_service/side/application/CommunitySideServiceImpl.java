package com.example.community_service.side.application;

import com.example.community_service.side.domain.CommunitySide;
import com.example.community_service.side.domain.DropdownContent;
import com.example.community_service.side.domain.ImageLink;
import com.example.community_service.side.dto.CommunitySideDto;
import com.example.community_service.side.infrastructure.CommunitySideRepository;
import com.example.community_service.side.infrastructure.DropdownContentRepository;
import com.example.community_service.side.infrastructure.ImageLinkRepository;
import com.example.community_service.side.vo.response.GetCommunitySideResponse;
import com.example.community_service.side.vo.response.GetDropdownResponse;
import com.example.community_service.side.vo.response.GetImageLinkResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunitySideServiceImpl implements CommunitySideService {
    private final CommunitySideRepository communitySideRepository;
    private final ImageLinkRepository imageLinkRepository;
    private final DropdownContentRepository dropdownContentRepository;
    // community 만들기
    @Override
    public Long createCommunitySide(CommunitySideDto communitySideDto) {
        CommunitySide communitySide = CommunitySide.builder()
                .sideOrder(communitySideDto.getSideOrder())
                .sideType(communitySideDto.getSideType())
                .title(communitySideDto.getTitle())
                .communityId(communitySideDto.getCommunityId())
                .build();
        communitySideRepository.save(communitySide);
        return communitySide.getId();
    }
    // community 수정하기
    @Override
    public void updateCommunitySide(Long communitySideId, CommunitySideDto communitySideDto, Long communityId) {
        CommunitySide communitySide = CommunitySide.builder()
                .id(communitySideId)
                .sideOrder(communitySideDto.getSideOrder())
                .sideType(communitySideDto.getSideType())
                .title(communitySideDto.getTitle())
                .communityId(communityId)
                .build();
        communitySideRepository.save(communitySide);
    }

    @Override
    public void deleteCommunitySide(Long communitySideId, Long communityId) {
        // communitySideId와 communityId를 통해 삭제 -> 자신의 community 게시글 것만 삭제하도록 확인 로직 추가
        communitySideRepository.deleteByIdAndCommunityId(communitySideId, communityId);
    }

    @Override
    public List<GetCommunitySideResponse> getCommunitySide(Long communityId) {
        // communityId를 통해 communitySide 정보 조회
        // 우선 communityId로 communitySide의 list를 가져온다.
        // 이때 side_order 순서대로
        // communitySideId와 sideType을 통해 switch문으로 imageLink와 dropdown을 가져온다.
        // 가져올때, dropdown은 toggle_order 순서대로 가져온다.
        // ArrayList에 각각의 값들을 넣는다.
        List<CommunitySide> communitySides = communitySideRepository.findAllByCommunityIdOrderBySideOrder(communityId);
        if (communitySides.isEmpty()) {
            throw new NoSuchElementException("커뮤니티 사이드가 존재하지 않습니다.");
        }
        List<GetCommunitySideResponse> communitySideList = new ArrayList<>();
        for (CommunitySide communitySide : communitySides) {
            switch (communitySide.getSideType()) {
                case IMAGE_LINK:
                    // imageLinkService를 통해 이미지 링크를 가져와 communitySideList에 추가하는 로직
                    ImageLink imageLink = imageLinkRepository.findByCommunitySideId(communitySide.getId());
                    GetImageLinkResponse imageLinkResponse = GetImageLinkResponse.builder()
                            .imageLinkId(imageLink.getId())
                            .link(imageLink.getLink())
                            .imageUrl(imageLink.getImageUrl())
                            .build();
                    GetCommunitySideResponse communitySideResponseImg = GetCommunitySideResponse.builder()
                            .sideOrder(communitySide.getSideOrder())
                            .sideType(communitySide.getSideType())
                            .title(communitySide.getTitle())
                            .communityId(communitySide.getCommunityId())
                            .imageLink(imageLinkResponse)
                            .dropdown(null)
                            .build();
                    communitySideList.add(communitySideResponseImg);
                    break;
                case DROPDOWN:
                    // dropdownService를 통해 드롭다운 내용을 가져와 communitySideList에 추가하는 로직
                    List<DropdownContent> dropdownContent = dropdownContentRepository
                            .findAllByCommunitySideIdOrderByToggleOrder(communitySide.getId());
                    List<GetDropdownResponse> dropdown = new ArrayList<>();
                    for (DropdownContent content : dropdownContent) {
                        GetDropdownResponse dropdownResponse = GetDropdownResponse.builder()
                                .dropdownContentId(content.getId())
                                .toggleOrder(content.getToggleOrder())
                                .toggleTitle(content.getToggleTitle())
                                .toggleContent(content.getToggleContent())
                                .build();
                        dropdown.add(dropdownResponse);
                    }
                    GetCommunitySideResponse communitySideResponseDropdown = GetCommunitySideResponse.builder()
                            .sideOrder(communitySide.getSideOrder())
                            .sideType(communitySide.getSideType())
                            .title(communitySide.getTitle())
                            .communityId(communitySide.getCommunityId())
                            .imageLink(null)
                            .dropdown(dropdown)
                            .build();
                    communitySideList.add(communitySideResponseDropdown);
                    break;
                default:
                    throw new IllegalStateException("알 수 없는 SideType입니다.");
            }
        }
        return communitySideList;

    }

}
