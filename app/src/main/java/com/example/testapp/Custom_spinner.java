package com.example.testapp;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;

public class Custom_spinner extends AppCompatSpinner {
    public Custom_spinner(@NonNull Context context) {
        super(context);
    }

    public Custom_spinner(@NonNull Context context, int mode) {
        super(context, mode);
    }

    public Custom_spinner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Custom_spinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public Custom_spinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public Custom_spinner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public interface OnSpinnerEventsListener {
        void onPopupWindowOpened(Spinner spinner);
        void onPopupWindowClosed(Spinner spinner);
    }

    private OnSpinnerEventsListener mListener;
    private boolean mOpenInitiated = false;

    @Override
    public boolean performClick() {
        mOpenInitiated = true;
        if (mListener != null) {
            mListener.onPopupWindowOpened(this);
        }
        return super.performClick();
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        if (hasBeenOpened() && hasFocus) {
            performClosedEvent();
        }
    }

    /**
     * Register the listener which will listen for events.
     */
    public void setSpinnerEventsListener(
            OnSpinnerEventsListener onSpinnerEventsListener) {
        mListener = onSpinnerEventsListener;
    }

    /**
     * Propagate the closed Spinner event to the listener from outside if needed.
     */
    public void performClosedEvent() {
        mOpenInitiated = false;
        if (mListener != null) {
            mListener.onPopupWindowClosed(this);
        }
    }

    /**
     * A boolean flag indicating that the Spinner triggered an open event.
     *
     * @return true for opened Spinner
     */
    public boolean hasBeenOpened() {
        return mOpenInitiated;
    }
}
