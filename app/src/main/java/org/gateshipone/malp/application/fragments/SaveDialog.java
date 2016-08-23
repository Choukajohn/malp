/*
 * Copyright (C) 2016  Hendrik Borghorst
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gateshipone.malp.application.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import org.gateshipone.malp.R;
import org.gateshipone.malp.application.callbacks.OnSaveDialogListener;


public class SaveDialog extends DialogFragment {

    public final static String ARG_OBJECTTYPE = "objecttype";

    public enum OBJECTTYPE {
        PLAYLIST, BOOKMARK
    }

    OnSaveDialogListener mSaveCallback;

    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mSaveCallback = (OnSaveDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSaveDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // read arguments to identify type of the object which should be saved
        Bundle mArgs = getArguments();
        final OBJECTTYPE type = (OBJECTTYPE) mArgs.get(ARG_OBJECTTYPE);

        String dialogTitle = "";
        String editTextDefaultTitle = "";

        if (type != null) {
            switch (type) {
                case PLAYLIST:
                    dialogTitle = getString(R.string.dialog_save_playlist);
                    editTextDefaultTitle = getString(R.string.default_playlist_title);
                    break;
            }
        }

        // create edit text for title
        final EditText editTextTitle = new EditText(getActivity());
        editTextTitle.setText(editTextDefaultTitle);
        builder.setView(editTextTitle);

        builder.setMessage(dialogTitle).setPositiveButton(R.string.dialog_action_save, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // accept title and call callback method
                String objectTitle = editTextTitle.getText().toString();
                mSaveCallback.onSaveObject(objectTitle, type);
            }
        }).setNegativeButton(R.string.dialog_action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog dont save object
                getDialog().cancel();
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
