// Do not submit with package statements if you are using eclipse.
// Only use what is provided in the standard libraries.

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class NaiveBayes {

	//declare hashmaps to track words and their individual probabilities
	private Map<String, Double> spamMap = new HashMap<>();
	private Map<String, Double> hamMap = new HashMap<>();
	
	//declare values to calculate the probability of a ham and spam email
	private double probSpam, probHam;
	
    /*
     * !! DO NOT CHANGE METHOD HEADER !!
     * If you change the method header here, our grading script won't
     * work and you will lose points!
     * 
     * Train your Naive Bayes Classifier based on the given training
     * ham and spam emails.
     *
     * Params:
     *      hams - email files labeled as 'ham'
     *      spams - email files labeled as 'spam'
     */
    public void train(File[] hams, File[] spams) throws IOException {
        // TODO: remove the exception and add your code here

    	//declare variables to track 'smoothed' probability per word
    	double spamWords, hamWords;
        
        //these call the method count that count the number of tokens
    	//in ham and spam emails, respectively and store in HashMaps
        spamMap = Count(spams);
        hamMap = Count(hams);
        
        //if a token is in the spam map, add it to ham and zero out the count
        for (String check : spamMap.keySet()) {
        	if (hamMap.containsKey(check)) {
            }
            else
            	hamMap.put(check, 0.0);
        }
        //if a token is in the ham map, add it to spam and zero out the count
        for (String check : hamMap.keySet()) {
            if (spamMap.containsKey(check)) {
            }
            else
            	spamMap.put(check, 0.0);
        }
        
        //Calculate 'smoothed' probability per word and add it to HashMap with the word
        //as the key for ham and spam
        for (String word : spamMap.keySet()) {
            spamWords = (double)(spamMap.get(word) + 1) / (double)(spams.length + 2);
            spamMap.put(word, spamWords);
        }
        for (String word : hamMap.keySet()) {
            hamWords = (double)(hamMap.get(word) + 1) / (double)(hams.length + 2);
            hamMap.put(word, hamWords);
        }
        
        //calculate the P(w|Spam) and P(w|Ham) of a ham and spam email
        probSpam = (double) (spams.length) / (double) (spams.length + hams.length);
        probHam = (double) (hams.length) / (double) (spams.length + hams.length);

    }
    
    /* Takes an array of emails (spam or ham emails) and then uses tokenset to
     * get an email and place all of the tokens into a HashSet. It then takes that
     * HashSet and puts the token as the Key and the Value of that key is the number
     * of emails from which it finds that token. So if three emails finds the word
     * 'Postage', then the Key = Postage and the value = 3. Once it goes through
     * all of the emails, it returns a HashMap
     * 
     * Params: an Array of Files
     * 
     * Returns: A HashMap with String as the key, and a double as the value
     *      
     */
    private Map<String,Double> Count(File[] files) throws IOException {
    	Map<String, Double> count = new HashMap<>();
    	HashSet<String> countTokens = null;
    	
    	for (int i = 0; i < files.length; i++) {
        	//takes token from an email and creates a set of tokens
            countTokens = tokenSet(files[i]);
            //for each token in tokens
            for (String token : countTokens) {
            	//if the map contains the key, increment
                if (count.containsKey(token)) {
                    count.put(token, count.get(token) + 1.0);
                } 
                //otherwise add it to map
                else
                    count.put(token, 1.0);
            }
        }
        
    	//return the HashMap
        return count;
    }
    
    /*
     * !! DO NOT CHANGE METHOD HEADER !!
     * If you change the method header here, our grading script won't
     * work and you will lose points!
     *
     * Classify the given unlabeled set of emails. Follow the format in
     * example_output.txt and output your result to stdout. Note the order
     * of the emails in the output does NOT matter.
     * 
     * Do NOT directly process the file paths, to get the names of the
     * email files, check out File's getName() function.
     *
     * Params:
     *      emails - unlabeled email files to be classified
     */
    public void classify(File[] files) throws IOException {
        // TODO: remove the exception and add your code here
    	
    	//declare variables to track logarithmic probability of an email
    	double logProbSpam = 0.0;
    	double logProbHam = 0.0;
    	
    	//run for the length of the email
    	for (int i = 0; i < files.length; i++) {
    		//takes token from an email and creates a set of tokens
            Set<String> tokens = tokenSet(files[i]);
            //calculate 
        	logProbSpam = Math.log(probSpam);
        	logProbHam = Math.log(probHam);
            
            //for each token in tokens
            for (String token : tokens) {
            	//if the token exists in both maps, then add log of probability
            	//this calculates the P(Spam|x1, x2, ...xn) and P(Ham|x1, x2, ...xn)
            	if (spamMap.containsKey(token) && hamMap.containsKey(token)) {
                    logProbSpam += Math.log(spamMap.get(token));
                    logProbHam += Math.log(hamMap.get(token));
                }
            }
           
            //outputs results based on final probability
            System.out.print(files[i].getName());
            
            if(logProbSpam > logProbHam) {
        		System.out.println(" spam");
        	}
        	else
        		System.out.println(" ham");
    	}
    }  

    /*
     *  Helper Function:
     *  This function reads in a file and returns a set of all the tokens. 
     *  It ignores "Subject:" in the subject line.
     *  
     *  If the email had the following content:
     *  
     *  Subject: Get rid of your student loans
     *  Hi there ,
     *  If you work for us , we will give you money
     *  to repay your student loans . You will be 
     *  debt free !
     *  FakePerson_22393
     *  
     *  This function would return to you
     *  ['be', 'student', 'for', 'your', 'rid', 'we', 'of', 'free', 'you', 
     *   'us', 'Hi', 'give', '!', 'repay', 'will', 'loans', 'work', 
     *   'FakePerson_22393', ',', '.', 'money', 'Get', 'there', 'to', 'If', 
     *   'debt', 'You']
     */
    public static HashSet<String> tokenSet(File filename) throws IOException {
        HashSet<String> tokens = new HashSet<String>();
        Scanner filescan = new Scanner(filename);
        filescan.next(); // Ignoring "Subject"
        while(filescan.hasNextLine() && filescan.hasNext()) {
            tokens.add(filescan.next());
        }
        filescan.close();
        return tokens;
    }
}
