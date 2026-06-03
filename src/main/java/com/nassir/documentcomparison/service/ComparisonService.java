package com.nassir.documentcomparison.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.springframework.stereotype.Service;
import com.nassir.documentcomparison.domain.ComparisonResult;

@Service
public class ComparisonService {
	
	static Set<String> stopWords = Set.of("the", "a", "an", "is", "was", "by", "in", "on", "at", "to", "of", "and", "or", "it", "this", "that");

	public ComparisonResult compare(String file1, String file2) {
		var freqMap1 = getMapOfWordFrequencies(file1);
		var freqMap2 = getMapOfWordFrequencies(file2);
		var dotProduct = dotProduct(freqMap1, freqMap2);
		var magnitude1 = magnitude(freqMap1);
		var magnitude2 = magnitude(freqMap2);
		var cosineSimilarity = cosineSimilarity(dotProduct, magnitude1, magnitude2);
		return new ComparisonResult(cosineSimilarity);
	}
	
	
	private static HashMap<String, Integer> getMapOfWordFrequencies(String text) {
		String[] arr = text.split(" ");
		var map = new HashMap<String, Integer>();
		for (String s : arr) {
			if(stopWords.contains(s)) continue;
			map.put(s, map.getOrDefault(s,0)+1);
		}
		return map;
	}
	
	private static HashMap<String, Integer> getMapOfWordFrequenciesUsingStream(String text) {
		String[] arr = text.split(" ");
		var map = new HashMap<String, Integer>();
		Arrays.stream(arr).forEach((s)-> {
			map.put(s, map.getOrDefault(s,0)+1);
		});
		
		return map;
		
	}
	
	private static double dotProduct(HashMap<String, Integer> map1, HashMap<String, Integer> map2) {
		double total = 0;
		for (String key : map1.keySet()) {
		    // key is each word
			if(map2.containsKey(key)) {
				total = total + map1.get(key) * map2.get(key);
			}
		}
		return total;
	}
	
	private static double magnitude(HashMap<String, Integer> map) {
		double magnitude = 0;
		for (String key : map.keySet()) {
			magnitude = magnitude + Math.pow(map.get(key), 2);
		}
		magnitude = Math.sqrt(magnitude);
		return magnitude;
	}
	
	private static double cosineSimilarity(double dotProduct, double magnitude1, double magnitude2) {
		return dotProduct / (magnitude1 * magnitude2);
	}

}
