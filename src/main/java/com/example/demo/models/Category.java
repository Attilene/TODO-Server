package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories")
public class Category extends AuditModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable (name="tasks_categories",
            joinColumns=@JoinColumn (name="task_id"),
            inverseJoinColumns=@JoinColumn(name="category_id"))
    @JsonIgnore
    private List<Task> tasks;

    public Category() {}

    public void addTask(Task task) {
        this.tasks.add(task);
        task.addCategory(this);
    }

    public void removeTask(Task task) {
        this.tasks.remove(task);
        task.removeCategory(null);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
