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

package andrompd.org.andrompd.application.fragments.database;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;


import java.util.List;

import andrompd.org.andrompd.R;
import andrompd.org.andrompd.application.adapters.FileAdapter;
import andrompd.org.andrompd.application.callbacks.OnSaveDialogListener;
import andrompd.org.andrompd.application.fragments.SaveDialog;
import andrompd.org.andrompd.application.loaders.PlaylistsLoader;
import andrompd.org.andrompd.mpdservice.mpdprotocol.mpddatabase.MPDFileEntry;
import andrompd.org.andrompd.mpdservice.mpdprotocol.mpddatabase.MPDPlaylist;

public class ChoosePlaylistDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<List<MPDFileEntry>> {

    /**
     * Listener to save the bookmark
     */
    OnSaveDialogListener mSaveCallback;

    /**
     * Adapter used for the ListView
     */
    private FileAdapter mPlaylistsListViewAdapter;

    @Override
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

    /**
     * This method creates a new loader for this fragment.
     *
     * @param id   The id of the loader
     * @param args Optional arguments
     * @return Return a new Loader instance that is ready to start loading.
     */
    @Override
    public Loader<List<MPDFileEntry>> onCreateLoader(int id, Bundle args) {
        return new PlaylistsLoader(getActivity(),true);
    }

    /**
     * Called when the loader finished loading its data.
     *
     * @param loader The used loader itself
     * @param data   Data of the loader
     */
    @Override
    public void onLoadFinished(Loader<List<MPDFileEntry>> loader, List<MPDFileEntry> data) {
        mPlaylistsListViewAdapter.swapModel(data);
    }

    /**
     * If a loader is reset the model data should be cleared.
     *
     * @param loader Loader that was resetted.
     */
    @Override
    public void onLoaderReset(Loader<List<MPDFileEntry>> loader) {
        mPlaylistsListViewAdapter.swapModel(null);
    }

    /**
     * Create the dialog to choose to override an existing bookmark or to create a new bookmark.
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        mPlaylistsListViewAdapter = new FileAdapter(getActivity(), false);

        builder.setTitle(getString(R.string.dialog_choose_playlist)).setAdapter(mPlaylistsListViewAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (which == 0) {
                    // open save dialog to create a new bookmark
                    SaveDialog saveDialog = new SaveDialog();
                    Bundle arguments = new Bundle();
                    arguments.putSerializable(SaveDialog.ARG_OBJECTTYPE, SaveDialog.OBJECTTYPE.PLAYLIST);
                    saveDialog.setArguments(arguments);
                    saveDialog.show(((AppCompatActivity) getContext()).getSupportFragmentManager(), "SaveDialog");
                } else {
                    // override existing bookmark
                    MPDPlaylist playlist = (MPDPlaylist) mPlaylistsListViewAdapter.getItem(which);
                    String objectTitle = playlist.getPath();
                    mSaveCallback.onSaveObject(objectTitle, SaveDialog.OBJECTTYPE.PLAYLIST);
                }
            }
        }).setNegativeButton(R.string.dialog_action_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog dont save object
                getDialog().cancel();
            }
        });

        // Prepare loader ( start new one or reuse old )
        getLoaderManager().initLoader(0, getArguments(), this);

        // set divider
        AlertDialog dlg = builder.create();
        dlg.getListView().setDivider(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.colorDivider)));
        dlg.getListView().setDividerHeight(getResources().getDimensionPixelSize(R.dimen.list_divider_size));

        return dlg;
    }
}
