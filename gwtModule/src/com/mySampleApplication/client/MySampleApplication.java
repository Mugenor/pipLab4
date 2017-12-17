package com.mySampleApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.DOM;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MySampleApplication implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public void onModuleLoad() {
        final Button button = new Button("Add point2");
        final Label label = new Label();

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                if (label.getText().equals("")) {
         //           MySampleApplicationService.App.getInstance().getMessage("Hello, World!", new MyAsyncCallback(label));
                } else {
                    label.setText("");
                }
            }
        });

        button.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                boolean b = Validator.checkX(label.getText());
                if (b) {label.setText("");
                       // gwt alert or red label
                      }
                else {}
            }
        });

    //    RootPanel.get("addButton").add(button);
      /*  RootPanel.get("slot1").add(button);
        RootPanel.get("slot2").add(label);*/
    }

    private static class MyAsyncCallback implements AsyncCallback<String> {
        private Label label;

        public MyAsyncCallback(Label label) {
            this.label = label;
        }

        public void onSuccess(String result) {
            label.getElement().setInnerHTML(result);
        }

        public void onFailure(Throwable throwable) {
            label.setText("Failed to receive answer from server!");
        }
    }
}
