package com.nhi.nguyen.friendmanagement.service;


import com.nhi.nguyen.friendmanagement.dto.*;
import com.nhi.nguyen.friendmanagement.entity.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FriendManagementService {
    Flux<UserEntity> getAllUser();

    /**
     * create a friend connection between two email addresses
     */
    Mono<AddFriend.Response> makeFriend(AddFriend.Request request);


    /**
     * retrieve the friends list for an email address
     */
    Mono<RetrieveFriend.Response> getListFriendByEmail(RetrieveFriend.Request request);

    /**
     * retrieve the common friends list between two email addresses
     */
    Mono<RetrieveFriendBetween.Response> getFriendBetween(RetrieveFriendBetween.Request request);

    /**
     * subscribe to update from an email address
     */
    Mono<AddRelation.Response> subScribeByEmail(AddRelation.Request request);

    /**
     * block updates from an email address
     */
    Mono<AddRelation.Response> blockUserByEmail(AddRelation.Request request);

    Mono<RetrieveReceiveUpdate.Response> retrieveEmailReceiveUpdate(RetrieveReceiveUpdate.Request request);


}
