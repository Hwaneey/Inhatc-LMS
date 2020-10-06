//package kr.co.inhatc.lms.introduce;
//
//import kr.co.inhatc.lms.account.Account;
//import kr.co.inhatc.lms.account.CurrentUser;
//import kr.co.inhatc.lms.lecture.Lecture;
//import kr.co.inhatc.lms.lecture.LectureRepository;
//import kr.co.inhatc.lms.lecture.LectureService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.Errors;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller @RequiredArgsConstructor
//public class IntroduceController {
//
//    private final LectureRepository lectureRepository;
//    private final IntroduceService introduceService;
//    private final LectureService lectureService;
//
//    @GetMapping("/lecture/{path}/introduceForm")
//    public String newIntroduce(@CurrentUser Account account, @PathVariable String path, Model model) {
//        Lecture lecture = lectureRepository.findByPath(path);
//        model.addAttribute(new IntroduceForm());
//        model.addAttribute(account);
//        model.addAttribute(lecture);
//        return "lecture/introduceForm";
//    }
//
//    @PostMapping("/lecture/{path}/introduceForm")
//    public String newIntroduceSubmit(@CurrentUser Account account,@PathVariable String path,IntroduceForm introduceForm, Errors errors, Model model) {
//        Lecture lecture = lectureService.getLectureToRegister(path);
//        if (errors.hasErrors()){
//            return "lecture/view";
//        }
//        introduceService.createIntroduce(introduceForm,account);
//        return "redirect:/lecture/" + lecture.getEncodedPath();
//    }
//}
