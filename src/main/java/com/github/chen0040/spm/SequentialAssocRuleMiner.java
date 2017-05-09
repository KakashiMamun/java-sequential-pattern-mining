package com.github.chen0040.spm;


import com.github.chen0040.spm.data.Sequence;
import com.github.chen0040.spm.data.Sequences;

import java.util.List;


/**
 * Created by xschen on 8/2/2015.
 */
public interface SequentialAssocRuleMiner {
   int getMinSupportLevel();
   void setMinSupportLevel(int level);
   Sequences findMaxPatterns(Iterable<? extends Sequence> database, List<String> uniqueItems);
   Sequences minePatterns(Iterable<? extends Sequence> database, List<String> uniqueItems, long maxTimeWindow);
   Sequences minePatterns(Iterable<? extends Sequence> database, List<String> uniqueItems);
}
