package com.md.doctor.dto;

import com.md.doctor.dto.office.OfficeDto;
import com.md.doctor.dto.security.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfficeContext {
    OfficeDto office;
    private UserDto owner;
}
