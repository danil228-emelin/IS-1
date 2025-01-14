package itmo.is.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Entity
@Setter
public class ImportHistory {
    // Геттеры и сеттеры
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;
    private String userName;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String status;  // "SUCCESS", "FAILED"

    private int addedCount;  // Количество добавленных объектов (только для успешных операций)

    @Column(nullable = false)
    private LocalDateTime timestamp;  // Время выполнения операции импорта

    public ImportHistory() {
        this.timestamp = LocalDateTime.now();
    }

}