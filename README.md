# TOPT-Enryption
TOPT based text encryption
TOPT or time based one time password like the one that Google authenticator is based on can be further used for encryption purposes. We can use it's as key to Cypher a text message.

This project demonstrates a simple encrypted message transmission using the AES 128bit time based key derived from server and client's deivce Ips or Mac addr

Every 30 second based on current time window key can be dynamically changed without need interchange key information between the two devices with some seconds of clock error roundoff
