#Naive-Bayes Classifier for Spam Filtering

Consider the following problem involving Bayes’ Theorem:
40% of all emails are spam. 10% of spam emails contain the word “viagra”, while only 0.5% of nonspam
emails contain the word “viagra”. What is the probability that an email is spam, given that it contains the
word “viagra”?

Let S be the event that a given email is spam, and let V be the event that the email contains the word
“viagra”. We would like to know P(S|V ), the probability that an email is spam given that it contains
“viagra”. An application of Bayes’ Theorem gives the answer:

P(S | V ) = P(V | S)P(S)
P(V | S)P(S) + P(V | S)P(S)
= 0.1 × 0.4
= 0.1 × 0.4 + 0.005 × 0.6
≈ 0.93

It’s easy enough to derive this sort of probability when you’re only examining one word. We would like to
extend this sort of thinking to classify emails as either spam or nonspam (also known as “ham”), by
examining every word in the email, building a very general, powerful spam filter along the way.

surapan1/Naive-Bayes/data - This is to classify spam and to use the spam filter on
surapan1/Naive-Bayes/imgs - You can ignore this folder
surapan1/Naive-Bayes/NaiveBayes.class - compiled java class
surapan1/Naive-Bayes/NaiveBayes.java - Naive-Bayes algorithm
surapan1/Naive-Bayes/SpamFilterMain.class - compiled java class
surapan1/Naive-Bayes/SpamFilterMain.java - Spam filter implementation to run the NB algorithm
surapan1/Naive-Bayes/example_output.txt - this is what the program outputs when run on several files to check whether they are spam or not
