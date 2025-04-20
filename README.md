# TOPT-Enryption
Note: I thought of this project after watching  avideo about google authenticator and how it works:

I have very less prior knowledge that can be anyhow related to crpytography

This project and code written is completely just a proof-of-concept that i wouold really like to see it be useful in data transmission over network as in real life if it work as secure as I originally had the idea of.

This cryptography is far from being secure as it was deemed to be but I will still continue wokring on it, getting pass some challenges and work around making it more secure.

Very idea of using the initial key as being derived form public IP is worst idea, it can be easily bruteforced and key can be derived easiy even though key has SHA-256 hashed and truncated, ot reconstruct the exact key for any 30second window would be easier then one can imagine. infact I tried it and did it to test it. So a better idea has to be implemented.
Using preshareed secret key as in standard practice would a straight forward troubleshoot to this problem but I had teh idea of 0 shared key maybe for identity less and ephemeral comms

The biggest challenge in this project probably is the concept of no key being shared from the start. Mutuality of key creation without device communication and complete being based on or relying on clock of 2 different hardware devices is difficult as client and sever clocks drift happen inevitably. It can surely resync by using NTP style re-sync to handle it which i will check on it later 

Will integerate key-window fallback on decryption fail

TOPT based text encryption
TOPT or time based one time password like the one that Google authenticator is based on can be further used for encryption purposes. We can use it's as key to Cypher a text message.

Every 30 second based on current time window key can be dynamically changed without need interchange key information between the two devices with some seconds of clock error roundoff

in conclusion we can count this project as a just a dream of mine, a creative spark yet a learning milestone but I really want to make this project a successful one 
