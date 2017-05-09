package com.github.chen0040.spm.data;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 8/2/2015.
 */
public class Sequences {
   private List<Sequence> sequences = new ArrayList<>();


   public void add(Sequence sequence) {
      sequences.add(sequence);
   }


   public List<Sequence> getSequences() {
      return sequences;
   }


   public void setSequences(List<Sequence> sequencies) {
      this.sequences = sequencies;
   }


   public int countSequences() {
      return sequences.size();
   }


   public Sequence sequenceAt(int i) {
      return sequences.get(i);
   }
}
