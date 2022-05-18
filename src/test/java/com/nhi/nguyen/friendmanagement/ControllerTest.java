package com.nhi.nguyen.friendmanagement;

import com.nhi.nguyen.friendmanagement.controller.FriendManagementController;
import com.nhi.nguyen.friendmanagement.dto.*;
import com.nhi.nguyen.friendmanagement.service.FriendManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

@SpringBootTest
public class ControllerTest {
    @InjectMocks
    private FriendManagementController controller;

    @Mock
    private FriendManagementService service;

    private AddFriend.Request addFriendRequest ;

    private RetrieveFriend.Request retrieveFriendRequest;

    private RetrieveFriendBetween.Request retrieveFriendBetweenRequest;

    private RetrieveReceiveUpdate.Request  retrieveReceiveUpdateRequest;

    private AddRelation.Request addRelationRequest;


    @BeforeEach
    public void setUp() {
        addFriendRequest = AddFriend.Request.builder().friends(List.of("abc@gmail.com", "xyz@gmail.com")).build();
        retrieveFriendRequest = RetrieveFriend.Request.builder().email("abc@gmail.com").build();
        retrieveFriendBetweenRequest = RetrieveFriendBetween.Request.builder().friends(List.of("abc@gmail.com", "xyz@gmail.com")).build();
        addRelationRequest =AddRelation.Request.builder().requester("abc@gmail.com").target("xyz@gmail.com").build();
        retrieveReceiveUpdateRequest = RetrieveReceiveUpdate.Request.builder().sender("abc@gmail.com").sender("Hello World! kate@example.com").build();

    }

    @Test
    @DisplayName("create a friend connection between two email addresses")
    public void shouldCreateConnectionBetween2Email() {
        Mockito.when(service.makeFriend(addFriendRequest)).thenReturn(Mono.just(AddFriend.Response.builder().success(true).build()));
        StepVerifier.create(controller.addFriend(addFriendRequest))
                .consumeNextWith(res -> {
                    Assertions.assertNotNull(res.getSuccess());
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("retrieve the friends list for an email address success ")
    public void shouldFriendList() {
        Mockito.when(service.getListFriendByEmail(retrieveFriendRequest)).
                thenReturn(Mono.just(RetrieveFriend.Response.builder()
                        .success(true)
                        .friends(List.of("xyz@gmail.com"))
                        .count(1)
                        .build()));
        StepVerifier.create(controller.getListFriend(retrieveFriendRequest))
                .consumeNextWith(res -> {
                    Assertions.assertNotNull(res.getSuccess());
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("retrieve the common friends list between two email addresses ")
    public void shouldFriendListBetween() {
        Mockito.when(service.getFriendBetween(retrieveFriendBetweenRequest)).
                thenReturn(Mono.just(RetrieveFriendBetween.Response.builder()
                        .success(true)
                        .friends(List.of("xyz@gmail.com"))
                        .count(1)
                        .build()));
        StepVerifier.create(controller.getListFriendBetween(retrieveFriendBetweenRequest))
                .consumeNextWith(res -> {
                    Assertions.assertNotNull(res.getSuccess());
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("subscribe to update from an email address ")
    public void shouldSubscribeUpdate() {
        Mockito.when(service.subScribeByEmail(addRelationRequest)).
                thenReturn(Mono.just(AddRelation.Response.builder()
                        .success(true)
                        .build()));
        StepVerifier.create(controller.subscribeUser(addRelationRequest))
                .consumeNextWith(res -> {
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("block updates from an email address")
    public void shouldBlockUpdate() {
        Mockito.when(service.blockUserByEmail(addRelationRequest)).
                thenReturn(Mono.just(AddRelation.Response.builder()
                        .success(true)
                        .build()));
        StepVerifier.create(controller.blockUser(addRelationRequest))
                .consumeNextWith(res -> {
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("retrieve all email addresses that can receive updates from an email address.")
    public void shouldRetrieveReceiveUpdateEmail() {
        Mockito.when(service.retrieveEmailReceiveUpdate(retrieveReceiveUpdateRequest)).
                thenReturn(Mono.just(RetrieveReceiveUpdate.Response.builder()
                        .success(true)
                        .recipients(List.of("xyz@gmail.com"))
                        .build()));
        StepVerifier.create(controller.getReceiveUpdateEmails(retrieveReceiveUpdateRequest))
                .consumeNextWith(res -> {
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }



}
