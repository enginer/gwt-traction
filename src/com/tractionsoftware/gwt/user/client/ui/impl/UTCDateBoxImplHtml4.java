/****************************************************************

  Traction Software, Inc. Confidential and Proprietary Information

  Copyright (c) 1996-2012 Traction Software, Inc.
  All rights reserved.

****************************************************************/

// PLEASE DO NOT DELETE THIS LINE -- make copyright depends on it.

package com.tractionsoftware.gwt.user.client.ui.impl;

import java.util.Date;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.tractionsoftware.gwt.user.client.ui.UTCDateBox;

/**
 * @author andy
 */
public class UTCDateBoxImplHtml4 extends UTCDateBoxImplShared {

    private HandlerManager handlerManager;
    private DateBox datebox;

    public UTCDateBoxImplHtml4() {
        handlerManager = new HandlerManager(this);
        datebox = new DateBox();
        datebox.addValueChangeHandler(new ValueChangeHandler<Date>() {

            @Override
            public void onValueChange(ValueChangeEvent<Date> event) {
                // pass this event onto our handlers after converting
                // the value
                fireValueChangeEvent(UTCDateBox.date2utc(event.getValue()));
            }
        });
    }
    
    @Override
    public Widget asWidget() {
        return datebox;
    }

    @Override
    public Long getValue() {
        return UTCDateBox.date2utc(datebox.getValue());
    }

    @Override
    public void setValue(Long value, boolean fireEvents) {
        datebox.setValue(UTCDateBox.utc2date(value), fireEvents);        
    }

    @Override
    public String getText() {
        return datebox.getTextBox().getValue();
    }

    @Override
    public void setText(String text) {
        String oldValue = getText();
        datebox.getTextBox().setValue(text, true);
        if (oldValue == null || !oldValue.equals(text)) {
            ValueChangeEvent.fire(this, getValue());
        }        
    }

    @Override
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Long> handler) {
        return handlerManager.addHandler(ValueChangeEvent.getType(), handler);
    }

    @Override
    public void fireEvent(GwtEvent<?> event) {
        handlerManager.fireEvent(event);
    }
    
    public void fireValueChangeEvent(long value) {
        ValueChangeEvent.fire(this, new Long(value));             
    }

    // ----------------------------------------------------------------------    
    
    @Override
    public void setDateFormat(DateTimeFormat dateFormat) {
        datebox.setFormat(new DateBox.DefaultFormat(dateFormat));
    }

    @Override
    public void setVisibleLength(int length) {
        datebox.getTextBox().setVisibleLength(length);
    }

    @Override
    public void setTabIndex(int tabIndex) {
        datebox.getTextBox().setTabIndex(tabIndex);
    }

}