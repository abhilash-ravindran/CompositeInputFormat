package com.sudha.hadoop.examples;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.KeyValueTextInputFormat;
import org.apache.hadoop.mapred.RunningJob;
import org.apache.hadoop.mapred.TextOutputFormat;
import org.apache.hadoop.mapred.join.CompositeInputFormat;

public class MapSideJoinJob {
	
	public static void main(String[] args) throws Exception {
		
		JobConf conf = new JobConf("MapSideJoinJob");
		conf.setJarByClass(MapSideJoinJob.class);
		
		conf.setMapperClass(MapSideJoinMap.class);
		conf.setInputFormat(CompositeInputFormat.class);
		String strJoinStmt = CompositeInputFormat.compose("inner", KeyValueTextInputFormat.class, new Path(args[0]), new Path(args[1]));
		conf.set("mapred.join.expr", strJoinStmt);
		
		conf.setNumReduceTasks(0);
		
		conf.setOutputFormat(TextOutputFormat.class);
		TextOutputFormat.setOutputPath(conf, new Path(args[2]));
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);
		
		RunningJob job = JobClient.runJob(conf);
		while (!job.isComplete()) {
			Thread.sleep(1000);
		}
		System.exit(job.isSuccessful() ? 0 : 2);
	}
}	
