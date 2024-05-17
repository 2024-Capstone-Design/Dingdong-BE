package com.seoultech.capstone.domain.fairytale;

import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FairytaleService {

    private final FairytaleRepository fairytaleRepository;
    private final TeacherRepository teacherRepository;

    public FairytaleResponse addFairytale(FairytaleRequest fairytaleRequestDTO) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Teacher teacher = teacherRepository.findByEmailAndActiveTrue(username)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND, "No teacher found with the provided email " + username));

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
