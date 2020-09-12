package kr.co.inhatc.lms.lecture;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor @Component
public class LectureFormValidator implements Validator {
    private final LectureRepository lectureRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return LectureForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        LectureForm lectureForm = (LectureForm) target;
        if (lectureRepository.existsByPath(lectureForm.getPath())) {
            errors.rejectValue("path", "wrong.path", "해당 강의 경로를 사용할수 없습니다");
        }

    }
}
