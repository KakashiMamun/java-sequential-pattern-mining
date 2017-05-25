package com.github.chen0040.spm;


import com.github.chen0040.spm.data.Sequence;
import com.github.chen0040.spm.data.Sequences;

import java.util.List;


/**
 * Created by xschen on 8/2/2015.
 */
public abstract class AbstractSequentialAssocRuleMiner implements SequentialAssocRuleMiner {

   private int minSupportLevel;


   public int getMinSupportLevel() {
      return minSupportLevel;
   }


   public void setMinSupportLevel(int minSupportLevel) {
      this.minSupportLevel = minSupportLevel;
   }


   public abstract Sequences minePatterns(Iterable<? extends Sequence> database, List<String> uniqueItems, long maxTimeWindow);
   public  Sequences minePatterns(Iterable<? extends Sequence> database, List<String> uniqueItems) {
      return minePatterns(database, uniqueItems, -1);
   }


   public Sequences findMaxPatterns(Iterable<? extends Sequence> database, List<String> uniqueItems) {
       return null;
   }
}
