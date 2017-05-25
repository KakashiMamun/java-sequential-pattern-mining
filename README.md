# java-sequential-pattern-mining
Package provides java implementation of sequential pattern mining algorithm GSP

[![Build Status](https://travis-ci.org/chen0040/java-sequential-pattern-mining.svg?branch=master)](https://travis-ci.org/chen0040/java-sequential-pattern-mining) [![Coverage Status](https://coveralls.io/repos/github/chen0040/java-sequential-pattern-mining/badge.svg?branch=master)](https://coveralls.io/github/chen0040/java-sequential-pattern-mining?branch=master) 


# Overview of GSP
The implementation of the algorithm is based on Srikant & Agrawal, 1996

The algorithm makes multiple passes over the data. The first pass determines the support of each item, that
is, the number of data-sequences that include the item. At the end of the first pas, the algorithm knows
which items are frequent, that is, have minimum support. Each such item yields a 1-element frequent sequence
consisting of that item.

Each subsequent pass starts with a seed set: the frequent sequences found in the previous pass. The seed set
is used to generate new potentially frequent sequences, called candidate sequences. Each candidate sequence
has one more item than a seed sequence; so all the candidate sequences in a pass will have the same number of
items. The support for these candidate sequences is found during the pass over the data. At the end of the
pass, the algorithm determines which of the candidate sequences are actually frequent. These frequent candidates
become the seed for the next pass.

# Install

Add the following dependency to your POM file:

```xml
<dependency>
  <groupId>com.github.chen0040</groupId>
  <artifactId>java-sequential-pattern-mining</artifactId>
  <version>1.0.1</version>
</dependency>
```

# Usage

The sample code belows illustrates how to use the GSP to find the frequent sequential pattern in a simple sequence database.

```java
List<Sequence> database = new ArrayList<>();

// Below is 4 sequences of transactions stored in the database 
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

GSP method = new GSP();
method.setMinSupportLevel(2);
List<String> uniqueItems = new MetaData(database).getUniqueItems();
Sequences result = method.minePatterns(database, uniqueItems, -1);

result.getSequences().stream().forEach(sequence -> {
 System.out.println("sequence: " + sequence);
});
```

