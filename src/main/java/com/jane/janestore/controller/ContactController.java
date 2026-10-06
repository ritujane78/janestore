package com.jane.janestore.controller;

import com.jane.janestore.dto.ContactRequestDto;
import com.jane.janestore.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService iContactService;

    @PostMapping
    public ResponseEntity<String> saveContact(@RequestBody ContactRequestDto contactRequestDto) {
        boolean isSaved = iContactService.saveContact(contactRequestDto);
        if (isSaved) {
            return ResponseEntity.status(201).body("Request processed successfully");
        } else {
            return ResponseEntity.status(500).body("An error occurred. Please try again or contact Dev team");
        }
    }
}
