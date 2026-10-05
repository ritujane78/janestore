package com.jane.janestore.repository;

import com.jane.janestore.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
  }
