package com.github.chen0040.spm.data;


/**
 * Created by xschen on 8/2/2015.
 */
public class ItemSetWithTimeId extends ItemSet {

   private long time;
   private long timeId = -1;

   public ItemSetWithTimeId(String[] items){
      super();
      for(String item : items){
         addItem(item);
      }
   }

   public ItemSetWithTimeId(){
      super();
   }

   public long getTimeId(){
      return timeId;
   }

   public void setTimeId(long timeId){
      this.timeId = timeId;
   }

   public long getTime() {
      return time;
   }


   public void setTime(long time) {
      this.time = time;
      if(timeId == -1) {
         timeId = time;
      }
   }

   public boolean isBefore(ItemSetWithTimeId rhs) {
      return getTime() < rhs.getTime();
   }

   @Override
   public ItemSet makeCopy(){
      ItemSetWithTimeId clone = new ItemSetWithTimeId();
      clone.copy(this);
      return clone;
   }

   @Override
   public void copy(ItemSet obj){
      super.copy(obj);

      if(obj instanceof ItemSetWithTimeId){
         ItemSetWithTimeId rhs = (ItemSetWithTimeId)obj;
         timeId = rhs.timeId;
         time = rhs.time;
      }
   }


   public long timeElapsed(ItemSetWithTimeId earlierItemSet) {
      return getTime() - earlierItemSet.getTime();
   }

   @Override
   public boolean equals(Object rhs) {
      if(rhs instanceof ItemSet) {
         return super.equals(rhs);
      }
      return false;
   }

   @Override
   public int hashCode(){
      return super.hashCode();
   }


}
