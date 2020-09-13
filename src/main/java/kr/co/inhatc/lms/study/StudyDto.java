package kr.co.inhatc.lms.study;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class StudyDto {

    @NotBlank @Length(max = 50)
    private String title;

    private String post;
}
