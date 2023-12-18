package com.msgsystems.jbugger.echipa2.backend.repository;

import com.msgsystems.jbugger.echipa2.backend.domain.Notification;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "notifications", path = "notifications")
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT n FROM Notification AS n WHERE :user MEMBER OF n.users")
    Optional<List<Notification>> findByUser(User user);


    @Modifying
    @Transactional
    @Query("DELETE FROM Notification n WHERE n.date < :date")
    int removeOlderThan(java.sql.Date date);


}
