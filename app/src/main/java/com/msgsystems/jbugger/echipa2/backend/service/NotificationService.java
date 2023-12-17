package com.msgsystems.jbugger.echipa2.backend.service;

import com.msgsystems.jbugger.echipa2.backend.domain.Notification;
import com.msgsystems.jbugger.echipa2.backend.domain.User;
import com.msgsystems.jbugger.echipa2.backend.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@EnableScheduling
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;
    public Optional<List<Notification>> findByUser(User user){
        return notificationRepository.findByUser(user);
    }

    @Scheduled(fixedDelay = 10000) // delete once in 10 seconds 
    public void removeOlderThan30Days(){
        Logger.getLogger("NotificationService").info("Notifications cleanup");
        var timeBomb = java.sql.Date.valueOf(LocalDate.now().minusDays(30));
        int removedCount = notificationRepository.removeOlderThan(timeBomb);
        Logger.getLogger("NotificationService").info("Removed "+removedCount+" notifications.");
    }
}
