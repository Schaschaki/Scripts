#Scripts

I revised the whole code and tried to match your directions.
Also I added some functionality and a lot of failsaves.

Idea: I could create a singular task to bring the character in a position from where the other tasks can operate, because currently I'm checking for every possible breaking situiation in every task and some of the problems overlap between Tasks, so that would probably be a good idea to slim down the code and make it more readable.

Known issues:

+ doesnt do anything when bank is not in viewport
+ ID arrays are not extended for level 7 enchants but only work up to level 4
+ the code is clustered and somewhat hard to read
+ Im not coding according to conventions (probably) and abusing enums in a way that is not intended.

Anyways, I tried to organize the code in a way that is logical and if you search for something you should be able to find it relatively quickly.
