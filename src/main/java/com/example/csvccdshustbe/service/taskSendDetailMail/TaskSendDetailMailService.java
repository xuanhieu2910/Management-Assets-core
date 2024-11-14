package com.example.csvccdshustbe.service.taskSendDetailMail;

import com.example.csvccdshustbe.dto.taskSendDetailMail.TaskSendDetailMailDto;
import com.example.csvccdshustbe.entity.TaskSendDetailMail;
import com.example.csvccdshustbe.request.taskSendDetailMail.FindAllTaskSendDetailMailRequest;
import com.example.csvccdshustbe.request.taskSendDetailMail.UpdateTaskSendDetailMailRequest;
import com.example.csvccdshustbe.response.taskSendDetailMail.FindAllTaskSendDetailMailResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskSendDetailMailService {

    TaskSendDetailMail saveTaskSendDetailMail(TaskSendDetailMail sendDetailMail);
    void updateTrackingTaskSendDetailMail(UpdateTaskSendDetailMailRequest updateTaskSendDetailMailRequest);
    Page<FindAllTaskSendDetailMailResponse> findAllTaskSendDetail(FindAllTaskSendDetailMailRequest request);
    void deleteTaskSendDetailMailByIds(List<Integer> idsTaskSendMail);
    void reSendTaskSendDetailMail(String codeTaskSendMail);
}
