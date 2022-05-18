package com.nhi.nguyen.friendmanagement.service.impl;

import com.nhi.nguyen.friendmanagement.contant.MessageConstant;
import com.nhi.nguyen.friendmanagement.contant.StringConstant;
import com.nhi.nguyen.friendmanagement.dto.*;
import com.nhi.nguyen.friendmanagement.entity.UserEntity;
import com.nhi.nguyen.friendmanagement.entity.UserRelationshipEntity;
import com.nhi.nguyen.friendmanagement.repository.UsersRelationshipRepository;
import com.nhi.nguyen.friendmanagement.repository.UsersRepository;
import com.nhi.nguyen.friendmanagement.service.FriendManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class FriendManagementServiceImpl implements FriendManagementService {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    UsersRelationshipRepository usersRelationshipRepository;


    @Override
    public Flux<UserEntity> getAllUser() {
        return usersRepository.findAll();
    }

    /**
     * create a friend connection between two email addresses
     */
    @Override
    public Mono<AddFriend.Response> makeFriend(AddFriend.Request request) {
        Mono<UserRelationshipEntity> rela1 =this.addRelation(request.getFriends().get(0),request.getFriends().get(1),
                StringConstant.FRIEND_TYPE, StringConstant.BLOCK_TYPE, MessageConstant.CAN_NOT_ADD_FRIEND);
        Mono<UserRelationshipEntity> rela2 =this.addRelation(request.getFriends().get(1),request.getFriends().get(0),
                StringConstant.FRIEND_TYPE, StringConstant.BLOCK_TYPE, MessageConstant.CAN_NOT_ADD_FRIEND);
        return rela1.zipWith(rela2)
                .map(p-> AddFriend.Response.builder().success(true).build());
    }

    /**
     * retrieve the friends list for an email address
     */
    @Override
    public Mono<RetrieveFriend.Response> getListFriendByEmail(RetrieveFriend.Request request) {
        return usersRepository.findByEmail(request.getEmail())
                .filter(Objects::nonNull)
                .flatMap(p-> usersRepository.findByRelationTypeAndEmail(StringConstant.FRIEND_TYPE,p.getId())
                        .map(UserEntity::getEmail).collectList()
                        .map(list-> RetrieveFriend.Response.builder().success(true).friends(list).count(list.size()).build()))
                .switchIfEmpty(monoResponseStatusNotFoundException(request.getEmail()));
    }
    /**
     * retrieve the common friends list between two email addresses
     */
    @Override
    public Mono<RetrieveFriendBetween.Response> getFriendBetween(RetrieveFriendBetween.Request request){

        return usersRepository.findByEmail(request.getFriends().get(0))
                .filter(Objects::nonNull)
                .flatMap(p-> usersRepository.findByRelationTypeAndEmail(StringConstant.FRIEND_TYPE,p.getId())
                        .map(UserEntity::getEmail)
                        .collectList().zipWith(usersRepository.findByEmail(request.getFriends().get(1))
                                .filter(Objects::nonNull)
                                .flatMap(z-> usersRepository.findByRelationTypeAndEmail(StringConstant.FRIEND_TYPE,z.getId())
                                        .map(UserEntity::getEmail)
                                        .collectList())
                                .switchIfEmpty(monoResponseStatusNotFoundException(request.getFriends().get(1))))
                        .map(tuple2 ->{
                            List<String> commonElements = new ArrayList<>(tuple2.getT2());
                            commonElements.retainAll(tuple2.getT1());
                            return commonElements;
                        }).map(k-> RetrieveFriendBetween.Response.builder().friends(k).success(true).count(k.size()).build()))
                .switchIfEmpty(monoResponseStatusNotFoundException(request.getFriends().get(0)));
    }

    /**
     * subscribe to update from an email address
     */
    @Override
    public Mono<AddRelation.Response> subScribeByEmail(AddRelation.Request request){
        return this.addRelation(request.getRequester(),request.getTarget(), StringConstant.SUBSCRIBE_TYPE, StringConstant.BLOCK_TYPE, MessageConstant.CAN_NOT_SUBSCRIBE_USER)
                .map(p-> AddRelation.Response.builder().success(true).build());
    }

    /**
     * block updates from an email address
     */
    @Override
    public Mono<AddRelation.Response> blockUserByEmail(AddRelation.Request request){
        return this.addRelation(request.getRequester(),request.getTarget(), StringConstant.BLOCK_TYPE,0, MessageConstant.CAN_NOT_BLOCK_USER)
                .map(p-> AddRelation.Response.builder().success(true).build());
    }

    /**
     * add relation updates by two email addresses and type relation
     */
    private  Mono<UserRelationshipEntity> addRelation (String email1, String email2, Integer type1, Integer type2, String message) {

        if(email1.equals(email2)){
            return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageConstant.DUPLICATE_EMAIL));
        }else {
            return usersRepository.findByEmail(email1)
                    .filter(Objects::nonNull)
                    .flatMap(p-> usersRepository.findByEmail(email2)
                            .filter(Objects::nonNull)
                            .flatMap(z -> usersRelationshipRepository.checkRelation(p.getId(), z.getId(), type1, type2)
                                    .filter(count -> count == 0)
                                    .flatMap(c-> Mono.just(UserRelationshipEntity.builder()
                                            .userId(p.getId())
                                            .relatedUserId(z.getId())
                                            .type(type1).build()) )
                                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, message))))
                            .flatMap(n -> usersRelationshipRepository.save(n)))
                            .switchIfEmpty(monoResponseStatusNotFoundException(email2))
                    .switchIfEmpty(monoResponseStatusNotFoundException(email1));
        }
    }
    private Mono<List<String>> getEmailFromString(String text){
        List<String> list = new ArrayList<>();
        Matcher m = Pattern.compile(StringConstant.REGEX_CHECK_EMAIL).matcher(text);
        while (m.find())
        {
            list.add(m.group());
        }
        return Mono.just(list);
    }

    /**
     * retrieve all email addresses that can receive updates from an email address.
     */
    @Override
    public Mono<RetrieveReceiveUpdate.Response> retrieveEmailReceiveUpdate(RetrieveReceiveUpdate.Request request){

        return usersRepository.findByEmail(request.getSender())
                .filter(Objects::nonNull)
                .flatMap(k -> usersRepository.getAllUserBlockAnEmail(StringConstant.BLOCK_TYPE,k.getEmail())
                        .map(UserEntity::getEmail).collectList()
                        .zipWith(getEmailFromString(request.getText())
                                .zipWith(usersRepository.getAllUserReceiveUpdateFromAnEmail(StringConstant.FRIEND_TYPE, StringConstant.SUBSCRIBE_TYPE,request.getSender())
                                        .map(UserEntity::getEmail).collectList()))
                        .map(data ->{
                            List<String> list = new ArrayList<>(data.getT2().getT1());
                            List<String> list1 = new ArrayList<>(data.getT2().getT2());
                            list1.removeAll(list);
                            list.addAll(list1);
                            list.removeAll(data.getT1());
                            return list;
                        }).map(p-> RetrieveReceiveUpdate.Response.builder().recipients(p).success(true).build()))
                .switchIfEmpty(monoResponseStatusNotFoundException(request.getSender()));
    }
    private  <T>Mono<T> monoResponseStatusNotFoundException(String email){
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, MessageConstant.EMAIL_NOT_FOUND + email));
    }


}
