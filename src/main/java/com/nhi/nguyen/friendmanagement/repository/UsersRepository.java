package com.nhi.nguyen.friendmanagement.repository;

import com.nhi.nguyen.friendmanagement.entity.UsersEntity;
import com.nhi.nguyen.friendmanagement.entity.UsersRelationshipEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
public interface UsersRepository extends ReactiveCrudRepository<UsersEntity, Long> {
    Mono<Boolean> existsByEmail(String email);

    Mono<UsersEntity> findByEmail(String email);

    @Query("select u.* " +
            "from productdb.friend_management.users_relationship ur " +
            "inner join productdb.friend_management.users u on u.id = ur.related_user_id " +
            "where ur.type = $1 " +
            "and ur.user_id = $2 ")
    Flux<UsersEntity> FindByRelationTypeAndEmail( Integer type, Integer id);

    @Query("select friend2.* " +
            "from productdb.friend_management.users_relationship ur2 " +
            "inner join productdb.friend_management.users friend2 on ur2.related_user_id = friend2.id " +
            "where ur2.user_id = $2 and ur2.type = $3 and " +
            "ur2.related_user_id IN (select friend.id from productdb.friend_management.users_relationship ur  " +
            "     inner join productdb.friend_management.users friend on ur.related_user_id = friend.id " +
            "     where ur.user_id = $1 and ur.type = $3 ) ")
    Flux<UsersEntity> FindByRelationTypeAndTwoEmail(Integer id, Integer id1, Integer type);


    @Query("select u2.* " +
            "from productdb.friend_management.users u " +
            "inner join productdb.friend_management.users_relationship ur on u.id = ur.related_user_id " +
            "inner join productdb.friend_management.users u2 on u2.id = ur.user_id " +
            "where (ur.type = $1 or ur.type = $2) " +
            "and u.email = $3 ")
    Flux<UsersEntity> getAllUserReceiveUpdateFromAnEmail( Integer type, Integer type2, String email);

    @Query("select u2.* " +
            "from productdb.friend_management.users u " +
            "inner join productdb.friend_management.users_relationship ur on u.id = ur.related_user_id " +
            "inner join productdb.friend_management.users u2 on u2.id = ur.user_id " +
            "where ur.type = $1 " +
            "and u.email = $2 ")
    Flux<UsersEntity> getAllUserBlockAnEmail( Integer type,String email);

}