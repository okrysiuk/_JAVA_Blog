package com.example.Blog.controllers;


import com.example.Blog.domain.Note;
import com.example.Blog.repos.NoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private NoteRepo noteRepo;

    @GetMapping("/")
    public String greeting() {
        return "greeting";
    }
    @GetMapping("/home")
    public String home(Model model) {
        Iterable<Note> notes = noteRepo.findAll();
        model.addAttribute("notes", notes);
        return "home";
    }
    @GetMapping("/blog-add")
    public String blogAdd(Model model) {

        return "blog-add";
    }

    @PostMapping("/addition")
    public String addition(@RequestParam(required = false) String author,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String text,
                           @RequestParam(required = false) String tag, Model model){
        Note note = new Note(author, title, text, tag);
        noteRepo.save(note);
        return "redirect:/";
    }
    @GetMapping("/note/{id}")
    public String noteDetails(@PathVariable(value = "id")long noteId, Model model) {
        if(!noteRepo.existsById(noteId)){
            return "redirect:/";
        }

        Optional<Note> note = noteRepo.findById(noteId);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("detail",res);
        return "blog-details";
    }
    @GetMapping("/note/{id}/edit")
    public String noteEdit(@PathVariable(value = "id")long noteId, Model model) {
        if(!noteRepo.existsById(noteId)){
            return "redirect:/";
        }

        Optional<Note> note = noteRepo.findById(noteId);
        ArrayList<Note> res = new ArrayList<>();
        note.ifPresent(res::add);
        model.addAttribute("detail",res);
        return "blog-edit";
    }

    @PostMapping("/note/{id}/edit")
    public String updating(@PathVariable(value = "id")long noteId,
                           @RequestParam(required = false) String author,
                           @RequestParam(required = false) String title,
                           @RequestParam(required = false) String text,
                           @RequestParam(required = false) String tag, Model model) {
        Note note = noteRepo.findById(noteId).orElseThrow(() ->
                new IllegalArgumentException("Unsupported value: " + noteId) // just return it
        );
        note.setAuthor(author);
        note.setTitle(title);
        note.setText(text);
        note.setTag(tag);

        noteRepo.save(note);

        return "redirect:/";
    }
    @PostMapping("/note/{id}/remove")
    public String removing(@PathVariable(value = "id")long noteId, Model model) {
        Note note = noteRepo.findById(noteId).orElseThrow(() ->
                new IllegalArgumentException("Unsupported value: " + noteId) // just return it
        );
        noteRepo.delete(note);

        return "redirect:/";
    }
}