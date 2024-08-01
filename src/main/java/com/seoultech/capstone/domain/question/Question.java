package com.seoultech.capstone.domain.question;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "question_type", nullable = false)
    private String type;

    @Column(nullable = false)
    private String prompt;

    @Column(name = "output", nullable = false)
    private String output;
}

