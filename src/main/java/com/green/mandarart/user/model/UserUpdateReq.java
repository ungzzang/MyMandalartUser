package com.green.mandarart.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateReq {
    @Schema(title = "유저이메일", requiredMode = Schema.RequiredMode.REQUIRED)
    private String userId;
    @Schema(title = "기존비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @Schema(title = "신규비밀번호")
    private String newUpw;
    @Schema(title = "비밀번호확인")
    private String checkUpw;
    @Schema(title = "바꿀닉네임", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickName;
    private String pic;
}
