package com.mySampleApplication.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.mySampleApplication.client.data.Point;
import com.mySampleApplication.client.data.Status;
import com.mySampleApplication.client.data.User;
import com.mySampleApplication.client.data.UserPoint;
import com.mySampleApplication.client.services.UserClient;
import com.mySampleApplication.client.util.CheckBoxUnique;
import com.mySampleApplication.client.util.UserParser;
import com.mySampleApplication.client.util.YValidator;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.ArrayList;
import java.util.List;

public class MySampleApplication implements EntryPoint {
    private static final String beginPage = "Beginning.html";
    private final Storage storage = Storage.getSessionStorageIfSupported();
    private User user;
    private final UserClient userClient = GWT.create(UserClient.class);
    private Grid xGrid = new Grid(2, 10);
    private Grid rGrid = new Grid(2, 5);
    private Grid pointGrid = new Grid(1, 4);
    private List<CheckBox> XList = new ArrayList<>();
    private List<CheckBox> RList = new ArrayList<>();
    private TextBox yText;

    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());

        if(storage==null){
            Window.alert("Sorry, but storage is not supported!");
            Window.Location.assign(GWT.getHostPageBaseURL() + beginPage);
        }
        String userJson = storage.getItem("user");
        if(userJson==null){
            Window.Location.assign(GWT.getHostPageBaseURL() + beginPage);
        }
        user = UserParser.decode(userJson);
        buildXGrid();
        buildRGrid();
        buildPointGrid();
        HorizontalPanel verticalPanel = new HorizontalPanel();

        Label yLabel = new Label();
        yLabel.setText(" Y: ");
        yLabel.addStyleName("labelY");

        yText = new TextBox();
        yText.addStyleName("y");
        yText.setMaxLength(5);
        verticalPanel.add(yLabel);
        verticalPanel.add(yText);

        Button addButton = new Button("Add point");
        addButton.addStyleName("addButton");

        RootPanel.get("leftTop").add(xGrid);
        RootPanel.get("leftTop").add(verticalPanel);
        RootPanel.get("leftTop").add(rGrid);
        RootPanel.get("leftTop").add(addButton);
        RootPanel.get("left").add(pointGrid);

        Button exitButton = Button.wrap(DOM.getElementById("exit"));
        exitButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                storage.removeItem("user");
                Window.Location.assign(GWT.getHostPageBaseURL() + beginPage);
            }
        });


        addButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                Double x = getX();
                Double y = getY();
                Double r = getR();
                if(x==null || y==null || r==null){
                    Window.alert("Incorrect data!");
                    Window.alert("X: " + x + "\nY: " + y + "\nR: " + r);
                    return;
                }
                UserPoint userPoint = new UserPoint();
                userPoint.setUsername(user.getUsername());
                userPoint.setPassword(user.getPassword());
                userPoint.setX(x);
                userPoint.setY(y);
                userPoint.setR(r);
                userClient.addPoint(userPoint, new MethodCallback<Status>() {
                    @Override
                    public void onFailure(Method method, Throwable exception) {
                        Window.alert("Something went wrong!\n" + exception.getMessage());
                    }

                    @Override
                    public void onSuccess(Method method, Status response) {
                        pointGrid.resizeRows(pointGrid.getRowCount()+1);
                        pointGrid.setText(pointGrid.getRowCount()-1, 0, userPoint.getX().toString());
                        pointGrid.setText(pointGrid.getRowCount()-1, 1, userPoint.getY().toString());
                        pointGrid.setText(pointGrid.getRowCount()-1, 2, userPoint.getR().toString());
                        pointGrid.setText(pointGrid.getRowCount()-1, 3, Boolean.toString(userPoint.checkHitted()));
                        user.getPoints().add(new Point(userPoint.getX(), userPoint.getY(), userPoint.getR()));
                    }
                });
            }
        });
        if(user.getPoints().size()!=0) {
            pointGrid.resizeRows(user.getPoints().size()+1);
            for (int i=0;i<user.getPoints().size();i++) {
                pointGrid.setText(i+1, 0, user.getPoints().get(i).getX().toString());
                pointGrid.setText(i+1, 1, user.getPoints().get(i).getY().toString());
                pointGrid.setText(i+1, 2, user.getPoints().get(i).getR().toString());
                pointGrid.setText(i+1, 3, Boolean.toString(user.getPoints().get(i).checkHitted()));
            }
        }
    }


    private void buildXGrid(){
        xGrid.setHTML(0, 0, "<label>X: </label>");
        for(int i=-4;i<5;i++){
            xGrid.setText(0, i+5, Integer.toString(i));
        }
        CheckBox checkBox;
        for(int i=1;i<10;i++){
            checkBox = new CheckBox();
            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    CheckBoxUnique.doUnique(XList, event);
                }
            });
            checkBox.setStylePrimaryName("Xclass");
            checkBox.setFormValue(Double.toString(i-5));

            xGrid.setWidget(1, i, checkBox);
            XList.add(checkBox);
        }
        XList.get(0).setValue(true);
        xGrid.addStyleName("tableX");
    }
    private void buildRGrid(){
        rGrid.setHTML(0, 0, "<label>R: </label>");
        for(int i=1;i<5;i++){
            rGrid.setText(0, i, Integer.toString(i));
        }
        CheckBox checkBox;
        for(int i=1;i<5;i++){
            checkBox = new CheckBox();
            checkBox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
                @Override
                public void onValueChange(ValueChangeEvent<Boolean> event) {
                    CheckBoxUnique.doUnique(RList, event);
                }
            });
            checkBox.setStyleName("Rclass");
            checkBox.setFormValue(Double.toString(i));
            rGrid.setWidget(1, i, checkBox);
            RList.add(checkBox);
        }
        rGrid.addStyleName("tableR");
        RList.get(0).setValue(true);
    }
    private void buildPointGrid(){
        pointGrid.setText(0, 0, "X");
        pointGrid.setText(0, 1, "Y");
        pointGrid.setText(0, 2, "R");
        pointGrid.setText(0, 3, "Is Hitted");
        pointGrid.addStyleName("tableRes");
    }
    private Double getX(){
        for(int i=0;i<XList.size();i++){
            if(XList.get(i).getValue()){
                return Double.valueOf(XList.get(i).getFormValue());
            }
        }
        return null;
    }
    private Double getY(){
        return YValidator.checkAndReturnY(yText.getText());
    }
    private Double getR(){
        for(int i=0;i<RList.size();i++){
            if(RList.get(i).getValue()){
                return Double.valueOf(RList.get(i).getFormValue());
            }
        }
        return null;
    }
}
