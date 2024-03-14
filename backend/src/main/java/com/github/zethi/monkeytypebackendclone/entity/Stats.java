package com.github.zethi.monkeytypebackendclone.entity;


import jakarta.persistence.*;

import java.math.BigInteger;


@Entity
@Table(name = "stats")
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "time_typing", nullable = false, columnDefinition = "BIGINT")
    private BigInteger timeTyping = BigInteger.valueOf(0);

    @Column(name = "highest_wpm", nullable = false)
    private Long maxWPM = 0L;

    @Column(name = "highest_raw_wpm", nullable = false)
    private Long maxRawWPM = 0L;

    @Column(name = "test_completed", nullable = false)
    private Long testCompleted = 0L;

    @Column(name = "test_started", nullable = false)
    private Long testStarted = 0L;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigInteger getTimeTyping() {
        return timeTyping;
    }

    public void setTimeTyping(BigInteger timeTyping) {
        this.timeTyping = timeTyping;
    }

    public Long getMaxWPM() {
        return maxWPM;
    }

    public void setMaxWPM(Long maxWPM) {
        this.maxWPM = maxWPM;
    }

    public Long getMaxRawWPM() {
        return maxRawWPM;
    }

    public void setMaxRawWPM(Long maxRawWPM) {
        this.maxRawWPM = maxRawWPM;
    }

    public Long getTestCompleted() {
        return testCompleted;
    }

    public void setTestCompleted(Long testCompleted) {
        this.testCompleted = testCompleted;
    }

    public Long getTestStarted() {
        return testStarted;
    }

    public void setTestStarted(Long testStarted) {
        this.testStarted = testStarted;
    }
}
