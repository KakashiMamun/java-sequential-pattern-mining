package com.github.chen0040.spm.apriori;

import com.github.chen0040.spm.AbstractSequentialAssocRuleMiner;
import com.github.chen0040.spm.data.ItemSetWithTimeId;
import com.github.chen0040.spm.data.Sequence;
import com.github.chen0040.spm.data.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by xschen on 8/2/2015.
 * implementation of the algorithm is based on Srikant & Agrawal, 1996
 *
 * The algorithm makes multiple passes over the data. The first pass determines the support of each item, that
 * is, the numbre of data-sequences that include the item. At the end of the first pas, the algorithm knows
 * which items are frequent, that is, have minimum support. Each such item yields a 1-element frequent sequence
 * consisting of that item.
 *
 * Each subsequent pass starts with a seed set: the frequent sequences found in the previous pass. The seed set
 * is used to generate new potentially frequent sequences, called candidate sequences. Each candidate sequence
 * has one more item than a seed sequence; so all the candidate sequences in a pass will have the same number of
 * items. The support for these candidate sequences is found during the pass over the data. At the end of the
 * pass, the algorithm determines which of the candidate sequences are actually frequent. These frequent candidates
 * become the seed for the next pass.
 */
public class GSP extends AbstractSequentialAssocRuleMiner {

   private static final Logger logger = LoggerFactory.getLogger(GSP.class);

   public Sequences minePatterns(Iterable<? extends Sequence> database, List<String> uniqueItems, long maxTimeWindow){
      Map<String, Integer> frequency = new HashMap<>();

      for(Sequence sequence : database){
         for(String item : uniqueItems){
            if(sequence.containsItem(item)){
               frequency.put(item, frequency.getOrDefault(item, 0) + 1);
            }
         }
      }

      List<Sequence> seeds = frequency.entrySet().stream()
              .filter(entry -> entry.getValue() >= getMinSupportLevel())
              .map(entry -> {
                 String item = entry.getKey();
                 int itemCount = entry.getValue();
                 Sequence sequence = new Sequence();

                 ItemSetWithTimeId itemSet = new ItemSetWithTimeId();
                 itemSet.addItem(item);
                 itemSet.setSupport(itemCount);

                 sequence.addElement(itemSet);

                 return sequence;
              }).collect(Collectors.toList());

      Sequences result = new Sequences();

      int k = 1;
      while(!seeds.isEmpty()){
         List<Sequence> candidates = new ArrayList<>();

         for(int i=0; i < seeds.size(); ++i){
            Sequence s1 = seeds.get(i);
            for(int j=0; j < seeds.size(); ++j) {

               if(i == j) {
                  continue;
               }

               Sequence s2 = seeds.get(j);


               if(k == 1) {
                  candidates.addAll(level2Join(s1, s2));
               } else {
                  if(canJoin(s1, s2)){
                     candidates.addAll(join(s1, s2));
                  }
               }

            }
         }

         int candidateCount = candidates.size();

         for(int i= candidateCount-1; i >= 0; --i){
            int support = 0;
            Sequence candidate = candidates.get(i);

            if(candidate.countItems() != k+1){
               candidates.remove(i);
               continue;
            }

            for(Sequence records: database){
               if(records.contains(candidate, maxTimeWindow)){
                  support++;
               }
            }

            candidate.setSupport(support);

            if(support >= getMinSupportLevel()){
               result.add(candidate);
            } else {
               candidates.remove(i);
            }
         }



         seeds = candidates;

         k++;
      }

      return result;

   }




   private boolean canJoin(Sequence s1, Sequence s2){

      Sequence s1Prime = s1.dropFirstItem();
      Sequence s2Prime = s2.dropLastItem();

      return s1Prime.equals(s2Prime);
   }

   private List<Sequence> join(Sequence s1, Sequence s2){

      Sequence s1p = s1.append(s2.lastItem(), s2.isLastItemSeparateElement());

      return Collections.singletonList(s1p);


   }

   private List<Sequence> level2Join(Sequence s1, Sequence s2){


      List<Sequence> result = new ArrayList<>();

      Sequence s1p = s1.append(s2.lastItem(), true);
      Sequence s2p = s1.append(s2.lastItem(), false);



      result.add(s1p);
      result.add(s2p);

      return result;
   }
}
