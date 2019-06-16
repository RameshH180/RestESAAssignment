package com.spring.assignment.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.List;

@Document(indexName = "users", type = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Long id;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String title;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String userName;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private Integer age;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String educationQualification;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<String> companiesWorkedIn;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String headline;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String summary;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private String errMsg;
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<User> usersDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getEducationQualification() {
        return educationQualification;
    }

    public void setEducationQualification(String educationQualification) {
        this.educationQualification = educationQualification;
    }

    public List<String> getCompaniesWorkedIn() {
        return companiesWorkedIn;
    }

    public void setCompaniesWorkedIn(List<String> companiesWorkedIn) {
        this.companiesWorkedIn = companiesWorkedIn;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<User> getUsersDetails() {
        return usersDetails;
    }

    public void setUsersDetails(List<User> usersDetails) {
        this.usersDetails = usersDetails;
    }
}
