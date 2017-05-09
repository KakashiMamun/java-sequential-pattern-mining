package com.github.chen0040.spm.data;


/**
 * Created by xschen on 8/2/2015.
 */
public class ItemSetWithTimeWindow extends ItemSetWithTimeId {
   private long startTime;
   private long endTime;

   @Override public long getTime() {
      return (startTime + endTime) / 2;
   }


   public long getStartTime() {
      return startTime;
   }


   public void setStartTime(long startTime) {
      this.startTime = startTime;
   }


   public long getEndTime() {
      return endTime;
   }


   public void setEndTime(long endTime) {
      this.endTime = endTime;
   }


   @Override public boolean isBefore(ItemSetWithTimeId rhs) {
      if(rhs instanceof ItemSetWithTimeWindow) {
         return endTime <= ((ItemSetWithTimeWindow)rhs).getStartTime();
      } else {
         return super.isBefore(rhs);
      }
   }

   @Override
   public boolean equals(Object obj) {
      if(obj instanceof ItemSetWithTimeId) {
         return super.equals(obj);
      }
      return false;
   }

   @Override
   public int hashCode(){
      return super.hashCode();
   }
}
