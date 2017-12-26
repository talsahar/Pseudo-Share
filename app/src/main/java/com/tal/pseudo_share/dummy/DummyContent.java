package com.tal.pseudo_share.dummy;

import com.tal.pseudo_share.model.Pseudo;
import com.tal.pseudo_share.model.PseudoImage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DummyContent {


    public static final List<Pseudo> list = new ArrayList<Pseudo>();


    public static final Map<String, Pseudo> hashMap = new HashMap<String, Pseudo>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(Pseudo item) {
        list.add(item);
        hashMap.put(item.getName(), item);
    }

    private static Pseudo createDummyItem(int position) {
        return new Pseudo("pseudo "+position, "Tal", new Date(11,22,1994), null, "Hello world", 5);

    }

 
    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
