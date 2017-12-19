package com.mySampleApplication.client.util;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.user.client.ui.CheckBox;
import java.util.List;

public class CheckBoxUnique{
    public static void doUnique(List<CheckBox> list, ValueChangeEvent<Boolean> event){
        if(!((CheckBox)event.getSource()).getValue()){
            ((CheckBox)event.getSource()).setValue(true);
            return;
        }
        for(int i=0;i<list.size();i++){
            if(list.get(i)!=event.getSource() && list.get(i).getValue()){
                list.get(i).setValue(false);
                return;
            }
        }
    }
}
