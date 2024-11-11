package com.example.csvccdshustbe.repository.taskSendDetailMail;

import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskSendDetailMailRepository extends JpaRepository<TaskSendDetailMail, Integer>, TaskSendDetailMailRepositoryCustom {
}
