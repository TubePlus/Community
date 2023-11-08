package com.example.community_service.community.vo.response;

import lombok.Builder;
import lombok.Getter;

public class ResponseCheckVo {

    @Getter
    @Builder
    public static class JoinHistoryExistsVo {

        private Boolean joinHistoryExists;

        public static JoinHistoryExistsVo formResponseVo(Boolean joinHistoryExists) {

            return JoinHistoryExistsVo.builder()
                    .joinHistoryExists(joinHistoryExists)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class CommunityNameDuplicateVo {

        private Boolean communityNameDuplicate;

        public static CommunityNameDuplicateVo formResponseVo(Boolean communityNameDuplicate) {

            return CommunityNameDuplicateVo.builder()
                    .communityNameDuplicate(communityNameDuplicate)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class CheckCommunityExistenceVo {

        private Boolean communityExists;

        public static CheckCommunityExistenceVo formResponseVo(Boolean communityExists) {

            return CheckCommunityExistenceVo.builder()
                    .communityExists(communityExists)
                    .build();
        }
    }
}
