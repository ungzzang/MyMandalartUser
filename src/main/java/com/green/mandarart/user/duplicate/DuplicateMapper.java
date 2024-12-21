package com.green.mandarart.user.duplicate;

import com.green.mandarart.user.duplicate.model.DuplicateReq;
import com.green.mandarart.user.duplicate.model.DuplicateRes;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DuplicateMapper {
    DuplicateRes checkEmail(String p);
    DuplicateRes checkNickName(String p);
}
