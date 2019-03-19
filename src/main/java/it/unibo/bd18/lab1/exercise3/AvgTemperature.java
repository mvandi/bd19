package it.unibo.bd18.lab1.exercise3;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;

import java.io.IOException;

public class AvgTemperature {
    public static void main(String[] args) throws IOException {
        JobConf conf = new JobConf(AvgTemperature.class);
        conf.setJobName("Avg temperature");

        Path inputPath = new Path(args[0]),
                outputPath = new Path(args[1]);
        FileSystem fs = FileSystem.get(new Configuration());

        if (fs.exists(outputPath)) {
            fs.delete(outputPath, true);
        }

        FileInputFormat.addInputPath(conf, inputPath);
        FileOutputFormat.setOutputPath(conf, outputPath);

        conf.setMapperClass(AvgTemperatureMapper.class);
        conf.setReducerClass(AvgTemperatureReducer.class);

        if (args.length > 2 && Integer.parseInt(args[2]) >= 0) {
            conf.setNumReduceTasks(Integer.parseInt(args[2]));
        } else {
            conf.setNumReduceTasks(1);
        }

        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(DoubleWritable.class);

        JobClient.runJob(conf);
    }
}
