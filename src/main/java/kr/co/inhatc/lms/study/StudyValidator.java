package kr.co.inhatc.lms.study;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class StudyValidator implements Validator  {

    @Override
    public boolean supports(Class<?> clazz) {
        return StudyForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudyForm studyForm = (StudyForm) target;

        if (isNotValidEndDateTime(studyForm)) {
            errors.rejectValue("endDateTime", "wrong.datetime", "강의 종료 일시를 정확히 입력하세요.");
        }

    }

    private boolean isNotValidEndDateTime(StudyForm studyForm) {
        LocalDateTime endDateTime = studyForm.getEndDateTime();
        return endDateTime.isBefore(studyForm.getStartDateTime());
    }


}
