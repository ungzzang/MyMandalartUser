package com.green.mandarart.user;

import com.green.mandarart.common.MyFileUtils;
import com.green.mandarart.user.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.dsig.SignedInfo;
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

    //회원정보수정
    public int putUser(UserUpdateReq p){
        // 이메일, 비밀번호 일치 여부 확인
        UserSignInRes res = new UserSignInRes();
        if(p.getUserId() != res.getUserId()){
            UserUpdateRes userUpdateRes = new UserUpdateRes();
            userUpdateRes.setMessage("이메일이 일치하지않습니다.");
            return 0;
        }

        if(!BCrypt.checkpw(p.getUpw(), res.getUpw())){
            UserUpdateRes userUpdateRes = new UserUpdateRes();
            userUpdateRes.setMessage("비밀번호가 일치하지않습니다.");
            return 0;
        }

        if(p.getUpw() != p.getNewUpw()) {
            UserUpdateRes userUpdateRes = new UserUpdateRes();
            userUpdateRes.setMessage("비밀번호를 다시 입력해주십시오.");
            return 0;
        }

        // 저장할 파일명(랜덤명 파일명) 생성
        String savedPicName = (p.getPic() != null ? myFileUtils.makeRandomFileName(p.getPic()) : null);
        p.setPicName(savedPicName);

        if(p.getPic() != null) {
            // 폴더 만들기
            String folderPath = String.format("user/%s", p.getUserId());
            myFileUtils.makeFolders(folderPath);

            // 기존 파일 삭제
            String deletePath = String.format("%s/user/%s", myFileUtils.getUploadPath(), p.getUserId());
            myFileUtils.deleteFolder(deletePath, false);

            // 원하는 위치에 저장할 파일명으로 파일을 이동(transferTo)
            String userId = p.getUserId();
            String filePath = String.format("user/%s/%s", userId, savedPicName);

            try {
                myFileUtils.transferTo(p.getPic(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // DB에 튜플을 수정(Update)
        int result = userMapper.updUser(p);

        UserUpdateRes userUpdateRes = new UserUpdateRes();
        userUpdateRes.setMessage("회원수정이 완료되었습니다.");
        return result;

    }
}
