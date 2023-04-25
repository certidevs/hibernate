package com.example.model.manytomany;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity(name="users_projects")
public class UserProject {

    @Id
    @Column(name = "project_id", insertable = false, updatable = false)
    private Long projectId;

    @Id
    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne
    private Role role;

    private LocalDate initDate;
    private LocalDate endDate;

    public UserProject() {
    }

    public UserProject(Project project, User user, Role role, LocalDate initDate, LocalDate endDate) {
        this.projectId = project.getId();
        this.userId = user.getId();
        this.role = role;
        this.initDate = initDate;
        this.endDate = endDate;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "UserProject{" +
                "projectId=" + projectId +
                ", userId=" + userId +
                ", initDate=" + initDate +
                ", endDate=" + endDate +
                '}';
    }
}
