package com.green.mandarart.user;

import com.green.mandarart.common.model.ResultResponse;
import com.green.mandarart.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user")
@Tag(name = "1. 회원", description = "sign-in / sign-out")
public class UserController {
    private final UserService userService;

    @PostMapping("signUp")
    @Operation(summary = "회원가입")
    public ResultResponse<Integer> signUpUser(@RequestPart(required = false) MultipartFile pic
                                              , @RequestPart UserSignUpReq p){
        int result = userService.postSignUp(pic, p);

        return ResultResponse.<Integer>builder()
                .resultMessage("회원가입이 완료되었습니다.")
                .resultData(result)
                .build();
    }

    @PostMapping("signIn")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> signInUser(@RequestBody UserSignInReq p) {

        UserSignInRes res = userService.postSignIn(p);

        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(res.getMessage())
                .resultData(res)
                .build();
    }

    @GetMapping()
    @Operation(summary = "유저정보조회")
    public ResultResponse<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p){

        UserInfoGetRes res = userService.getUserInfo(p);

        return ResultResponse.<UserInfoGetRes>builder()
                .resultMessage("조회완료")
                .resultData(res)
                .build();
    }


}