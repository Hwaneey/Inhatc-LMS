package kr.co.inhatc.lms.lecture;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Data @NoArgsConstructor
public class IntroduceForm {
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String introduce;
}
