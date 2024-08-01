package com.seoultech.capstone.domain.task;

import com.seoultech.capstone.domain.fairytale.domain.Fairytale;
import com.seoultech.capstone.domain.fairytale.domain.FairytaleRepository;
import com.seoultech.capstone.domain.group.Group;
import com.seoultech.capstone.domain.group.GroupRepository;
import com.seoultech.capstone.domain.question.Question;
import com.seoultech.capstone.domain.question.QuestionRepository;
import com.seoultech.capstone.domain.studentTaskProgress.StudentTaskProgressRepository;
import com.seoultech.capstone.domain.user.student.Student;
import com.seoultech.capstone.domain.user.student.StudentRepository;
import com.seoultech.capstone.domain.user.teacher.Teacher;
import com.seoultech.capstone.domain.user.teacher.TeacherRepository;
import com.seoultech.capstone.exception.CustomException;
import com.seoultech.capstone.response.ErrorStatus;
import com.seoultech.capstone.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.seoultech.capstone.response.ErrorStatus.ENTITY_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final FairytaleRepository fairytaleRepository;
    private final GroupRepository classRepository;
    private final QuestionRepository questionRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final StudentTaskProgressRepository studentTaskProgressRepository;

    public TaskResponse addTask(TaskRequest taskRequest) {

        final int id = SecurityUtil.getCurrentUserId();

        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such teacher found with id " + id));

        Fairytale fairytale = fairytaleRepository.findById(taskRequest.getFairytaleId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such fairytale with id " + taskRequest.getFairytaleId()));
        Group targetClass = classRepository.findById(taskRequest.getTargetClassId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such class with id " + taskRequest.getTargetClassId()));
        Question question = questionRepository.findById(taskRequest.getQuestionId())
                .orElseThrow(() -> new CustomException(ErrorStatus.ENTITY_NOT_FOUND, "No such question with id " + taskRequest.getQuestionId()));

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

    public List<TaskResponse> getTasksByTeacherId(Integer teacherId) {
        List<Task> tasks = taskRepository.findByTeacherId(teacherId);
        return tasks.stream()
                .map(Task::toResponse)
                .collect(Collectors.toList());
    }

    public List<TaskResponse> getTasksByStudentId(Integer studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(ENTITY_NOT_FOUND, "Invalid student ID: " + studentId));

        List<Task> tasks = taskRepository.findByTargetClassId(student.getGroup().getId());
        return tasks.stream()
                .map(Task::toResponse)
                .collect(Collectors.toList());
    }
}
