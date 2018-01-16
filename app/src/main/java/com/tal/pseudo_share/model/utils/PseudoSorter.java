package com.tal.pseudo_share.model.utils;

import com.tal.pseudo_share.data.Pseudo;

import java.util.List;

/**
 * Created by User on 13/01/2018.
 */

public class PseudoSorter {

    public static void sortbyDate(List<Pseudo> list){
        for(int i=0;i<list.size();i++)
            for(int j=i+1;j<list.size();j++)
                if(list.get(i).getDate()<list.get(j).getDate())
                {
                    Pseudo tmp=list.get(i);
                    list.set(i,list.get(j));
                    list.set(j,tmp);
                }
    }

}
