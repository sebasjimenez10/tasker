/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Sebastian
 */
@Entity
@Table(name = "user_task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTask.findAll", query = "SELECT u FROM UserTask u"),
    @NamedQuery(name = "UserTask.findById", query = "SELECT u FROM UserTask u WHERE u.id = :id"),
    @NamedQuery(name = "UserTask.findByDescription", query = "SELECT u FROM UserTask u WHERE u.description = :description"),
    @NamedQuery(name = "UserTask.findByCreationDate", query = "SELECT u FROM UserTask u WHERE u.creationDate = :creationDate"),
    @NamedQuery(name = "UserTask.findByPriority", query = "SELECT u FROM UserTask u WHERE u.priority = :priority")})
public class UserTask implements Serializable {
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
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "priority")
    private int priority;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "taskId", fetch = FetchType.LAZY)
    private List<UserTaskActivity> userTaskActivityList;
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User idUser;

    public UserTask() {
    }

    public UserTask(Integer id) {
        this.id = id;
    }

    public UserTask(Integer id, String description, Date creationDate, int priority) {
        this.id = id;
        this.description = description;
        this.creationDate = creationDate;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @XmlTransient
    @JsonIgnore
    public List<UserTaskActivity> getUserTaskActivityList() {
        return userTaskActivityList;
    }

    public void setUserTaskActivityList(List<UserTaskActivity> userTaskActivityList) {
        this.userTaskActivityList = userTaskActivityList;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
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
        if (!(object instanceof UserTask)) {
            return false;
        }
        UserTask other = (UserTask) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tasker.entity.UserTask[ id=" + id + " ]";
    }
    
}
