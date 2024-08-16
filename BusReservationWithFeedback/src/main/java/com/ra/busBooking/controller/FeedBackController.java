package com.ra.busBooking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ra.busBooking.DTO.FeedBackDTO;
import com.ra.busBooking.model.FeedBack;
import com.ra.busBooking.model.User;
import com.ra.busBooking.repository.FeedbackRepository;
import com.ra.busBooking.repository.UserRepository;

@Controller
@RequestMapping("/feedback")
public class FeedBackController {

    @Autowired
    FeedbackRepository feedbackRepo;

    @Autowired
    UserRepository userRepository;


    @GetMapping
    public String showRegistrationForm(Model model) {
        FeedBackDTO dto = new FeedBackDTO();
        dto.setName(returnUsername().get("name"));
        dto.setEmail(returnUsername().get("email"));
        model.addAttribute("userDetails", returnUsername().get("name"));
        model.addAttribute("feedback", dto);
        return "feedback";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("feedback") FeedBackDTO feedbackDTO) {
        Map<String, String> map = returnUsername();
        FeedBack feedback = new FeedBack();
        feedback.setComment(feedbackDTO.getComment());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setName(map.get("name"));
        feedback.setEmail(map.get("email"));
        feedbackRepo.save(feedback);
        return "redirect:/feedback?success";
    }

    private Map<String, String> returnUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        UserDetails user = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User users = userRepository.findByEmail(user.getUsername());
        Map<String, String> map = new HashMap<String, String>();
        map.put("email", users.getEmail());
        map.put("name", users.getName());
        return map;
    }
}
