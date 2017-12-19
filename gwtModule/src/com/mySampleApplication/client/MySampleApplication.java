package com.mySampleApplication.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class MySampleApplication implements EntryPoint {
    Canvas canvas;
    Context2d context;
    static final int canvasWidth = 440;
    static final int canvasHeight = 440;
    int half = canvasWidth/2;
    int pad = canvasWidth/44;
    int mult = canvasWidth/20;

    public void onModuleLoad() {
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

        repaint(3);

        context.setStrokeStyle(CssColor.make("black"));
        context.setFillStyle(CssColor.make("black"));
        context.moveTo(half, pad);
        context.lineTo(half, canvasHeight - pad);
        context.stroke();
        context.moveTo(pad, half);
        context.lineTo(canvasWidth - pad, half);
        context.stroke();

        context.moveTo(half, pad);
        context.lineTo(half - pad /2, pad + pad);
        context.stroke();
        context.moveTo(half, pad);
        context.lineTo(half + pad /2, pad + pad);
        context.stroke();
        context.moveTo(canvasWidth - pad, half);
        context.lineTo(canvasWidth - pad - pad, half - pad /2);
        context.stroke();
        context.moveTo(canvasWidth - pad, half);
        context.lineTo(canvasWidth - pad - pad, half + pad /2);
        context.stroke();



    }

    public void repaint(double r) {
        context.setStrokeStyle(CssColor.make("red"));
        context.setFillStyle(CssColor.make("#38a6ff"));
        context.moveTo(half, half);
        context.lineTo(half - r*mult, half);
        context.lineTo(half, half - r*mult);
        context.lineTo(half + r*mult, half);
        context.lineTo(half, half);
        context.fill();
        context.rect(half, half, 2*r*mult, 2*r*mult);
        context.fill();
        context.arc(half, half, r*mult, 1.5*Math.PI, 2*Math.PI);
        context.fill();

        context.setStrokeStyle("black");
        context.moveTo(half - r*mult, half - pad /3);
        context.lineTo(half - r*mult, half + pad /3);
        context.stroke();
        context.moveTo(half - 2*r*mult, half - pad /3);
        context.lineTo(half - 2*r*mult, half + pad /3);
        context.stroke();
        context.moveTo(half + r*mult, half - pad /3);
        context.lineTo(half + r*mult, half + pad /3);
        context.stroke();
        context.moveTo(half + 2*r*mult, half - pad /3);
        context.lineTo(half + 2*r*mult, half + pad /3);
        context.stroke();
        context.moveTo(half - pad /3, half - 2*r*mult);
        context.lineTo(half + pad /3, half - 2*r*mult);
        context.stroke();
        context.moveTo(half - pad /3, half - r*mult);
        context.lineTo(half + pad /3, half - r*mult);
        context.stroke();
        context.moveTo(half - pad /3, half + r*mult);
        context.lineTo(half + pad /3, half + r*mult);
        context.stroke();
        context.moveTo(half - pad /3, half + 2*r*mult);
        context.lineTo(half + pad /3, half + 2*r*mult);
        context.stroke();

        context.setFont("16px Arial");
        if (r<3) context.setFont("12px Arial");
        context.setFillStyle("black");
        context.fillText("X", canvasWidth - 2* pad, half - pad);
        context.fillText ("Y", half + pad, 2*pad);
        context.fillText("0", half + 3, half - pad/2);
        context.fillText(String.valueOf(r), half + r*mult - 4, half - pad/2);
        context.fillText(String.valueOf(r*2), half + 2*r*mult - 4, half - pad/2);
        context.fillText(String.valueOf(-r), half - r*mult - 7, half - pad/2);
        context.fillText(String.valueOf(-r*2), half - 2*r*mult - 7, half - pad/2);
        context.fillText(String.valueOf(r), half + 6, half - r*mult + 4);
        context.fillText(String.valueOf(r*2), half + 6, half - 2*r*mult + 4);
        context.fillText(String.valueOf(-r), half + 6, half + r*mult + 4);
        context.fillText(String.valueOf(-r*2), half + 6, half + 2*r*mult + 4);



    }
}

