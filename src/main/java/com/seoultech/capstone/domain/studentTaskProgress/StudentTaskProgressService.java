package com.seoultech.capstone.domain.studentTaskProgress;

import com.seoultech.capstone.domain.studentTaskProgress.Enum.Progress;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressRequest;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressResponse;
import com.seoultech.capstone.domain.task.Task;
import com.seoultech.capstone.domain.task.TaskRepository;
import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentTaskProgressService {

    private final StudentTaskProgressRepository studentTaskProgressRepository;
    private final StudentRepository studentRepository;
    private final TaskRepository taskRepository;

    public StudentTaskProgressResponse createStudentTaskProgress(StudentTaskProgressRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such student with id " + request.getStudentId()));
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such task with id " + request.getTaskId()));

        StudentTaskProgress studentTaskProgress = StudentTaskProgress.builder()
                .student(student)
                .task(task)
                .createdAt(LocalDateTime.now())
                .completed(false)
                .progress(Progress.NOT_STARTED)
                .build();

        StudentTaskProgress savedProgress = studentTaskProgressRepository.save(studentTaskProgress);

        return createStudentTaskResponse(savedProgress, task);
    }

    public List<StudentTaskProgressResponse> getProgressedTasksByStudentId(Integer studentId) {
        List<StudentTaskProgress> studentTaskProgresses = studentTaskProgressRepository.findByStudentId(studentId);
        return studentTaskProgresses.stream()
                .map(this::convertToStudentTaskResponse)
                .collect(Collectors.toList());
    }

    private StudentTaskProgressResponse convertToStudentTaskResponse(StudentTaskProgress studentTaskProgress) {
        Task task = studentTaskProgress.getTask();
        return createStudentTaskResponse(studentTaskProgress, task);
    }

    private StudentTaskProgressResponse createStudentTaskResponse(StudentTaskProgress studentTaskProgress, Task task) {
        return StudentTaskProgressResponse.studentTaskProgressResponseBuilder()
                .studentTaskProgressId(studentTaskProgress.getId())
                .taskId(task.getId())
                .taskTitle(task.getTitle())
                .taskSummary(task.getSummary())
                .fairytaleId(task.getFairytale().getId())
                .fairytaleTitle(task.getFairytale().getTitle())
                .fairytaleImageUrl(task.getFairytale().getImageUrl())
                .targetClassId(task.getTargetClass().getId())
                .targetClassName(task.getTargetClass().getName())
                .startDate(task.getStartDate())
                .finishDate(task.getFinishDate())
                .questionId(task.getQuestion().getId())
                .questionType(task.getQuestion().getType())
                .questionPrompt(task.getQuestion().getPrompt())
                .questionOutput(task.getQuestion().getOutput())
                .createdAt(task.getCreatedAt())
                .studentId(studentTaskProgress.getStudent().getId())
                .completed(studentTaskProgress.getCompleted())
                .completionDate(studentTaskProgress.getCompletionDate())
                .progress(studentTaskProgress.getProgress())
                .build();
    }

    public StudentTaskProgressResponse updateProgress(Integer id, Progress progress) {
        StudentTaskProgress studentTaskProgress = studentTaskProgressRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such progress with id " + id));

        studentTaskProgress.updateProgress(progress);

        if (progress.equals(Progress.COMPLETED)){
            studentTaskProgress.updateCompleted(true, LocalDateTime.now());
        }

        StudentTaskProgress updatedProgress = studentTaskProgressRepository.save(studentTaskProgress);

        return convertToStudentTaskResponse(updatedProgress);
    }
}
