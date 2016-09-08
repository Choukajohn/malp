/*
 * Copyright (C) 2016  Hendrik Borghorst & Frederik Luetkes
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.gateshipone.malp.application.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

import org.gateshipone.malp.R;

public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Read theme preference
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String themePref = sharedPref.getString("pref_theme","indigo");

        switch (themePref) {
            case "indigo":
                setTheme(R.style.AppTheme_indigo);
                break;
            case "orange":
                setTheme(R.style.AppTheme_orange);
                break;
            case "deeporange":
                setTheme(R.style.AppTheme_deepOrange);
                break;
            case "blue":
                setTheme(R.style.AppTheme_blue);
                break;
            case "darkgrey":
                setTheme(R.style.AppTheme_darkGrey);
                break;
            case "brown":
                setTheme(R.style.AppTheme_brown);
                break;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        String versionName = "BLA";
        // get version from manifest
        try {
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView)findViewById(R.id.activity_about_version)).setText(versionName);

        findViewById(R.id.logo_musicbrainz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(getResources().getString(R.string.url_musicbrainz)));
                startActivity(urlIntent);
            }
        });

        findViewById(R.id.logo_lastfm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(getResources().getString(R.string.url_lastfm)));
                startActivity(urlIntent);
            }
        });

        findViewById(R.id.logo_fanarttv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(getResources().getString(R.string.url_fanarttv)));
                startActivity(urlIntent);
            }
        });
    }
}
