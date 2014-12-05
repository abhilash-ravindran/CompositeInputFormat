CompositeInputFormat
====================

Understanding Data Joins in MapReduce

Abstract:
	This project demonstrates how to implement Map side join using CompositeInputFomat. Why take the time to learn how to join data instead, when it is better accomplished using tools that work at higher level of abstraction such as Hive or Pig?? Joining data is arguably one of the biggest uses of Hadoop. Gaining a full understanding of how Hadoop performs joins is critical for deciding which join to use and for debugging when trouble strikes. Also, once you fully understand how different joins are performed in Hadoop, you can better leverage tools like Hive and Pig. 
	
Prerequites for using CompositeInputFormat :
1. All the files you want to join are sorted
2. All of them have the same joining key
3. The files are too big to join using DistributedCache

If you have n files sorted by their join key, you can combine them easily, reading the records one by one from each files so that you are always reading the records with same key.

This is how CompositeInputFormat works. It will read your files and deliver a TupleWritable object  to the mapper.

So, MapInputKey – Text MapInputValue - TupleWritable

To configure a job beside setting the CompositeInputFormat class as input format, you have to specify the join expression. You can specify outer or inner.

conf.setInputFormat(CompositeInputFormat.class);

String StrJoinStmt = CompositeInputFormat.compose("inner", KeyValueTextInputFormat.class, new Path(args[0]), new Path(args[1]));

conf.set(“mapred.join.expr”, StrJoinStmt);

You also have to specify what is actually the format of files you're reading. That's why we see KeyValueTextInputFormat.class in the above code.

CompositeInputFormat will delegate picking the key to that class. In this example, it will take the first column in a tab-separated file, as it is default behaviour for KeyValueTextInputFormat.

Don't forget to include appropriate classes for output format and key, value pair

conf.setOutputFormat(TextOutputFormat.class); 

TextOutputFormat.setOutputPath(conf, new Path(args[2])); 

conf.setOutputKeyClass(Text.class); 

conf.setOutputValueClass(Text.class);
