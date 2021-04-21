package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date operation_date;

    @Column(nullable = false)
    private Boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable (name="tasks_categories",
            joinColumns=@JoinColumn (name="task_id"),
            inverseJoinColumns=@JoinColumn(name="category_id"))
    @JsonIgnore
    private List<Category> categories;

    public Task() {}

    public void addCategory(Category category) {
        this.categories.add(category);
        category.addTask(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.removeTask(this);
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Date getOperation_date() {
        return operation_date;
    }

    public Boolean getComplete() {
        return complete;
    }

    public User getUser() {
        return user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public void setOperation_date(Date operation_date) {
        this.operation_date = operation_date;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", operation_date=" + operation_date +
                ", complete=" + complete +
                ", user=" + user +
                ", categories=" + categories +
                '}';
    }
}
