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

    @PostMapping("/addFriend")
    public Mono<AddFriend.Response> addFriend(@RequestBody AddFriend.Request request) {
        return friendManagementService.makeFriend(request);
    }

    @GetMapping("/friends")
    public Mono<RetrieveFriend.Response> getListFriend(@RequestBody RetrieveFriend.Request request) {
        return friendManagementService.getListFriendByEmail(request);
    }

    @GetMapping("/friendsBetween")
    public Mono<RetrieveFriendBetween.Response> getListFriendBetween(@RequestBody RetrieveFriendBetween.Request request) {
        return friendManagementService.getFriendBetween(request);
    }

    @PostMapping("/subscribe")
    public Mono<AddRelation.Response> subscribeUser(@RequestBody AddRelation.Request request) {
        return friendManagementService.subScribeByEmail(request);
    }

    @PostMapping("/block")
    public Mono<AddRelation.Response> blockUser(@RequestBody AddRelation.Request request) {
        return friendManagementService.blockUserByEmail(request);
    }

    @GetMapping("/retrieveUpdate")
    public Mono<RetrieveReceiveUpdate.Response> getReceiveUpdateEmails(@RequestBody RetrieveReceiveUpdate.Request request){
        return friendManagementService.retrieveEmailReceiveUpdate(request);
    }

}
