package com.nhi.nguyen.friendmanagement.dto;

import com.nhi.nguyen.friendmanagement.entity.UsersRelationshipEntity;
import lombok.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

public interface AddFriend {
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Request {
        List<String> friends;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    class Response  {
        private Boolean success;
    }
}
