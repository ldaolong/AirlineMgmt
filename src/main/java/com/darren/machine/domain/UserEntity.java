package com.darren.machine.domain;


//@Entity
//@Table(name = "t_user")
public class UserEntity extends BaseEntity
{
    private String userId;
    
    private String password;
    private String confirmPassword;
    
    private String userName;
    
    private String superviseBy;
    
    private String description;
    
    private String department;
    
    private boolean isLocked;
    
    private boolean isDisabled;
    
    private String address;
    
    private String email;
    private String phoneNumber;
    private String status;
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }


    public String getStatus()
    {
        return status;
    }


    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getUserId()
    {
        return userId;
    }


    public void setUserId(String userId)
    {
        this.userId = userId;
    }


    public String getPassword()
    {
        return password;
    }


    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public String getUserName()
    {
        return userName;
    }


    public void setUserName(String userName)
    {
        this.userName = userName;
    }


    public String getSuperviseBy()
    {
        return superviseBy;
    }


    public void setSuperviseBy(String superviseBy)
    {
        this.superviseBy = superviseBy;
    }


    public String getDescription()
    {
        return description;
    }


    public void setDescription(String description)
    {
        this.description = description;
    }


    public String getDepartment()
    {
        return department;
    }


    public void setDepartment(String department)
    {
        this.department = department;
    }


    public boolean isLocked()
    {
        return isLocked;
    }


    public void setLocked(boolean isLocked)
    {
        this.isLocked = isLocked;
    }


    public boolean isDisabled()
    {
        return isDisabled;
    }


    public void setDisabled(boolean isDisabled)
    {
        this.isDisabled = isDisabled;
    }


    public String getAddress()
    {
        return address;
    }


    public void setAddress(String address)
    {
        this.address = address;
    }


    public String getEmail()
    {
        return email;
    }


    public void setEmail(String email)
    {
        this.email = email;
    }
}
