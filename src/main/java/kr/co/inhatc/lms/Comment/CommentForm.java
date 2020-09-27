package kr.co.inhatc.lms.Comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentForm {
    @NotBlank
    String content;

    @NotBlank
    Long studyId;
}
