package com.github.chen0040.spm.data;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Created by xschen on 8/2/2015.
 */
public class Sequence {

   private static final Logger logger = LoggerFactory.getLogger(Sequence.class);

   private List<ItemSetWithTimeId> elements = new ArrayList<>();
   private int support;


   public Sequence(){

   }


   public List<ItemSetWithTimeId> getElements(){
      return elements;
   }


   public boolean containsItem(String item) {
      for(ItemSetWithTimeId t : elements){
         if(t.containsItem(item)){
            return true;
         }
      }
      return false;
   }


   public void addElement(ItemSetWithTimeId itemSet) {
      elements.add(itemSet);
   }


   public Sequence dropFirstItem() {
      Sequence clone = makeCopy();
      clone.getElements().get(0).dropFirstItem();
      if(clone.firstElement().countItems()==0){
         clone.getElements().remove(0);
      }

      return clone;
   }

   private ItemSetWithTimeId firstElement(){
      return elements.get(0);
   }

   public Sequence makeCopy(){
      Sequence clone = new Sequence();
      clone.copy(this);
      return clone;
   }

   public void copy(Sequence rhs){
      elements.clear();
      for(int i = 0; i < rhs.elements.size(); ++i){
         ItemSetWithTimeId itemSet = rhs.elements.get(i);
         elements.add((ItemSetWithTimeId)itemSet.makeCopy());
      }
   }

   public Sequence dropLastItem() {
      Sequence clone = makeCopy();
      clone.lastElement().dropLastItem();
      if(clone.lastElement().countItems() == 0){
         clone.getElements().remove(clone.countElements()-1);
      }
      return clone;
   }


   @Override public int hashCode() {
      int hash = 3001;
      for(int i = 0; i < elements.size(); ++i){
         hash = hash * 31 + elements.get(i).hashCode();
      }
      return hash;
   }


   @Override public boolean equals(Object obj) {
      if(!(obj instanceof Sequence)){
         return false;
      }

      Sequence rhs = (Sequence)obj;

      if(elements.size() != rhs.elements.size()) {
         return false;
      }

      for(int i = 0; i < elements.size(); ++i){
         if(!elements.get(i).equals(rhs.elements.get(i))){
            return false;
         }
      }
      return true;
   }

   public boolean isLastItemSeparateElement(){
      return lastElement().countItems()==1;
   }

   public boolean isFirstItemSeparateElement(){
      return firstElement().countItems() == 0;
   }

   public ItemSetWithTimeId lastElement(){
      return elements.get(elements.size()-1);
   }


   public Sequence append(String item, boolean itemAsSeparateElement) {
      Sequence clone = makeCopy();

      if(itemAsSeparateElement){
         ItemSetWithTimeId element = new ItemSetWithTimeId();
         element.addItem(item);

         clone.addElement(element);
      } else {
         ItemSetWithTimeId element = clone.lastElement();
         if(!element.containsItem(item)) {
            element.addItem(item);
         }
      }

      return clone;
   }


   public String lastItem() {
      return lastElement().lastItem();
   }

   public String firstItem(){
      return firstElement().firstItem();
   }

   public int countElements(){
      return elements.size();
   }


   public boolean contains(Sequence candidate, long maxTimeWindow) {
      int offset = 0;
      int prevOffset = -1;
      boolean contained = true;
      int candidateOffset = 0;
      //logger.info("sequence: {}", this);
      //logger.info("candidate: {}", candidate);

      while(candidateOffset < candidate.countElements()){
         boolean maxTimeWindowExceeded = false;
         while(offset < countElements() && !candidate.elementAt(candidateOffset).isSubsetOf(elementAt(offset))){
            offset++;

            //logger.info("candidate_offset: {} offset: {}", candidateOffset, offset);

            if(offset < countElements() && prevOffset != -1 && maxTimeWindow > 0){
               if(elementAt(offset).timeElapsed(elementAt(prevOffset)) > maxTimeWindow){
                  maxTimeWindowExceeded = true;
                  break;
               }
            }
         }


         if(maxTimeWindowExceeded) {
            if (prevOffset != -1) {
               candidateOffset = candidateOffset - 1;
               offset = prevOffset + 1;
               continue;
            }
            else {
               contained = false;
               break;
            }
         }

         if(offset == countElements()) {
            contained = false;
            break;
         } else {
            prevOffset = offset;
         }

         ++candidateOffset;
         offset++;

         //logger.info("candidate_offset: {} offset: {}", candidateOffset, offset);
      }

      //logger.info("candidate_offset: {} offset: {}", candidateOffset, offset);



      if(candidateOffset != candidate.countElements()){
         contained = false;

      }

      //logger.info("{} {} contains {}", this, contained ? "" : "does not", candidate);

      return contained;
   }


   public ItemSetWithTimeId elementAt(int i) {
      return elements.get(i);
   }


   public boolean containsOnlyOneItem() {
      if(elements.size() == 1){
         return elements.get(0).countItems()==1;
      }
      return false;
   }

   @Override
   public String toString(){
      return "<(" + elements.stream().map(element -> element.getItems().stream().collect(Collectors.joining(", ")))
              .collect(Collectors.joining("), (")) + ")> [support: " + support + "]";
   }


   public void setSupport(int support) {
      this.support = support;
   }


   public int getSupport() {
      return support;
   }


   public int countItems() {
      return elements.stream().map(element -> element.countItems()).reduce((a, b) -> a + b).get();
   }


   public Sequence prepend(String item, boolean itemAsSeparateElement) {
      Sequence clone = makeCopy();

      if(itemAsSeparateElement){
         ItemSetWithTimeId element = new ItemSetWithTimeId();
         element.prependItem(item);

         clone.prependElement(element);
      } else {
         ItemSetWithTimeId element = clone.firstElement();
         if(!element.containsItem(item)) {
            element.prependItem(item);
         }
      }

      return clone;
   }


   private void prependElement(ItemSetWithTimeId element) {
      List<ItemSetWithTimeId> temp = elements;
      elements = new ArrayList<>();
      elements.add(element);
      elements.addAll(temp);
   }


}
