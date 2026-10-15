package com.jane.janestore.service;

import com.jane.janestore.dto.ProfileRequestDto;
import com.jane.janestore.dto.ProfileResponseDto;

public interface IProfileService {

    ProfileResponseDto getProfile();

    ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto);
}
