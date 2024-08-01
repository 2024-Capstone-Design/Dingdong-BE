package com.seoultech.capstone.domain.fairytale.service;

import com.seoultech.capstone.domain.fairytale.domain.Fairytale;
import com.seoultech.capstone.domain.fairytale.domain.FairytaleRepository;
import com.seoultech.capstone.domain.fairytale.dto.FairytaleRequest;
import com.seoultech.capstone.domain.fairytale.dto.FairytaleResponse;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import com.seoultech.capstone.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FairytaleService {

    private final FairytaleRepository fairytaleRepository;
    private final TeacherRepository teacherRepository;

    public FairytaleResponse addFairytale(FairytaleRequest fairytaleRequestDTO) {
        final int id = SecurityUtil.getCurrentUserId();

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such teacher with id " + id));

        Fairytale fairytale = Fairytale.builder()
                .title(fairytaleRequestDTO.getTitle())
                .background(fairytaleRequestDTO.getBackground())
                .characters(fairytaleRequestDTO.getCharacters())
                .content(fairytaleRequestDTO.getContent())
                .teacher(teacher)
                .build();

        Fairytale savedFairytale = fairytaleRepository.save(fairytale);

        return savedFairytale.toResponse();
    }

    public List<FairytaleResponse> getAllFairytales() {
        return fairytaleRepository.findAll().stream()
                .map(Fairytale::toResponse)
                .collect(Collectors.toList());
    }

    public FairytaleResponse getFairytaleById(Integer id) {
        Fairytale fairytale = fairytaleRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such fairytale with id " + id));
        return fairytale.toResponse();
    }
}
