package com.nhi.nguyen.friendmanagement.repository;

import com.nhi.nguyen.friendmanagement.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findByEmail(String email);

    @Query("select u.* " +
            "from productdb.friend_management.users_relationship ur " +
            "inner join productdb.friend_management.users u on u.id = ur.related_user_id " +
            "where ur.type = $1 " +
            "and ur.user_id = $2 ")
    Flux<UserEntity> findByRelationTypeAndEmail(Integer type, Integer id);

    @Query("select u2.* " +
            "from productdb.friend_management.users u " +
            "inner join productdb.friend_management.users_relationship ur on u.id = ur.related_user_id " +
            "inner join productdb.friend_management.users u2 on u2.id = ur.user_id " +
            "where (ur.type = $1 or ur.type = $2) " +
            "and u.email = $3 ")
    Flux<UserEntity> getAllUserReceiveUpdateFromAnEmail(Integer type, Integer type2, String email);

    @Query("select u2.* " +
            "from productdb.friend_management.users u " +
            "inner join productdb.friend_management.users_relationship ur on u.id = ur.related_user_id " +
            "inner join productdb.friend_management.users u2 on u2.id = ur.user_id " +
            "where ur.type = $1 " +
            "and u.email = $2 ")
    Flux<UserEntity> getAllUserBlockAnEmail(Integer type, String email);

}