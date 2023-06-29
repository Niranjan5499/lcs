package com.niranjan.lcs.service;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This service class contains the core algorithm to find the longest common substring from a list of strings.
 * The method longestCommonSubstring iterates through the provided list of strings, checks each possible substring
 * of the first string against all the other strings in the list, and keeps track of the longest common substring(s).
 */
@Service
public class LCSService {
    public List<String> longestCommonSubstring(List<String> strList) {
        int n = strList.size();
        String s = strList.get(0);
        int len = s.length();

        List<String> response = new ArrayList<>();

        for (int i = 0; i <= len; i++) {
            for (int j = i + 1; j <= len; j++) {

                String stem = s.substring(i, j);
                int k = 1;
                for (k = 1; k < n; k++)
                    if (!strList.get(k).contains(stem))
                        break;

                if (k == n && (response.size() == 0 || response.get(0).length() < stem.length())) {
                    response = new ArrayList<>();
                    response.add(stem);
                } else if (k == n && response.get(0).length() == stem.length() && !response.contains(stem)) {
                    response.add(stem);
                }
            }
        }
        Collections.sort(response);
        return response;
    }
}
