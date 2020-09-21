package kr.co.inhatc.lms.study;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDateTime;

@Component
public class StudyValidator implements Validator  {

    @Override
    public boolean supports(Class<?> clazz) {
        return StudyDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        StudyDto studyDto = (StudyDto) target;

        if (isNotValidEndDateTime(studyDto)) {
            errors.rejectValue("endDateTime", "wrong.datetime", "강의 종료 일시를 정확히 입력하세요.");
        }
    }

    private boolean isNotValidEndDateTime(StudyDto studyDto) {
        LocalDateTime endDateTime = studyDto.getEndDateTime();
        return endDateTime.isBefore(studyDto.getStartDateTime());
    }
}
