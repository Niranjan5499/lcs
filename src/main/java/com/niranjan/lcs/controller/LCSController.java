package com.niranjan.lcs.controller;

import com.niranjan.lcs.exception.LcsException;
import com.niranjan.lcs.model.*;
import com.niranjan.lcs.service.LCSService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This is a Spring REST controller class that handles HTTP POST requests at the path /lcs.
 * It receives a StringSet object from the client's POST request, checks for various error
 * conditions (such as if the set of strings is empty or contains duplicate values), and
 * calls the LCSService to find the longest common substring(s). Depending on the result,
 * it returns an HTTP response with an appropriate status code and a JSON object containing
 * the longest common substring(s).
 */
@RestController
@RequestMapping("/lcs")
public class LCSController {

    @Autowired
    private LCSService lcsService;

    @PostMapping
    public ResponseEntity<LcsResponse> getLongestCommonSubstring(@RequestBody(required = false) LcsRequest lcsRequest)  {

        if(lcsRequest == null) {
            throw new LcsException("The format of the request was not acceptable");
        }
        Set<StringElement> setOfStrings = lcsRequest.getSetOfStrings();

        if(setOfStrings == null || setOfStrings.isEmpty()) {
            throw new LcsException("Set of strings should not be empty");
        }

        List<String> stringList = setOfStrings.stream().map(StringElement::getValue).collect(Collectors.toList());

        if(stringList.size() != new HashSet<>(stringList).size()) {
            throw new LcsException("Set of strings must be Unique set");
        }

        List<String> lcs = lcsService.longestCommonSubstring(stringList);
        List<StringElement> response = new ArrayList<>();
        for(String str : lcs) {
            StringElement stringElement = new StringElement();
            stringElement.setValue(str);
            response.add(stringElement);
        }

        LcsResponse res = new LcsResponse();
        res.setLcs(response);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @ExceptionHandler(LcsException.class)
    public ResponseEntity<LcsErrorMessage> errorMessageBody(LcsException lcsException){
        LcsErrorMessage lcsErrorMessage = new LcsErrorMessage();
        lcsErrorMessage.setMessage(lcsException.getMessage());
        lcsErrorMessage.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
        return new ResponseEntity<>(lcsErrorMessage,HttpStatus.BAD_REQUEST) ;
    }
}