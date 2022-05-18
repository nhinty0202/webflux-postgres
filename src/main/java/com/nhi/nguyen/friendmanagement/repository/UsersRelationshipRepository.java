package com.nhi.nguyen.friendmanagement.repository;

import com.nhi.nguyen.friendmanagement.entity.UserRelationshipEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsersRelationshipRepository extends ReactiveCrudRepository<UserRelationshipEntity, Long> {
    @Query("select count(u.id) " +
            "from productdb.friend_management.users_relationship u " +
            "where (u.type = $3 AND u.user_id = $1 AND u.related_user_id = $2)  " +
            "or (u.type = $4 AND ((u.user_id = $1 and u.related_user_id = $2) or (u.user_id = $2 and u.related_user_id = $1))) limit 1")
    Mono<Integer> checkRelation(Integer userId, Integer relatedId, Integer type, Integer type2);
}
