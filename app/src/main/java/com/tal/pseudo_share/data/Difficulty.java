package com.tal.pseudo_share.data;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by User on 06/01/2018.
 */



public enum Difficulty {
    Easy,
    Normal,
    Hard;
        @TypeConverter
        public static Difficulty toDifficulty(String difficulty) {
            return difficulty == null ? null : Difficulty.valueOf(difficulty);
        }

        @TypeConverter
        public static String toTimeStamp(Difficulty difficulty) {
            return difficulty == null ? null : difficulty.name();
        }


}