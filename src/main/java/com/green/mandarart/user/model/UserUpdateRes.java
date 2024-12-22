package com.green.mandarart.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRes {
    private String userId;
    @JsonIgnore
    private String upw;
    private String message;
}
