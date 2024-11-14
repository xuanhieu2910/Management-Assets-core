package com.example.csvccdshustbe.repository.taskSendDetailMail;

import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.request.taskSendDetailMail.FindAllTaskSendDetailMailRequest;
import com.example.csvccdshustbe.response.taskSendDetailMail.FindAllTaskSendDetailMailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskSendDetailMailRepositoryCustom {
    Optional<TaskSendDetailMail> findByCodeTaskSendMail(String codeTaskSendMail);
    Page<FindAllTaskSendDetailMailResponse> findAllTaskSendDetailMail(Pageable pageable,
                                                                      FindAllTaskSendDetailMailRequest request);
    void deleteTaskSendDetailMailByIds(List<Integer> idsTaskSendMail);
}
