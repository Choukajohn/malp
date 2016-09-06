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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import org.gateshipone.malp.R;
import org.gateshipone.malp.application.activities.MainActivity;
import org.gateshipone.malp.application.artworkdatabase.ArtworkDatabaseManager;
import org.gateshipone.malp.application.artworkdatabase.ArtworkManager;


public class ArtworkSettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * Called to do initial creation of a fragment.
     *
     * This method will setup a listener to start the system audio equalizer.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // add listener to clear album data
        Preference clearAlbums = findPreference("pref_clear_album");
        clearAlbums.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                ArtworkDatabaseManager.getInstance(getContext()).clearAlbumImages();
                return true;
            }
        });

        // add listener to clear artist data
        Preference clearArtist = findPreference("pref_clear_artist");
        clearArtist.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

            public boolean onPreferenceClick(Preference preference) {
                ArtworkDatabaseManager.getInstance(getContext()).clearArtistImages();
                return true;
            }
        });
    }

    /**
     * Called when the fragment resumes.
     * <p/>
     * Register listener and setup the toolbar.
     */
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        // set toolbar behaviour and title
        MainActivity activity = (MainActivity) getActivity();
    }

    /**
     * Called when the Fragment is no longer resumed.
     * <p/>
     * Unregister listener.
     */
    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Create the preferences from an xml resource file
     */
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.malp_artwork_settings);
        PreferenceManager.setDefaultValues(getActivity(), R.xml.malp_artwork_settings, false);
    }

    /**
     * Called when a shared preference is changed, added, or removed.
     *
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("pref_album_provider")|| key.equals("pref_artist_provider")) {
            ArtworkManager.getInstance(getContext()).cancelAllRequests();
        }
    }

}
