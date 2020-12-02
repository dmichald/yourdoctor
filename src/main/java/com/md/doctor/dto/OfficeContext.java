package com.md.doctor.dto;

import com.md.doctor.dto.office.AddOfficeDto;
import com.md.doctor.dto.security.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OfficeContext {
    @NotNull
    private AddOfficeDto office;

    @NotNull
    private UserDto owner;
}
