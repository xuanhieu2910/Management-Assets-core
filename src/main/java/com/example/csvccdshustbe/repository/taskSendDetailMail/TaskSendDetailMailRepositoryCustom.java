package com.example.csvccdshustbe.repository.taskSendDetailMail;

import com.example.csvccdshustbe.entity.TaskSendDetailMail;

import java.util.Optional;

public interface TaskSendDetailMailRepositoryCustom {
    Optional<TaskSendDetailMail> findByCodeTaskSendMail(String codeTaskSendMail);
}
