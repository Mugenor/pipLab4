package com.mySampleApplication.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.DOM;
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
    Canvas canvas;
    Context2d context;
    static final int canvasWidth = 440;
    static final int canvasHeight = 440;
    int half = canvasWidth / 2;
    int pad = canvasWidth / 44;
    int mult = canvasWidth / 20;

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
    private Label pointLabel = new Label();

    public void onModuleLoad() {
        Defaults.setServiceRoot(GWT.getHostPageBaseURL());

        if (storage == null) {
            Window.alert("Sorry, but storage is not supported!");
            Window.Location.assign(GWT.getHostPageBaseURL() + beginPage);
        }
        String userJson = storage.getItem("user");
        if (userJson == null) {
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


        printCanvas(3);
        RootPanel.get().add(pointLabel);
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
                    StringBuilder str = new StringBuilder();
                    if(x==null){
                        str.append("X must be from -4 to 4!\n");
                    }
                    if(y==null){
                        str.append("Y must be from -5 to 5!\n");
                    }
                    if(r==null){
                        str.append("R must be from 1 to 4!");
                    }
                    pointLabel.setText(str.toString());
                    return;
                }
                pointLabel.setText("");
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
                        if(userPoint.checkHitted()){
                            RootPanel.get().removeStyleName("dontGotIt");
                            RootPanel.get().addStyleName("gotIt");
                        } else {
                            RootPanel.get().removeStyleName("gotIt");
                            RootPanel.get().addStyleName("dontGotIt");
                        }
                        user.getPoints().add(new Point(userPoint.getX(), userPoint.getY(), userPoint.getR()));
                        storage.removeItem("user");
                        storage.setItem("user", UserParser.encode(user));
                    }
                });
            }
        });
        if (user.getPoints().size() != 0) {
            pointGrid.resizeRows(user.getPoints().size() + 1);
            for (int i = 0; i < user.getPoints().size(); i++) {
                pointGrid.setText(i + 1, 0, user.getPoints().get(i).getX().toString());
                pointGrid.setText(i + 1, 1, user.getPoints().get(i).getY().toString());
                pointGrid.setText(i + 1, 2, user.getPoints().get(i).getR().toString());
                pointGrid.setText(i + 1, 3, Boolean.toString(user.getPoints().get(i).checkHitted()));
            }
        }
    }

    public void printCanvas(int r) {
        canvas = Canvas.createIfSupported();
        if (canvas == null) {
            RootPanel.get("right").add(new Label("Sorry, your browser doesn't support the HTML5 Canvas element"));
            return;
        }

        canvas.setStyleName("mainCanvas");
        canvas.setWidth(canvasWidth + "px");
        canvas.setCoordinateSpaceWidth(canvasWidth);
        canvas.setHeight(canvasHeight + "px");
        canvas.setCoordinateSpaceHeight(canvasHeight);

        RootPanel.get("right").add(canvas);
        context = canvas.getContext2d();

        context.setFillStyle(CssColor.make("white"));
        context.fillRect(0, 0, canvasWidth, canvasHeight);
        context.fill();

        repaint(r);

        context.setStrokeStyle(CssColor.make("black"));
        context.setFillStyle(CssColor.make("black"));
        context.moveTo(half, pad);
        context.lineTo(half, canvasHeight - pad);
        context.stroke();
        context.moveTo(pad, half);
        context.lineTo(canvasWidth - pad, half);
        context.stroke();

        context.moveTo(half, pad);
        context.lineTo(half - pad / 2, pad + pad);
        context.stroke();
        context.moveTo(half, pad);
        context.lineTo(half + pad / 2, pad + pad);
        context.stroke();
        context.moveTo(canvasWidth - pad, half);
        context.lineTo(canvasWidth - pad - pad, half - pad / 2);
        context.stroke();
        context.moveTo(canvasWidth - pad, half);
        context.lineTo(canvasWidth - pad - pad, half + pad / 2);
        context.stroke();
    }

    public void repaint(double r) {
        context.setStrokeStyle(CssColor.make("red"));
        context.setFillStyle(CssColor.make("#38a6ff"));
        context.beginPath();
        context.moveTo(half, half);
        context.lineTo(half - r * mult, half);
        context.lineTo(half, half - r * mult);
        context.lineTo(half + r * mult, half);
        context.lineTo(half, half);
        context.closePath();
        context.fill();

        context.rect(half, half, 2 * r * mult, 2 * r * mult);
        context.fill();
        context.arc(half, half, r * mult, 1.5 * Math.PI, 2 * Math.PI);
        context.fill();

        context.setStrokeStyle("black");
        context.beginPath();
        context.moveTo(half - r * mult, half - pad / 3);
        context.lineTo(half - r * mult, half + pad / 3);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half - 2 * r * mult, half - pad / 3);
        context.lineTo(half - 2 * r * mult, half + pad / 3);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half + r * mult, half - pad / 3);
        context.lineTo(half + r * mult, half + pad / 3);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half + 2 * r * mult, half - pad / 3);
        context.lineTo(half + 2 * r * mult, half + pad / 3);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half - pad / 3, half - 2 * r * mult);
        context.lineTo(half + pad / 3, half - 2 * r * mult);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half - pad / 3, half - r * mult);
        context.lineTo(half + pad / 3, half - r * mult);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half - pad / 3, half + r * mult);
        context.lineTo(half + pad / 3, half + r * mult);
        context.closePath();
        context.stroke();

        context.beginPath();
        context.moveTo(half - pad / 3, half + 2 * r * mult);
        context.lineTo(half + pad / 3, half + 2 * r * mult);
        context.closePath();
        context.stroke();

        context.setFont("16px Arial");
        if (r < 3) context.setFont("12px Arial");
        context.setFillStyle("black");
        context.fillText("X", canvasWidth - 2 * pad, half - pad);
        context.fillText("Y", half + pad, 2 * pad);
        context.fillText("0", half + 3, half - pad / 2);
        context.fillText(String.valueOf(r), half + r * mult - 4, half - pad / 2);
        context.fillText(String.valueOf(r * 2), half + 2 * r * mult - 4, half - pad / 2);
        context.fillText(String.valueOf(-r), half - r * mult - 7, half - pad / 2);
        context.fillText(String.valueOf(-r * 2), half - 2 * r * mult - 7, half - pad / 2);
        context.fillText(String.valueOf(r), half + 6, half - r * mult + 4);
        context.fillText(String.valueOf(r * 2), half + 6, half - 2 * r * mult + 4);
        context.fillText(String.valueOf(-r), half + 6, half + r * mult + 4);
        context.fillText(String.valueOf(-r * 2), half + 6, half + 2 * r * mult + 4);
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

