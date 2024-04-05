package com.unleash.userservice.DTO;

import com.unleash.userservice.Model.Language;
import com.unleash.userservice.Model.Qualification;
import com.unleash.userservice.Model.Specialization;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class SelectionResponse {

    List<Qualification> qualifications;

    List<Language> languages;

    List<Specialization> specializations;
}
