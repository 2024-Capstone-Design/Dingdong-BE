package com.seoultech.capstone.domain.fairytale;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fairytales")
public class FairytaleController {

    private final FairytaleService fairytaleService;

    @Secured("ROLE_TEACHER")
    @PostMapping
    public ResponseEntity<FairytaleResponse> addFairytale(@RequestBody FairytaleRequest fairytaleRequest) {
        FairytaleResponse createdFairytale = fairytaleService.addFairytale(fairytaleRequest);
        return new ResponseEntity<>(createdFairytale, HttpStatus.CREATED);
    }
}
