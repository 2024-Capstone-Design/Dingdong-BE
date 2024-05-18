package com.seoultech.capstone.domain.fairytale;

import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.exception.ErrorCode;
import com.seoultech.capstone.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairytaleService {

    private final FairytaleRepository fairytaleRepository;
    private final TeacherRepository teacherRepository;

    public FairytaleResponse addFairytale(FairytaleRequest fairytaleRequestDTO) {
        final int id = SecurityUtil.getCurrentUserId();

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND, "No such teacher with id " + id));

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
}
