package edu.stevens.cs549.hadoop.pagerank;

import java.io.*;
import java.util.Iterator;

import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.io.*;

public class JoinReducer extends Reducer<TextPair, Text, Text, Text> {

	public void reduce(TextPair key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

		/* 
		 * TextPair ensures that we have values with tag "0" first, followed by tag "1"
		 * So we know that first value is the name and second value is the rank
		 */
		String k = key.toString(); // Converts the key to a String
		
		// TODO values should have the vertex name and the page rank (in that order).
		// Emit (vertex name, pagerank) or (vertex id, vertex name, pagerank)
		// Ignore if the values do not include both vertex name and page rank

		Iterator<Text> iterator = values.iterator();
		String nodeName = "";
		String rank  = "";
		while(iterator.hasNext()) {
			String tmp = iterator.next().toString();
			if(tmp.startsWith("NAME:")) {
				nodeName = tmp.replaceAll("NAME:", "");
			}
			if(tmp.startsWith("RANK:")) {
				rank = tmp.replaceAll("RANK:", "");
			}
		}

		context.write(new Text(k + "+" + nodeName) , new Text(rank));
	}
}
