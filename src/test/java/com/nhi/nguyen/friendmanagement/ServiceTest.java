package com.nhi.nguyen.friendmanagement;

import com.nhi.nguyen.friendmanagement.contant.StringConstant;
import com.nhi.nguyen.friendmanagement.dto.*;
import com.nhi.nguyen.friendmanagement.entity.UserEntity;
import com.nhi.nguyen.friendmanagement.entity.UserRelationshipEntity;
import com.nhi.nguyen.friendmanagement.repository.UsersRelationshipRepository;
import com.nhi.nguyen.friendmanagement.repository.UsersRepository;
import com.nhi.nguyen.friendmanagement.service.FriendManagementService;
import com.nhi.nguyen.friendmanagement.service.impl.FriendManagementServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ServiceTest {

    @InjectMocks
    private FriendManagementServiceImpl serviceImpl;

    @Mock
    private UsersRepository repository;

    @Mock
    private UsersRelationshipRepository repositoryRela;

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
        retrieveReceiveUpdateRequest = RetrieveReceiveUpdate.Request.builder().sender("abc@gmail.com").text("Hello World! kate@example.com").build();

    }

    @Test
    @DisplayName("create a friend connection between two email addresses")
    public void shouldCreateConnectionBetween2Email() {

        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(1).email("abc@gmail.com").name("User1").build()));
        Mockito.when(repository.findByEmail("xyz@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(2).email("xyz@gmail.com").name("User2").build()));
        Mockito.when(repositoryRela.checkRelation(any(),any(),any(),any()))
                .thenReturn(Mono.just(0));
        Mockito.when(repositoryRela.save(any()))
                .thenReturn(Mono.just(new UserRelationshipEntity(null,1,1,2)));
        StepVerifier.create(serviceImpl.makeFriend(AddFriend.Request
                        .builder().friends(List.of("abc@gmail.com","xyz@gmail.com")).build()))
                .consumeNextWith(res ->{
                    Assertions.assertNotNull(res.getSuccess());
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();

    }
    /**
     *
     */
    @Test
    @DisplayName("retrieve the friends list for an email address")
    public void shouldFriendList() {

        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(1).email("abc@gmail.com").name("User1").build()));
        Mockito.when(repository.findByRelationTypeAndEmail(any(),any()))
                .thenReturn(Flux.just(UserEntity.builder().id(2).email("xyz@gmail.com").name("User2").build()));
        StepVerifier.create(serviceImpl.getListFriendByEmail(RetrieveFriend.Request
                        .builder().email("abc@gmail.com").build()))
                .consumeNextWith(res ->{
                    Assertions.assertNotNull(res.getSuccess());
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();

    }
    @Test
    @DisplayName("retrieve the common friends list between two email addresses ")
    public void shouldFriendListBetween() {
        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(1).email("abc@gmail.com").name("User1").build()));
        Mockito.when(repository.findByRelationTypeAndEmail(StringConstant.FRIEND_TYPE,1))
                        .thenReturn(Flux.just(UserEntity.builder().id(2).email("123@gmail.com").name("User3").build()));
        Mockito.when(repository.findByEmail("xyz@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(2).email("xyz@gmail.com").name("User2").build()));
        Mockito.when(repository.findByRelationTypeAndEmail(StringConstant.FRIEND_TYPE,2))
                .thenReturn(Flux.just(UserEntity.builder().id(2).email("123@gmail.com").name("User3").build()));
        StepVerifier.create(serviceImpl.getFriendBetween(RetrieveFriendBetween.Request
                        .builder().friends(List.of("abc@gmail.com","xyz@gmail.com")).build()))
                .consumeNextWith(res ->{
                    Assertions.assertNotNull(res.getSuccess());
                    Assertions.assertEquals(true, res.getSuccess());
                    Assertions.assertEquals(1, res.getCount());
                    Assertions.assertEquals(List.of("123@gmail.com"), res.getFriends());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("subscribe to update from an email address ")
    public void shouldSubscribeUpdate() {
        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(1).email("abc@gmail.com").name("User1").build()));
        Mockito.when(repository.findByEmail("xyz@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(2).email("xyz@gmail.com").name("User2").build()));
        Mockito.when(repositoryRela.checkRelation(any(),any(),any(),any()))
                .thenReturn(Mono.just(0));
        Mockito.when(repositoryRela.save(any()))
                .thenReturn(Mono.just(new UserRelationshipEntity(null,1,1,2)));
        StepVerifier.create(serviceImpl.subScribeByEmail(addRelationRequest))
                .consumeNextWith(res -> {
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }

    @Test
    @DisplayName("block updates from an email address")
    public void shouldBlockUpdate() {
        Mockito.when(repository.findByEmail("abc@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(1).email("abc@gmail.com").name("User1").build()));
        Mockito.when(repository.findByEmail("xyz@gmail.com"))
                .thenReturn(Mono.just(UserEntity.builder().id(2).email("xyz@gmail.com").name("User2").build()));
        Mockito.when(repositoryRela.checkRelation(any(),any(),any(),any()))
                .thenReturn(Mono.just(0));
        Mockito.when(repositoryRela.save(any()))
                .thenReturn(Mono.just(new UserRelationshipEntity(null,1,1,2)));
        StepVerifier.create(serviceImpl.blockUserByEmail(addRelationRequest))
                .consumeNextWith(res -> {
                    Assertions.assertEquals(true, res.getSuccess());
                })
                .verifyComplete();
    }
    @Test
    @DisplayName("retrieve all email addresses that can receive updates from an email address.")
    public void shouldRetrieveReceiveUpdateEmail() {
        Mockito.when(repository.getAllUserBlockAnEmail(StringConstant.BLOCK_TYPE,"abc@gmail.com"))
                .thenReturn(Flux.just(UserEntity.builder().id(4).email("wer@gmail.com").name("User4").build()));
        Mockito.when(repository.getAllUserReceiveUpdateFromAnEmail(StringConstant.FRIEND_TYPE,StringConstant.SUBSCRIBE_TYPE,"abc@gmail.com"))
                .thenReturn(Flux.just(UserEntity.builder().id(4).email("mnv@gmail.com").name("User4").build()));
        StepVerifier.create(serviceImpl.retrieveEmailReceiveUpdate(retrieveReceiveUpdateRequest))
                .consumeNextWith(res -> {
                    Assertions.assertEquals(true, res.getSuccess());
                    Assertions.assertEquals(List.of("kate@example.com","mnv@gmail.com"), res.getRecipients());
                })
                .verifyComplete();
    }


}

