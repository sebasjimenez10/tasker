/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sebastian
 */
@Entity
@Table(name = "user_task_activity")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTaskActivity.findAll", query = "SELECT u FROM UserTaskActivity u"),
    @NamedQuery(name = "UserTaskActivity.findById", query = "SELECT u FROM UserTaskActivity u WHERE u.id = :id"),
    @NamedQuery(name = "UserTaskActivity.findByDescription", query = "SELECT u FROM UserTaskActivity u WHERE u.description = :description"),
    @NamedQuery(name = "UserTaskActivity.findByPriority", query = "SELECT u FROM UserTaskActivity u WHERE u.priority = :priority")})
public class UserTaskActivity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priority")
    private int priority;
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserTask taskId;
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private UserTaskActivityStatus statusId;

    public UserTaskActivity() {
    }

    public UserTaskActivity(Integer id) {
        this.id = id;
    }

    public UserTaskActivity(Integer id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public UserTask getTaskId() {
        return taskId;
    }

    public void setTaskId(UserTask taskId) {
        this.taskId = taskId;
    }

    public UserTaskActivityStatus getStatusId() {
        return statusId;
    }

    public void setStatusId(UserTaskActivityStatus statusId) {
        this.statusId = statusId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserTaskActivity)) {
            return false;
        }
        UserTaskActivity other = (UserTaskActivity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tasker.entity.UserTaskActivity[ id=" + id + " ]";
    }
    
}
