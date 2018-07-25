package com.team4.app.controller;

import com.team4.app.model.User;
import com.team4.app.service.EmailService;
import com.team4.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class AccountController {

    private UserService userService;
    private EmailService emailService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountController(UserService userService,
                             EmailService emailService,
                             BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerUser(Model model, User user) {

        model.addAttribute("user",user);

        return "auth/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String postAddNewUser(Model model, @Valid @ModelAttribute("user") User user,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirect,
                                 HttpServletRequest request) {


        User userExists = userService.findByUsername(user.getUsername());
        if (userExists != null){
            redirect.addFlashAttribute("error","A user with that username already exists");
            bindingResult.reject("username");
            return "redirect:/register";
        }

        userExists = userService.findByEmail(user.getEmail());
        if (userExists != null){
            redirect.addFlashAttribute("error","A user with that email already exists");
            bindingResult.reject("email");
            return "redirect:/register";

        }

        if (bindingResult.hasErrors()){

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            String result = "";
            for (int i = 0; i <  fieldErrors.size() - 1; i++) {
                result += fieldErrors.get(i).getField();
                result += ", ";
            }
            result += fieldErrors.get(fieldErrors.size() - 1).getField();
            if(fieldErrors.size() > 1)
                redirect.addFlashAttribute("error", "Error registering a user. " + result + " are invalid.");
            else
                redirect.addFlashAttribute("error", "Error registering a user. " + result + " is invalid..");

            return "redirect:/register";

        } else{
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setEnabled(false);
            user.setPasswordResetToken(userService.getNewToken());
            user.setConfirmAccountToken(userService.getNewToken());
            user.setReactivateAccountToken(userService.getNewToken());
            userService.save(user);

            String appUrl = request.getScheme() + "://" + request.getServerName();
            SimpleMailMessage registrationEmail = new SimpleMailMessage();
            registrationEmail.setTo(user.getEmail());
            registrationEmail.setSubject("Confirm your email");
            registrationEmail.setText("To confirm your email address, please click the link below:\n"
                    + appUrl + ":8080/confirm-account?token=" + user.getConfirmAccountToken());

            emailService.sendEmail(registrationEmail);

            redirect.addFlashAttribute("success", "A confirmation e-mail has been sent to " + user.getEmail());

            return "redirect:/register";
        }
    }

    @RequestMapping(value = "/confirm-account", method = RequestMethod.GET)
    public String confirmCreatedAccount(@RequestParam("token") String token, RedirectAttributes redirect){

        User user = userService.findByConfirmAccountToken(token);

        if (user != null) {
            user.setEnabled(true);
            user.setRole("ROLE_USER");
            user.setConfirmAccountToken(userService.getNewToken());
            userService.save(user);
            redirect.addFlashAttribute("success", "Account confirmed.");
        }
        else {
            redirect.addFlashAttribute("error", "Something went wrong.");
        }
        return "redirect:/login";
    }

    //supervisor and admin only
    @RequestMapping(value = "/confirm-user-account", method = RequestMethod.GET)
    public String confirmCreateUserAccount(Model model, @RequestParam("token") String token, RedirectAttributes redirectAttributes){

        User user = userService.findByConfirmAccountToken(token);
        if (user == null){
            redirectAttributes.addFlashAttribute("error", "Something went wrong.");
            return "redirect:/login";
        }
        if (user.isEnabled()){
            redirectAttributes.addFlashAttribute("error", "Something went wrong.");
            return "redirect:/login";
        }
        model.addAttribute("token", token);

        return "auth/confirmAccount";
    }

    /*
     * This method perform the actual account confirmation but checking for the token and then setting the password.
     */
    @RequestMapping(value = "/confirm-user-account", method = RequestMethod.POST)
    public String postConfirmCreatedAccount(@RequestParam Map requestParams, Model model){

        String token = (String) requestParams.get("token");
        User user = userService.findByConfirmAccountToken(token);
        if (user == null){
            return "redirect:/";
        }

        if (user.isEnabled()){
            return "redirect:/";
        }

        if (userService.findByUsername(requestParams.get("username").toString()) != null){
            return "redirect:/";
        }

        user.setUsername(requestParams.get("username").toString());
        user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password")));
        user.setEnabled(true);
        user.setConfirmAccountToken(userService.getNewToken());
        userService.save(user);

        model.addAttribute("success", "Account activated.");

        return "redirect:/login";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
    public String showForgotPassword() {
        return "auth/resetPassword";
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public String postForgotPassword(Model model, @RequestParam Map requestParams, HttpServletRequest request, RedirectAttributes redirectAttributes){

        User user = userService.findByEmail(requestParams.get("email").toString());
        if (user == null){
            redirectAttributes.addFlashAttribute("error", "Email not found.");
            return "redirect:/forgot-password";
        }

        String appUrl = request.getScheme() + "://" + request.getServerName();
        SimpleMailMessage resetPasswordEmail = new SimpleMailMessage();
        resetPasswordEmail.setTo(user.getEmail());
        resetPasswordEmail.setSubject("Password reset");
        resetPasswordEmail.setText("To reset your password, please click on the link below.\n"
                + appUrl + ":8080/reset-password?token=" + user.getPasswordResetToken());

        emailService.sendEmail(resetPasswordEmail);
        redirectAttributes.addFlashAttribute("success", "Password reset link was sent to your email.");

        return "redirect:/forgot-password";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.GET)
    public String resetPassword(Model model, @RequestParam("token") String token, RedirectAttributes redirectAttributes){

        User user = userService.findByPasswordResetToken(token);
        if (user == null){
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/forgot-password";
        }

        model.addAttribute("token", token);

        return "auth/setPassword";
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public String postResetPassword(@RequestParam Map requestParams, RedirectAttributes redirectAttributes){

        User user = userService.findByPasswordResetToken(requestParams.get("token").toString());
        if (user == null){
            redirectAttributes.addFlashAttribute("error", "User not found.");
            return "redirect:/forgot-password";
        }

        user.setPassword(bCryptPasswordEncoder.encode((CharSequence) requestParams.get("password").toString()));
        user.setPasswordResetToken(userService.getNewToken());
        userService.save(user);

        redirectAttributes.addFlashAttribute("success", "Password has been successfully changed.");
        return "redirect:/login";
    }
}