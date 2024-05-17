package com.seoultech.capstone.domain.task;

import com.seoultech.capstone.domain.fairytale.Fairytale;
import com.seoultech.capstone.domain.fairytale.FairytaleRepository;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupRepository;
import com.seoultech.capstone.domain.question.Question;
import com.seoultech.capstone.domain.question.QuestionRepository;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FairytaleRepository fairytaleRepository;
    private final GroupRepository classRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;

    public TaskResponse addTask(TaskRequest taskRequest) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Teacher teacher = teacherRepository.findByEmailAndActiveTrue(username)
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND, "No such teacher found with email " + username));

        Fairytale fairytale = fairytaleRepository.findById(taskRequest.getFairytaleId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND, "No such fairytale with id " + taskRequest.getFairytaleId()));
        Group targetClass = classRepository.findById(taskRequest.getTargetClassId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND, "No such class with id " + taskRequest.getTargetClassId()));
        Question question = questionRepository.findById(taskRequest.getQuestionId())
                .orElseThrow(() -> new CustomException(ErrorCode.ENTITY_NOT_FOUND, "No such question with id " + taskRequest.getQuestionId()));

        Task task = Task.builder()
                .fairytale(fairytale)
                .targetClass(targetClass)
                .teacher(teacher)
                .title(taskRequest.getTitle())
                .summary(taskRequest.getSummary())
                .startDate(taskRequest.getStartDate())
                .finishDate(taskRequest.getFinishDate())
                .question(question)
                .build();

        Task savedTask = taskRepository.save(task);

        return savedTask.toResponse();
    }
}
