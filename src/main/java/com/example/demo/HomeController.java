package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * You are to write an application that is kind of like Twitter. It will allow a user to
 * post a message, and everyone else to see all the messages that have been posted.
 *
 * Users can edit and delete their own messages.
 *
 * Build an application that allows you to add MESSAGEs, list them and view them.
 *
 * Message object should have at least:
 *-------------------------------------
 * id
 * title
 * content
 * postedDate
 * postedBy
 * Features/Functionalities:
 *
 * The home page ("/") path should point to a list of all MESSAGE's
 * Every page (or template) should have link (or button) to the add path
 * ("/add") which is the new MESSAGE form
 * Use a repository to store your messages
 * The user should be able to update, view single messages on their own page, and delete messages.
 * Add Bootstrap and styling to make your pages look professional.
 * Once your application is working, put it on GitHub and deploy to Heroku.
 * Submit your Heroku link (you can add the GitHub link as a comment).
 * Done Already?
 *
 * Try adding an option for users to upload images as part of a post. Allow the updating of images as well.
 */
@Controller
public class HomeController {
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listMessages(Model model){
        model.addAttribute("messages", messageRepository.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("message", new Message());
        return "messageform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Message message,
                              BindingResult result,
                              @RequestParam("file") MultipartFile file) {
        System.out.println("object = " + message);
        if (result.hasErrors() || file.isEmpty()){
            return "messageform";
        }

        try {
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcetype", "auto"));
            String url = uploadResult.get("url").toString();
            message.setPicturePath(url);
            messageRepository.save(message);
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/messageform";
        }
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("message", messageRepository.findById(id).get());
        return "messageform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id){
        messageRepository.deleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/about")
    public String getAbout() {
        return "about";
    }
}
