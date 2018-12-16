package com.rpl.service.util;

import java.util.List;

import com.rpl.model.Range;

public class RangeUtils {

    public static String calculatePersonRangeName(Integer points, List<Range> sortedRanges) {

        if (sortedRanges.isEmpty() || sortedRanges.get(0).getMinScore() > points)
            return null;
        Range previousRange = sortedRanges.get(0);
        for (Range r : sortedRanges) {
            if (r.getMinScore() > points)
                return previousRange.getRangeName();
            previousRange = r;
        }
        return sortedRanges.get(sortedRanges.size() - 1).getRangeName();
    }

}
