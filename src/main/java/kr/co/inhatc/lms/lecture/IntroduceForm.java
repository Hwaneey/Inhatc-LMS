package kr.co.inhatc.lms.lecture;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class IntroduceForm {
    @NotBlank
    private String introducePost;

}
