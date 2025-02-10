package com.example.csvccdshustbe.repository.toolInstance;

import com.example.csvccdshustbe.entity.ToolInstance;
import com.example.csvccdshustbe.request.toolInstance.FindAllToolInstanceRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ToolInstanceRepositoryCustom {
    Page<ToolInstance> findAllToolInstance(FindAllToolInstanceRequest request, Pageable pageable);
    long totalErrorToolInstance();
    List<ToolInstance> findAllToolInstanceByIds(List<Integer> idsToolInstance);
}
