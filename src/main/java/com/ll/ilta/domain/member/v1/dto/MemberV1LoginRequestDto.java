package com.ll.ilta.domain.member.v1.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberV1LoginRequestDto {

    private String username;
    private String password;


}
