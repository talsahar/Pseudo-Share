package com.tal.pseudo_share.data;

import android.arch.persistence.room.TypeConverter;

/**
 * Created by User on 06/01/2018.
 */

public enum PseudoType {
    Implementation,
    Strings,
    Search,
    Graphs,
    Greedy,
    Recursion;

    @TypeConverter
    public static PseudoType toPseudoType(String pseudoType) {
        return pseudoType == null ? null : PseudoType.valueOf(pseudoType);
    }

    @TypeConverter
    public static String toString(PseudoType pseudoType) {
        return pseudoType == null ? null : pseudoType.name();
    }

}