/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tasker.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Table(name = "user_task_activity_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserTaskActivityStatus.findAll", query = "SELECT u FROM UserTaskActivityStatus u"),
    @NamedQuery(name = "UserTaskActivityStatus.findById", query = "SELECT u FROM UserTaskActivityStatus u WHERE u.id = :id"),
    @NamedQuery(name = "UserTaskActivityStatus.findByStatusName", query = "SELECT u FROM UserTaskActivityStatus u WHERE u.statusName = :statusName")})
public class UserTaskActivityStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "status_name")
    private String statusName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "statusId")
    private List<UserTaskActivity> userTaskActivityList;

    public UserTaskActivityStatus() {
    }

    public UserTaskActivityStatus(Integer id) {
        this.id = id;
    }

    public UserTaskActivityStatus(Integer id, String statusName) {
        this.id = id;
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @XmlTransient
    @JsonIgnore
    public List<UserTaskActivity> getUserTaskActivityList() {
        return userTaskActivityList;
    }

    public void setUserTaskActivityList(List<UserTaskActivity> userTaskActivityList) {
        this.userTaskActivityList = userTaskActivityList;
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
        if (!(object instanceof UserTaskActivityStatus)) {
            return false;
        }
        UserTaskActivityStatus other = (UserTaskActivityStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tasker.entity.UserTaskActivityStatus[ id=" + id + " ]";
    }
    
}
