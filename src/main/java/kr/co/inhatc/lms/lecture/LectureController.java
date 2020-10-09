package kr.co.inhatc.lms.lecture;

import kr.co.inhatc.lms.register.Register;
import kr.co.inhatc.lms.register.RegisterRepository;
import kr.co.inhatc.lms.account.Account;
import kr.co.inhatc.lms.account.CurrentUser;
import kr.co.inhatc.lms.account.UserRepository;
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
    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;


    private void list(@CurrentUser Account account, Model model) {
        model.addAttribute("lectureManagerOf", lectureRepository.findFirst20ByLecturerContaining(account));
        model.addAttribute("studentManagerOf", lectureRepository.findFirst20ByStudentContaining(account));
    }

    @GetMapping("/new-lecture")
    public String newStudyForm(@CurrentUser Account account, Model model) {
        model.addAttribute(new LectureForm());
        model.addAttribute(account);
        list(account, model);
        return "lecture/form";
    }

    @PostMapping("/new-lecture")
    public String newStudySubmit(@CurrentUser Account account, @Valid LectureForm lectureForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute(account);
            return "lecture/form";
        }
        lectureFormValidator.validate(lectureForm, errors);
        if (errors.hasErrors()) {
            return "lecture/form";
        }
        Lecture createLecture = lectureService.createLecture(modelMapper.map(lectureForm, Lecture.class), account);
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

    @GetMapping("/lecture/{path}/introduceForm")
    public String newIntroduce(@CurrentUser Account account, @PathVariable String path, Model model) {
        Lecture lecture = lectureRepository.findByPath(path);
        list(account, model);
        model.addAttribute(account);
        model.addAttribute(lecture);
        model.addAttribute(new IntroduceForm());
        return "lecture/introduceForm";
    }

    @PostMapping("/lecture/{path}/introduceForm")
    public String newIntroduceSubmit(@CurrentUser Account account, @PathVariable String path, IntroduceForm introduceForm, Errors errors,Model model) {
        Lecture lecture = lectureService.getLectureToRegister(path);
        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(lecture);
            return "lecture/view";
        }
        lectureService.createIntroduce(lecture,introduceForm);
        return "redirect:/lecture/" + lecture.getEncodedPath();
    }

    @GetMapping("/lecture/{path}/edit")
    public String editIntroduce(@CurrentUser Account account, @PathVariable String path, Model model) {
//        Lecture lecture = lectureRepository.findByPath(path);
        Lecture lecture = lectureService.getLecture(path);
        model.addAttribute(account);
        model.addAttribute(lecture);
        model.addAttribute(modelMapper.map(lecture,IntroduceForm.class));
        list(account, model);
        return "lecture/editIntroduceForm";
    }

    @PostMapping("/lecture/{path}/edit")
    public String editIntroduceSubmit(@CurrentUser Account account, @PathVariable String path,IntroduceForm introduceForm,
                                  Errors errors, Model model) {
//        Lecture lecture = lectureService.getStudyToUpdateStatus(path);
//        Lecture lecture = lectureRepository.findByPath(path);
        Lecture lecture = lectureService.getLecture(path);
        if (errors.hasErrors()) {
            model.addAttribute(account);
            model.addAttribute(lecture);
            return "lecture/view";
        }
        lectureService.editIntroduce(lecture,introduceForm);
        return "redirect:/lecture/" + lecture.getEncodedPath();
    }

    @GetMapping("/lecture/{path}/student")
    public String viewStudyMembers(@CurrentUser Account account, @PathVariable String path, Model model) {
        list(account, model);
        model.addAttribute(account);
        model.addAttribute(lectureRepository.findByPath(path));
        return "lecture/student";
    }

    @PostMapping("/lecture/{path}/register/{id}")
    public String newRegister(@CurrentUser Account account, @PathVariable String path, @PathVariable Long id) {
        Lecture lecture = lectureService.getLectureToRegister(path);
        lectureService.newRegister(lectureRepository.findById(id).orElseThrow(), account);
        return "redirect:/lecture/" + lecture.getEncodedPath();
    }

    @PostMapping("/lecture/{path}/disRegister/{id}")
    public String cancelEnrollment(@CurrentUser Account account, @PathVariable String path, @PathVariable Long id) {
        Lecture lecture = lectureService.getLectureToRegister(path);
        lectureService.cancelRegister(lectureRepository.findById(id).orElseThrow(), account);
        return "redirect:/lecture/" + lecture.getEncodedPath();
    }

    @GetMapping("/lecture/{path}/register/{id}/registers/{registerId}/accept")
    public String acceptEnrollment(@PathVariable String path, @PathVariable Long id, @PathVariable Long registerId) {

        Lecture lecture = lectureRepository.findByPath(path);
        Register register = registerRepository.findById(registerId).orElseThrow();
        Account findAccount = userRepository.findById(id).orElseThrow();

        lectureService.acceptRegister(findAccount, lecture, register);
        return "redirect:/lecture/" + lecture.getEncodedPath() + "/student";
    }

    @GetMapping("/lecture/{path}/disRegister/{id}/registers/{registerId}/reject")
    public String rejectEnrollment(@PathVariable String path, @PathVariable Long id, @PathVariable Long registerId) {

        Lecture lecture = lectureRepository.findByPath(path);
        Register register = registerRepository.findById(registerId).orElseThrow();
        Account findAccount = userRepository.findById(id).orElseThrow();

        lectureService.rejectRegister(findAccount, lecture, register);
        return "redirect:/lecture/" + lecture.getEncodedPath() + "/student";
    }
}
