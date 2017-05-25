package com.github.chen0040.spm.apriori;


import com.github.chen0040.spm.data.MetaData;
import com.github.chen0040.spm.data.Sequence;
import com.github.chen0040.spm.data.Sequences;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by xschen on 9/5/2017.
 */
public class GSPUnitTest {
   private static final Logger logger = LoggerFactory.getLogger(GSPUnitTest.class);

   @Test public void test_minePatterns() throws Exception {
      List<Sequence> database = createSimpleSequentialData();

      GSP method = new GSP();
      method.setMinSupportLevel(2);
      List<String> uniqueItems = new MetaData(database).getUniqueItems();
      Sequences result = method.minePatterns(database, uniqueItems, -1);

      result.getSequences().stream().forEach(sequence -> {
         logger.info("sequence: {}", sequence);
      });
   }


   public List<Sequence> createSimpleSequentialData(){
      List<Sequence> database = new ArrayList<>();

      /*
      S1 	(1), (1 2 3), (1 3), (4), (3 6)
      S2 	(1 4), (3), (2 3), (1 5)
      S3 	(5 6), (1 2), (4 6), (3), (2)
      S4 	(5), (7), (1 6), (3), (2), (3)
      */

      database.add(Sequence.make("1", "1,2,3", "1,3", "4", "3,6"));
      database.add(Sequence.make("1,4", "3", "2,3", "1,5"));
      database.add(Sequence.make("5,6", "1,2", "4,6", "3", "2"));
      database.add(Sequence.make("5", "7", "1,6", "3", "2", "3"));

      return database;

   }


}
