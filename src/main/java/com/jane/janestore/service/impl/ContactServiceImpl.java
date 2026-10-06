package com.jane.janestore.service.impl;

import com.jane.janestore.dto.ContactRequestDto;
import com.jane.janestore.entity.Contact;
import com.jane.janestore.repository.ContactRepository;
import com.jane.janestore.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
          Contact contact = transformToEntity(contactRequestDto);
          contactRepository.save(contact);
          return true;
    }

    private Contact transformToEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto, contact);
        return contact;
    }
}
