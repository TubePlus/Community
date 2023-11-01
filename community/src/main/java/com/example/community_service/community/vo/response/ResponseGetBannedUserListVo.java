package com.example.community_service.community.vo.response;

import com.example.community_service.community.domain.BannedUser;
import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResponseGetBannedUserListVo {

    private List<BannedUser> bannedUserList;
}
