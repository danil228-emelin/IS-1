package itmo.is.model.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ImportHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;
    private String userName;

    @Column(nullable = false)
    private String status;  // "SUCCESS", "FAILED"

    private int addedCount;  // Количество добавленных объектов (только для успешных операций)

    @Column(nullable = false)
    private LocalDateTime timestamp;  // Время выполнения операции импорта

    public ImportHistory() {
        this.timestamp = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getAddedCount() {
        return addedCount;
    }

    public void setAddedCount(int addedCount) {
        this.addedCount = addedCount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}