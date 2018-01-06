/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tal.pseudo_share.db.utils;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tal.pseudo_share.db.AppDatabase;
import com.tal.pseudo_share.db.entity.Pseudo;

import java.util.Calendar;
import java.util.Date;

public class DatabaseInitializer {

    private static final int DELAY_MILLIS = 500;

    public static void populateAsync(final AppDatabase db) {

        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static Pseudo addPseudo(final AppDatabase db, Pseudo pseudo) {
        db.pseudoDao().insertPseudo(pseudo);
        return pseudo;
    }

    private static void populateWithTestData(AppDatabase db) {
        db.pseudoDao().deleteAll();
        Pseudo pseudo1 = new Pseudo("BFS", "Tal", "Cool alg", getTodayPlusDays(0), null, null, 0);
        Pseudo pseudo2 = new Pseudo("DFS", "Tal", "Cool alg", getTodayPlusDays(0), null, null, 0);
        Pseudo pseudo3 = new Pseudo("Dijkstra", "Tal", "Cool alg", getTodayPlusDays(0), null, null, 0);

        Pseudo user1 = addPseudo(db, pseudo1);
        Pseudo user2 = addPseudo(db, pseudo2);
        Pseudo user3 = addPseudo(db, pseudo3);
    }

    private static Date getTodayPlusDays(int daysAgo) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, daysAgo);
        return calendar.getTime();
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
