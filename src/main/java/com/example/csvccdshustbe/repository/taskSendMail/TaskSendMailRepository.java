package com.example.csvccdshustbe.repository.taskSendMail;

import com.example.csvccdshustbe.entity.TaskSendMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskSendMailRepository extends JpaRepository<TaskSendMail, Integer>, TaskSendMailRepositoryCustom {
}
