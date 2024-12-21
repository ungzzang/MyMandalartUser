package com.green.mandarart.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSignInRes {
    private String userId;
    private String nickName;
    private String pic;
    @JsonIgnore
    private String upw;
    @JsonIgnore
    private String message;
}
