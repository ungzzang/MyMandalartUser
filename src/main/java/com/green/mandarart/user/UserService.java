package com.green.mandarart.user;

import com.green.mandarart.common.MyFileUtils;
import com.green.mandarart.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    private final MyFileUtils myFileUtils;

    //회원가입
    public int postSignUp(MultipartFile pic, UserSignUpReq p){
        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);
        String hashedPassWord = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());

        p.setPic(savedPicName);
        p.setUpw(hashedPassWord);

        int result = userMapper.insUser(p);
        if(pic == null){
            return result;
        }

        String userId = p.getUserId();
        String middlePath = String.format("user/%s", userId); //userId = 이메일
        myFileUtils.makeFolders(middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);

        try{
            myFileUtils.transferTo(pic, filePath);
        }catch (IOException e){
            e.printStackTrace();
        }
        return result;

    }

    //로그인
    public UserSignInRes postSignIn(UserSignInReq p){
        UserSignInRes res = userMapper.selUser(p);

        if(res == null || !BCrypt.checkpw(p.getUpw(), res.getUpw())){
            res = new UserSignInRes();
            res.setMessage("아이디 혹은 비밀번호를 확인해 주십시오.");
            return res;
        }

        res.setMessage("로그인성공");
        return res;
    }

    //회원정보조회
    public UserInfoGetRes getUserInfo(UserInfoGetReq p){
       return userMapper.selUserInfo(p);
    }
}
