package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class LectureController {
    private final ModelMapper modelMapper;
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;

    @GetMapping("/new-lecture")
    public String newStudyForm(@CurrentUser Account account, Model model) {
        model.addAttribute(account);
        model.addAttribute(new LectureForm());
        return "lecture/form";
    }
    @PostMapping("/new-lecture")
    public String newStudySubmit(@CurrentUser Account account, LectureForm lectureForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "lecture/form";
        }
        Lecture createLecture = lectureService.createLecture(modelMapper.map(lectureForm, Lecture.class),account);
        return "redirect:/lecture/" + URLEncoder.encode(createLecture.getPath(), StandardCharsets.UTF_8);
    }

    @GetMapping("/lecture/{path}")
    public String viewLecture(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = lectureRepository.findByPath(path);
        if (lecture == null) {
            throw new IllegalArgumentException(path + "에 해당하는 강의가 없습니다.");
        }
        model.addAttribute(account);
        model.addAttribute(lecture);
        return "lecture/view";
    }
}
