package com.github.chen0040.spm.data;


import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Created by xschen on 9/5/2017.
 */
@Getter
public class MetaData {

   private List<String> uniqueItems;

   public MetaData(List<Sequence> database) {
      Set<String> uniqueItems = new HashSet<>();
      for(Sequence sequence : database){
         for(int i=0; i < sequence.countElements(); ++i){
            ItemSetWithTimeId element = sequence.elementAt(i);
            uniqueItems.addAll(element.getItems());
         }
      }

      this.uniqueItems = uniqueItems.stream().collect(Collectors.toList());
   }
}
