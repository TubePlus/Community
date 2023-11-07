package com.example.community_service.side.application;

import com.example.community_service.side.domain.DropdownContent;
import com.example.community_service.side.infrastructure.DropdownContentRepository;
import com.example.community_service.side.vo.request.DropdownContentCreateRequest;
import com.example.community_service.side.vo.request.ImageLinkRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DropdownServiceImpl implements DropdownService{
    private final DropdownContentRepository dropdownContentRepository;

    // dropdown 만들기
    @Override
    public void createDropdownContent(List<DropdownContentCreateRequest> dropdown, Long communitySideId) {
        System.out.println("dropdown: " + dropdown.get(0).getToggleContent());
        List<DropdownContent> dropdownContents = new ArrayList<>();
        for (DropdownContentCreateRequest request : dropdown) {
            DropdownContent dropdownContent = DropdownContent.builder()
                    .toggleTitle(request.getToggleTitle())
                    .toggleContent(request.getToggleContent())
                    .toggleOrder(request.getToggleOrder())
                    .communitySideId(communitySideId)
                    .build();
            dropdownContents.add(dropdownContent);
        }
        dropdownContentRepository.saveAll(dropdownContents);
    }

    @Override
    public void deleteDropdownContent(List<Long> dropdownContentId) {
        // dropdownContentId를 통해 삭제
        dropdownContentRepository.deleteAllById(dropdownContentId);
    }
}
