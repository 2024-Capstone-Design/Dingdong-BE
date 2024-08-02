package com.seoultech.capstone.domain.studentTaskProgress;

import com.seoultech.capstone.domain.studentTaskProgress.Enum.Progress;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressRequest;
import com.seoultech.capstone.domain.studentTaskProgress.dto.StudentTaskProgressResponse;
import com.seoultech.capstone.domain.studentTaskProgress.entity.StudentTask;
import com.seoultech.capstone.domain.task.Task;
import com.seoultech.capstone.domain.task.TaskRepository;
import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentTaskService {

    private final StudentTaskRepository studentTaskRepository;
    private final StudentRepository studentRepository;
    private final TaskRepository taskRepository;

    public StudentTaskProgressResponse createStudentTaskProgress(StudentTaskProgressRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such student with id " + request.getStudentId()));
        Task task = taskRepository.findById(request.getTaskId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such task with id " + request.getTaskId()));

        studentTaskRepository.findByStudentIdAndTaskId(request.getStudentId(), request.getTaskId())
                .ifPresent(existingProgress -> studentTaskRepository.delete(existingProgress));

        StudentTask studentTask = StudentTask.builder()
                .student(student)
                .task(task)
                .createdAt(LocalDateTime.now())
                .completed(false)
                .progress(Progress.NOT_STARTED)
                .build();

        StudentTask savedProgress = studentTaskRepository.save(studentTask);

        return createStudentTaskResponse(savedProgress, task);
    }

    public List<StudentTaskProgressResponse> getProgressedTasksByStudentId(Integer studentId, String sortBy, List<Progress> includeProgress) {
        List<StudentTask> studentTasks = studentTaskRepository.findByStudentId(studentId);

        if (includeProgress != null && !includeProgress.isEmpty()) {
            studentTasks = studentTasks.stream()
                    .filter(task -> includeProgress.contains(task.getProgress()))
                    .collect(Collectors.toList());
        }

        Comparator<StudentTask> comparator = Comparator.comparing(StudentTask::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo));
        if ("progress".equalsIgnoreCase(sortBy)) {
            comparator = Comparator.comparing(StudentTask::getProgress, Comparator.comparingInt(this::getProgressOrder));
        }

        return studentTasks.stream()
                .sorted(comparator)
                .map(this::convertToStudentTaskResponse)
                .collect(Collectors.toList());
    }

    private int getProgressOrder(Progress progress) {
        switch (progress) {
            case NOT_STARTED: return 1;
            case CHAT: return 2;
            case SKETCH: return 3;
            case CODING: return 4;
            case COMPLETED: return 5;
            default: throw new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "Unknown progress: " + progress);
        }
    }

    private StudentTaskProgressResponse convertToStudentTaskResponse(StudentTask studentTask) {
        Task task = studentTask.getTask();
        return createStudentTaskResponse(studentTask, task);
    }

    private StudentTaskProgressResponse createStudentTaskResponse(StudentTask studentTask, Task task) {
        return StudentTaskProgressResponse.studentTaskProgressResponseBuilder()
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
                .studentId(studentTask.getStudent().getId())
                .completed(studentTask.getCompleted())
                .completionDate(studentTask.getCompletionDate())
                .progress(studentTask.getProgress())
                .build();
    }

    public StudentTaskProgressResponse updateProgress(Integer id, Progress progress) {
        StudentTask studentTask = studentTaskRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such progress with id " + id));

        studentTask.updateProgress(progress);

        if (progress.equals(Progress.COMPLETED)){
            studentTask.updateCompleted(true, LocalDateTime.now());
        }

        StudentTask updatedProgress = studentTaskRepository.save(studentTask);

        return convertToStudentTaskResponse(updatedProgress);
    }
}
