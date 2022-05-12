package com.nhi.nguyen.friendmanagement.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
@Data
@Builder
public class UsersEntity {

    @Id
    private Integer id;

    @Column("name")
    private String name;

    @Column("email")
    private String email;

}
