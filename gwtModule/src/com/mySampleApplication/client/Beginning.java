package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.mySampleApplication.client.data.Data;
import com.mySampleApplication.client.data.Status;
import com.mySampleApplication.client.data.User;
import com.mySampleApplication.client.services.UserClient;
import com.mySampleApplication.client.util.UserParser;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

public class Beginning implements EntryPoint {
    private Storage storage = Storage.getSessionStorageIfSupported();
    private UserClient userClient = GWT.create(UserClient.class);
    private Label usernameErrorLabel = Label.wrap(DOM.getElementById("erUsername"));
    private Label passwordErrorLabel = Label.wrap(DOM.getElementById("erPassword"));
    private Label regStatus = Label.wrap(DOM.getElementById("regStatus"));
    private TextBox usernameTextBox = TextBox.wrap(DOM.getElementById("username"));
    private PasswordTextBox passwordTextBox = PasswordTextBox.wrap(DOM.getElementById("password"));

    @Override
    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());
        if (storage == null) {
            Window.alert("Sorry but storage is not supported!");
        } else {
            tryToLogIn();

            Button registrationButton = Button.wrap(DOM.getElementById("registration"));
            Button loginButton = Button.wrap(DOM.getElementById("entry"));

            registrationButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    registrationButton.setEnabled(false);
                    loginButton.setEnabled(false);
                    if (checkUsernameAndPassword()) {
                        User newUser = new User(usernameTextBox.getText(), passwordTextBox.getText().hashCode());
                        userClient.register(newUser, new MethodCallback<Status>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                Window.alert("Can't register this user\nCause: " + exception.getMessage());
                                registrationButton.setEnabled(true);
                                loginButton.setEnabled(true);
                            }

                            @Override
                            public void onSuccess(Method method, Status response) {
                                if (response.getStatus().equals("success")) {
                                    regStatus.removeStyleName("regStatusBad");
                                    regStatus.addStyleName("regStatusGood");
                                    regStatus.setText("Registration successful!");
                                } else {
                                    regStatus.removeStyleName("regStatusGood");
                                    regStatus.addStyleName("regStatusBad");
                                    regStatus.setText("Registration failed!");
                                }
                                registrationButton.setEnabled(true);
                                loginButton.setEnabled(true);
                            }
                        });
                    } else {
                        registrationButton.setEnabled(true);
                        loginButton.setEnabled(true);
                    }
                }
            });

            loginButton.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    registrationButton.setEnabled(false);
                    loginButton.setEnabled(false);
                    if(checkUsernameAndPassword()) {
                        User user = new User(usernameTextBox.getText(), passwordTextBox.getText().hashCode());
                        userClient.loginIn(user, new MethodCallback<Data>() {
                            @Override
                            public void onFailure(Method method, Throwable exception) {
                                Window.alert("Can't log in this user\nCause: " + exception.getMessage());
                                registrationButton.setEnabled(true);
                                loginButton.setEnabled(true);
                            }

                            @Override
                            public void onSuccess(Method method, Data response) {
                                if (response.getStatus().equals("success")) {
                                    user.setPoints(response.getPoints());
                                    logIn(user);
                                } else {
                                    regStatus.removeStyleName("regStatusGood");
                                    regStatus.addStyleName("regStatusBad");
                                    regStatus.setText("Log in failed!");
                                }
                                registrationButton.setEnabled(true);
                                loginButton.setEnabled(true);
                            }
                        });
                    } else {
                        registrationButton.setEnabled(true);
                        loginButton.setEnabled(true);
                    }
                }
            });
        }
    }

    private void logIn(User user){
        if (storage.getItem("user") != null) {
            storage.removeItem("user");
        }
        storage.setItem("user", UserParser.encode(user));
        Window.Location.assign(GWT.getHostPageBaseURL() + "MySampleApplication.html");
    }

    private void tryToLogIn(){
        String userJson = storage.getItem("user");
        if(userJson!=null){
            User user = UserParser.decode(userJson);
            userClient.loginIn(user, new MethodCallback<Data>() {
                @Override
                public void onFailure(Method method, Throwable exception) {
                    storage.removeItem("user");
                }

                @Override
                public void onSuccess(Method method, Data response) {
                    if(response.getStatus().equals("success")){
                        user.setPoints(response.getPoints());
                        logIn(user);
                    } else {
                        storage.removeItem("user");
                    }
                }
            });
        }
    }
    private boolean checkUsernameAndPassword(){
        boolean isCorrect = true;
        if(usernameTextBox.getText().length()<4 || usernameTextBox.getText().length()>20){
            isCorrect=false;
            usernameErrorLabel.setText("Username must have from 4 to 20 characters!");
        } else {
            usernameErrorLabel.setText("  ");
        }
        if(passwordTextBox.getText().length()<4){
            isCorrect=false;
            passwordErrorLabel.setText("Password must have more than 4 characters!");
        } else {
            passwordErrorLabel.setText("  ");
        }
        return isCorrect;
    }
}
