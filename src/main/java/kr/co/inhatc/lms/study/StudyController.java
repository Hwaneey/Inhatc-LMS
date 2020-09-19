package kr.co.inhatc.lms.study;

import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import kr.co.inhatc.lms.lecture.Lecture;
import kr.co.inhatc.lms.lecture.LectureRepository;
import kr.co.inhatc.lms.lecture.LectureService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final LectureService lectureService;
    private final StudyService studyService;
    private final StudyRepository studyRepository;
    private final LectureRepository lectureRepository;
    private final ModelMapper modelMapper;

    @GetMapping("/lecture/{path}/createStudy")
    public String createStudy(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = lectureService.getStudyToUpdateStatus(account, path);
        model.addAttribute("lectureManagerOf",
                lectureRepository.findFirst5ByLecturerContaining(account));
        model.addAttribute(lecture);
        model.addAttribute(account);
        model.addAttribute(new StudyDto());
        return "study/form";
    }

    @PostMapping("/lecture/{path}/createStudy")
    public String newEventSubmit(@CurrentUser Account account, @PathVariable String path,
                                 @Valid StudyDto studyDto, Errors errors, Model model) {
        Lecture lecture = lectureService.getStudyToUpdateStatus(account, path);
        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(lecture);
            return "study/form";
        }

        Study study = studyService.createStudy(modelMapper.map(studyDto, Study.class), lecture, account);
        return "redirect:/study/" + lecture.getEncodedPath() + "/events/" + study.getId();
    }

    @GetMapping("/study/{path}/events/{id}")
    public String getEvent(@CurrentUser Account account, @PathVariable String path, @PathVariable Long id, Model model) {
        model.addAttribute(account);
        model.addAttribute(studyRepository.findById(id).orElseThrow());
        model.addAttribute(lectureService.getStudy(path));

        Lecture lecture = studyService.getStudy(path);
        List<Study> study = studyRepository.findByLectureOrderByStartDateTime(lecture);
        model.addAttribute("studys",study);
        return "study/view";

    }

    @GetMapping("/study/{path}/events")
    public String viewStudyEvents(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = studyService.getStudy(path);
        model.addAttribute(account);
        model.addAttribute(lecture);

        List<Study> study = studyRepository.findByLectureOrderByStartDateTime(lecture);
        List<Study> newEvents = new ArrayList<>();
        List<Study> oldEvents = new ArrayList<>();
        study.forEach(e -> {
            if (e.getEndDateTime().isBefore(LocalDateTime.now())) {
                oldEvents.add(e);
            } else {
                newEvents.add(e);
            }
        });

        model.addAttribute("newEvents", newEvents);
        model.addAttribute("oldEvents", oldEvents);

        return "study/events";
    }

}
