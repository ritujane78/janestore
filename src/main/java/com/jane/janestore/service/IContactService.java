package com.jane.janestore.service;

import com.jane.janestore.dto.ContactRequestDto;

import java.util.List;

public interface IContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);
}
