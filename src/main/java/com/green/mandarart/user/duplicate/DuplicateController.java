package com.green.mandarart.user.duplicate;

import com.green.mandarart.common.model.ResultResponse;
import com.green.mandarart.user.duplicate.model.DuplicateReq;
import com.green.mandarart.user.duplicate.model.DuplicateRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("user/duplicate")
public class DuplicateController {
    private final DuplicateService duplicateService;

    //이메일 중복체크
    @GetMapping("checkEmail")
    public ResultResponse<Integer> emailChk(@ParameterObject @ModelAttribute DuplicateReq p){
        DuplicateRes res = duplicateService.checkEmail(p.getUserId());

        return ResultResponse.<Integer>builder()
                .resultMessage(res.getMessage())
                .resultData(res.getCheck())
                .build();
    }

    //닉네임 중복체크
    @GetMapping("checkNickName")
    public ResultResponse<Integer> nickNameChk(@ParameterObject @ModelAttribute DuplicateReq p) {
        DuplicateRes res = duplicateService.checkNickName(p.getNickName());

        return ResultResponse.<Integer>builder()
                .resultMessage(res.getMessage())
                .resultData(res.getCheck())
                .build();
    }

}
