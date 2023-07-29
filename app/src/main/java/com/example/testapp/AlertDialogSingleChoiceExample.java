package com.example.testapp;
import android.app.Activity;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.HashSet;
import java.util.Set;

public class AlertDialogSingleChoiceExample {
    String rs;
    public String showAlertDialog(final Activity activity)  {
        String re;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // Set Title.
        builder.setTitle("Chọn bữa ăn");

        // Add a list
        final String[] meats = {"Sáng", "Trưa","Tối"};

        int checkedItem = 1; // Sheep
        final Set<String> selectedItems = new HashSet<String>();
        selectedItems.add(meats[checkedItem]);

        builder.setSingleChoiceItems(meats, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do Something...
                selectedItems.clear();
                selectedItems.add(meats[which]);
            }
        });

        //
        builder.setCancelable(true);
        builder.setIcon(R.drawable.plus_icon_24);
        // Create "Yes" button with OnClickListener.
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(selectedItems.isEmpty()) {
                    return ;
                }else {
                    String meats = selectedItems.iterator().next();
                    String rs = handleButtonClick(meats);
                    // Close Dialog
                    dialog.dismiss();
                    // Do something, for example: Call a method of Activity...
                    Toast.makeText(activity, "You select " + meats,
                            Toast.LENGTH_SHORT).show();

                }
            }

        });

        // Create "Cancel" button with OnClickListener.
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(activity,"You choose Cancel button",
                        Toast.LENGTH_SHORT).show();
                //  Cancel
                dialog.cancel();
            }
        });

        // Create AlertDialog:
        AlertDialog alert = builder.create();
        alert.show();
        return rs;
    }

    public String handleButtonClick(String value) {
        return value;
    }
}
