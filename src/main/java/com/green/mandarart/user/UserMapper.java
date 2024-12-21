package com.green.mandarart.user;

import com.green.mandarart.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int insUser(UserSignUpReq p);
    UserSignInRes selUser(UserSignInReq p);
    UserInfoGetRes selUserInfo(UserInfoGetReq p);
    int updUser(UserUpdateReq p);
}
