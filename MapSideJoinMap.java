package com.sudha.hadoop.examples;

import java.io.IOException;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.join.TupleWritable;

public class MapSideJoinMap extends MapReduceBase implements Mapper<Text, TupleWritable, Text, Text> {
	Text txtValue = new Text("");
	
	@Override
	public void map(Text key, TupleWritable value,OutputCollector<Text, Text> output, Reporter reporter)
	throws IOException {
		if (value.toString().length() > 0) {
			
			String emp = value.get(0).toString();
			String data = value.get(1).toString();
			
			txtValue.set(emp + "\t" + data);
			output.collect(key, txtValue);
		}
	}
}
