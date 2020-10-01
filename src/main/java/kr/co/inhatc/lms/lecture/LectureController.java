package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.Register.Register;
import kr.co.inhatc.lms.Register.RegisterRepository;
import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import kr.co.inhatc.lms.study.StudyRepository;
import kr.co.inhatc.lms.study.StudyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
public class LectureController {
    private final ModelMapper modelMapper;
    private final LectureService lectureService;
    private final LectureRepository lectureRepository;
    private final LectureFormValidator lectureFormValidator;
    private final StudyService studyService;
    private final StudyRepository studyRepository;
    private final RegisterRepository registerRepository;

    private void list(@CurrentUser Account account, Model model) {
        model.addAttribute("lectureManagerOf", lectureRepository.findFirst5ByLecturerContaining(account));
        model.addAttribute("studentManagerOf", lectureRepository.findFirst5ByStudentContaining(account));
    }

    @GetMapping("/new-lecture")
    public String newStudyForm(@CurrentUser Account account, Model model) {
        model.addAttribute(account);
        list(account, model);
        model.addAttribute(new LectureForm());
        return "lecture/form";
    }

    @PostMapping("/new-lecture")
    public String newStudySubmit(@CurrentUser Account account, @Valid LectureForm lectureForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "lecture/form";
        }
        lectureFormValidator.validate(lectureForm,errors);
        if (errors.hasErrors()){
            return "lecture/form";
        }
        Lecture createLecture = lectureService.createLecture(modelMapper.map(lectureForm, Lecture.class),account);
        return "redirect:/lecture/" + URLEncoder.encode(createLecture.getPath(), StandardCharsets.UTF_8);
    }

    @GetMapping("/lecture/{path}")
    public String viewLecture(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = lectureRepository.findByPath(path);
        list(account, model);
        model.addAttribute(account);
        model.addAttribute(lecture);
        return "lecture/view";
    }

    @GetMapping("/lecture/{path}/student")
    public String viewStudyMembers(@CurrentUser Account account, @PathVariable String path, Model model) {
        list(account, model);
        model.addAttribute(account);
        model.addAttribute(lectureRepository.findByPath(path));
        return "lecture/student";
    }

    @GetMapping("/lecture/{path}/register")
    public String registerStudy(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = lectureRepository.findStudyWithStudentByPath(path);
        lectureService.addStudent(lecture, account);
        list(account, model);
        model.addAttribute(account);
        model.addAttribute(lecture);
        return "lecture/view";
    }

    @GetMapping("/lecture/{path}/leave")
    public String leaveStudy(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = lectureRepository.findStudyWithStudentByPath(path);
        lectureService.removeStudent(lecture, account);
        list(account, model);
        model.addAttribute(account);
        model.addAttribute(lecture);
        return "lecture/view";
    }



    @PostMapping("/lecture/{path}/register/{id}")
    public String newRegister(@CurrentUser Account account,@PathVariable String path, @PathVariable Long id) {
        Lecture lecture = lectureService.getLectureToRegister(path);
        lectureService.newRegister(lectureRepository.findById(id).orElseThrow(), account);
        return "redirect:/lecture/" + lecture.getEncodedPath();
    }

    @PostMapping("/lecture/{path}/disRegister/{id}")
    public String cancelEnrollment(@CurrentUser Account account,
                                   @PathVariable String path, @PathVariable Long id) {
        Lecture lecture = lectureService.getLectureToRegister(path);
        lectureService.cancelRegister(lectureRepository.findById(id).orElseThrow(), account);
        return "redirect:/lecture/" + lecture.getEncodedPath();
    }

    @GetMapping("/lecture/{path}/register/{id}/registers/{registerId}/accept")
    public String acceptEnrollment(@CurrentUser Account account, @PathVariable String path,
                                   Model model, @PathVariable Long registerId) {
//        Lecture lecture = lectureService.getStudyToUpdateStatus(path);
//        Lecture lecture = lectureRepository.findById(id).orElseThrow();
        Lecture lecture = lectureRepository.findByPath(path);
        Register register = registerRepository.findById(registerId).orElseThrow();
        lectureService.acceptRegister(lecture, register);
        return "redirect:/lecture/" + lecture.getEncodedPath() + "/student";
    }

    @GetMapping("/lecture/{path}/disRegister/{id}/registers/{registerId}/reject")
    public String rejectEnrollment(@CurrentUser Account account, @PathVariable String path,
                                   Model model, @PathVariable Long registerId) {
//        Study study = studyService.getStudyToUpdate(account, path);
//        Lecture lecture = lectureRepository.findById(id).orElseThrow();
        Lecture lecture = lectureRepository.findByPath(path);
        Register register = registerRepository.findById(registerId).orElseThrow();
        lectureService.rejectRegister(lecture, register);
        return "redirect:/lecture/" + lecture.getEncodedPath() + "/student";
    }
}
