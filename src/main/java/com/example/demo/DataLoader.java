package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    MessageRepository repository;

    @Override
    public void run(String... args) throws Exception {
        Message message = new Message("Today is holiday",
                "Dave wants to give holiday because we did good in class",
                LocalDate.now(),
                "Muhammad",
                "https://res.cloudinary.com/mhussainshah1/image/upload/v1550870732/blog/shah.jpg");
        repository.save(message);

        message = new Message("Valentines Day",
                "I am still looking for someone to come in my life",
                LocalDate.of(2019, 02, 14),
                "Victor",
                "https://res.cloudinary.com/mhussainshah1/image/upload/v1553267514/victor.png");
        repository.save(message);

        message = new Message("Independence Day",
                "I am proud to be an American where at least i am free. " +
                        "I wont forget men who die gave that right to me",
                LocalDate.of(2019, 07, 04),
                "Toyelani",
                "https://res.cloudinary.com/mhussainshah1/image/upload/v1551643804/Tolani%20Oyefule.jpg");
        repository.save(message);

        message = new Message("Mother's Day",
                "Happy mother day to the most loving mom in the world",
                LocalDate.of(2019, 05, 15),
                "Melisa",
                "https://res.cloudinary.com/mhussainshah1/image/upload/v1551715335/ktlpiusvm2hecfopse7y.png");
        repository.save(message);
    }
}
