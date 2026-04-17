package com.fourimpact.TaskManagementWithDbPersistence.Repository;

import com.fourimpact.TaskManagementWithDbPersistence.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
