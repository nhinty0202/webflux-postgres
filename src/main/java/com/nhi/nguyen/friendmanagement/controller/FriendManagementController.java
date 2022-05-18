package com.nhi.nguyen.friendmanagement.controller;

import com.nhi.nguyen.friendmanagement.dto.*;
import com.nhi.nguyen.friendmanagement.service.FriendManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class FriendManagementController {
    @Autowired
    FriendManagementService friendManagementService;

    @PostMapping("/add-friend")
    public Mono<AddFriend.Response> addFriend(@RequestBody AddFriend.Request request) {
        return friendManagementService.makeFriend(request);
    }

    @GetMapping("/friends")
    public Mono<RetrieveFriend.Response> getListFriend(@RequestBody RetrieveFriend.Request request) {
        return friendManagementService.getListFriendByEmail(request);
    }

    @GetMapping("/friends-between")
    public Mono<RetrieveFriendBetween.Response> getListFriendBetween(@RequestBody RetrieveFriendBetween.Request request) {
        return friendManagementService.getFriendBetween(request);
    }

    @PostMapping("/subscribe")
    public Mono<AddRelation.Response> subscribeUser(@RequestBody AddRelation.Request request) {
        return friendManagementService.subScribeByEmail(request);
        //  return Mono.just(AddFriend.Response.builder().build());
    }

    @PostMapping("/block")
    public Mono<AddRelation.Response> blockUser(@RequestBody AddRelation.Request request) {
        return friendManagementService.blockUserByEmail(request);
    }

    @GetMapping("/retrieve-update")
    public Mono<RetrieveReceiveUpdate.Response> getReceiveUpdateEmails(@RequestBody RetrieveReceiveUpdate.Request request){
        return friendManagementService.retrieveEmailReceiveUpdate(request);
    }

}
