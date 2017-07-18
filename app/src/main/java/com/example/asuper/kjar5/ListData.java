package com.example.asuper.kjar5;

import java.text.Collator;
import java.util.Comparator;

/**
 * Created by super on 2016-11-20.
 */

public class ListData {

    // 이름
    public String sort_comname;

    // 주소
    public String sort_comadd;

    /**
     * 알파벳 이름으로 정렬
     */
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.sort_comadd, mListDate_2.sort_comadd);
        }
    };
}
