package kr.co.inhatc.lms.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {
    @NotBlank
    String comment;

    @NotBlank
    Long studyId;
}
