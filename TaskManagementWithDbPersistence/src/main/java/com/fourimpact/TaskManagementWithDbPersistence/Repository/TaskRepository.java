package com.fourimpact.TaskManagementWithDbPersistence.Repository;

import com.fourimpact.TaskManagementWithDbPersistence.Enums.TaskStatus;
import com.fourimpact.TaskManagementWithDbPersistence.Model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    @Query("SELECT t FROM Task t JOIN FETCH t.user JOIN FETCH t.category")
    List<Task> findAllWithUserAndCategory();

    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :userId",
            countQuery = "SELECT COUNT(t) FROM Task t WHERE t.user.id = :userId")
    Page<Task> findByUserPaginated(Long userId, Pageable pageable);

    Page<Task> findByUser_Id(Long userID, Pageable pageable);

    List<Task> getAllBy();
}
