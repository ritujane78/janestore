package com.jane.janestore.service;

import com.jane.janestore.dto.ContactRequestDto;
import com.jane.janestore.dto.ContactResponseDto;

import java.util.List;

public interface IContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);
    List<ContactResponseDto> getAllOpenMessages();

    void updateMessageStatus(Long contactId, String status);
}
