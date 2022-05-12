package com.nhi.nguyen.friendmanagement.controller;

import com.nhi.nguyen.friendmanagement.dto.*;
import com.nhi.nguyen.friendmanagement.entity.UsersEntity;
import com.nhi.nguyen.friendmanagement.service.FriendManagementService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
public class FriendManagementController {
    @Autowired
    FriendManagementService friendManagementService;


    @GetMapping("/friends")
    @ResponseBody
    public Mono<RetrieveFriend.Response> getListUser(@RequestBody RetrieveFriend.Request request) {
        return friendManagementService.getListFriendByEmail(request);
    }
    @GetMapping("/friends-between")
    @ResponseBody
    public Mono<RetrieveFriendBetween.Response> getListUserBetween(@RequestBody RetrieveFriendBetween.Request request) {
        return friendManagementService.getFriendBetween(request);
    }
    @GetMapping("/friends-between-2")
    @ResponseBody
    public Mono<RetrieveFriendBetween.Response> get2(@RequestBody RetrieveFriendBetween.Request request) {
        return friendManagementService.getFriendBetween2(request);
    }

    @PostMapping("/add")
    @ResponseBody
    public Mono<AddFriend.Response> get2(@RequestBody AddFriend.Request request) {
        return friendManagementService.makeFriend(request);
    }

    @PostMapping("/subscribe")
    @ResponseBody
    public Mono<AddRelation.Response> subscribeUser(@RequestBody AddRelation.Request request) {
        return friendManagementService.subScribeByEmail(request);
        //  return Mono.just(AddFriend.Response.builder().build());
    }

    /**
     * block updates from an email address
     */
    @PostMapping("/block")
    @ResponseBody
    public Mono<AddRelation.Response> blockUser(@RequestBody AddRelation.Request request) {
        return friendManagementService.blockUserByEmail(request);
        //  return Mono.just(AddFriend.Response.builder().build());
    }

    @GetMapping("/test")
    @ResponseBody
    public Mono<RetrieveReceiveUpdate.Response> getTest(@RequestBody RetrieveReceiveUpdate.Request request){
        return friendManagementService.retrieveEmailReceiveUpdate(request);
    }

}
