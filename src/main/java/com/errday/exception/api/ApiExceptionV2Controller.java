package com.errday.exception.api;

import com.errday.exception.exception.UserException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiExceptionV2Controller {

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if ("ex".equals(id)) {
            throw new RuntimeException("잘못된 사용자");
        }

        if ("bad".equals(id)) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }

        if ("user-ex".equals(id)) {
            throw new UserException("사용자 오류");
        }

        return new MemberDto(id, "hello " + id);
    }


    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String memberId;
        private String name;
    }
}
