package com.nhi.nguyen.friendmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Id;

@Table("users_relationship")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRelationshipEntity {
    @Id
    private Integer id;

    @Column("user_id")
    private Integer userId;

    @Column("type")
    private Integer type;

    @Column("related_user_id")
    private Integer relatedUserId;
}
