package com.example.myapplication;

// Product
public class RegistrationFormBuilder extends BuilderInterface {
    private String username;
    private String password;
    private String confirmPassword;

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public RegistrationForm getResult() {
        return new RegistrationForm(this);
    }


    public RegistrationForm build() {
        return new RegistrationForm(this);
    }
}

// Builder Interface that RegistrationFormBuilder extends
abstract class BuilderInterface {
    public abstract void setUsername(String username);
    public abstract void setPassword(String password);
    public abstract void setConfirmPassword(String confirmPassword);
    public abstract RegistrationForm getResult();
}

// Builder
class Builder {
    public String username;
    public String password;
    public String confirmPassword;

    public Builder setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }

    public Builder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Builder setUsername(String username) {
        this.username = username;
        return this;
    }

    public RegistrationForm build() {
        return new RegistrationForm(this);
    }
}

// Director
class RegistrationForm {
    private String username;
    private String password;
    private String confirmPassword;

    public RegistrationForm(Builder build) {
        this.username = build.username;
        this.password = build.password;
        this.confirmPassword = build.confirmPassword;
    }

    public RegistrationForm(RegistrationFormBuilder registrationFormBuilder) {
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }
}