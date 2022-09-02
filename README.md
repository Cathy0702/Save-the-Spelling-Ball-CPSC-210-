# Save the Spelling Ball

## A Spelling Game

### What will the application do?

This application is a **spelling game**. It allows user to input words everytime. Then, it will display the words
with some letters covered, and let the user enters letters. 
- If the spelling is correct, a ball will stay right 
at its original position on the table, and waits for the next letters to be entered. 
- If the spelling is incorrect, the ball will move one unit to the edge of
the table until it falls and ends the game.

### Who will use it?

In fact, everyone can use this application, but I designed it specifically to help *students* and *language learners* 
learn and memorize words better.

### Why is this project of interest to me?

- In terms of **software construction and coding**, I think this project has applied the knowledge I have learned 
in many aspects. It involves *building different classes*, *using different data abstraction*, *writing different 
methods* and their *tests* to implement functions. In the future, it will also involve *graphing* and *user 
interface design*. This is a challenge for me, but also a test of my comprehensive ability.

- In terms of **content**, as a language learner, learning words is boring most of the time, 
and this boring accompanies me almost the whole learning process. Therefore, I hope that this simple 
application will help more language learners and students to make their experience of learning words more 
*efficient* and *fun*.

### User Stories

- As a user, I want to be able to input words and add them to my wordlist
- As a user, I want to be able to see the words in my wordlist with random letters covered
- As a user, I want to be able to enter letters in the covered position
- As a user, I want to be able to see and play with next covered word if my entry is correct
- As a user, I want to be able to see the ball moves toward the edge of the table if my entry is incorrect
- As a user, I want to be able to end the game when the ball falls from the edge
- As a user, I want to be able to save my current game status during the game time
- As a user, I want to be able to choose whether to start a new game or load my previous status
- As a user, I want to be able to continue with the previous game status if I choose to load game

### Phase 4: Task 2

Fri Nov 26 14:36:27 PST 2021<br />
A new empty word-list has been constructed.


Fri Nov 26 14:36:29 PST 2021<br />
A new word: monday is constructed.


Fri Nov 26 14:36:29 PST 2021<br />
Word: monday is add to word-list.


Fri Nov 26 14:36:31 PST 2021<br />
A new word: tuesday is constructed.


Fri Nov 26 14:36:31 PST 2021<br />
Word: tuesday is add to word-list.


Fri Nov 26 14:36:32 PST 2021<br />
A new word: friday is constructed.


Fri Nov 26 14:36:32 PST 2021<br />
Word: friday is add to word-list.


Fri Nov 26 14:36:33 PST 2021<br />
Word-list is randomized.


Fri Nov 26 14:36:33 PST 2021<br />
A new word: ?o???y is constructed.


Fri Nov 26 14:36:33 PST 2021<br />
Word: monday is composed to become a question word.


Fri Nov 26 14:36:56 PST 2021<br />
A new empty word-list has been constructed.


Fri Nov 26 14:36:56 PST 2021<br />
Word-list is reset.


Fri Nov 26 14:36:56 PST 2021<br />
Word-list is randomized.


Fri Nov 26 14:36:56 PST 2021<br />
A new word: ??id?y is constructed.


Fri Nov 26 14:36:56 PST 2021<br />
Word: friday is composed to become a question word.


Fri Nov 26 14:37:16 PST 2021<br />
A new empty word-list has been constructed.


Fri Nov 26 14:37:16 PST 2021<br />
Word-list is reset.


Fri Nov 26 14:37:16 PST 2021<br />
Word-list is randomized.


Fri Nov 26 14:37:16 PST 2021<br />
A new word: ?ue??ay is constructed.


Fri Nov 26 14:37:16 PST 2021<br />
Word: tuesday is composed to become a question word.


Fri Nov 26 14:37:17 PST 2021<br />
A new word: ?ue??ay is constructed.


Fri Nov 26 14:37:17 PST 2021<br />
A new empty word-list has been constructed.


Fri Nov 26 14:37:17 PST 2021<br />
Word-list is reset.


Fri Nov 26 14:37:17 PST 2021<br />
Current question word is saved.


Fri Nov 26 14:37:17 PST 2021<br />
Current word-list status is saved.


### Phase 4: Task 3

- When I was designing this program, I decide that "comparing an answer letter (and a
position) with the correct word and determining right or wrong" is a behavior that should
be performed during the actual processing, meaning in ui package. However, it becomes hard
to record and log that event when the program ends. Therefore, I might try to refactor that
behavior as a method in Word class or ListOfWords class if I have more time. I believe it 
follows the cohesion principle, and also makes that behavior more recordable.
- When coding GUI, I extract GameState class, which contains all methods for processing from 
SpellingApp class (my console base UI). This results a plenty of same code between 
GameState class and SpellingApp class, which can be redundant. Since both GUI and console-base
UI use similar code, the only difference is the rendering way, I might be able to make 
GameState an abstract class, containing all methods relating to the game processing. It allows
both GUI and console-base UI extends GameState, making the code not only less redundant, but 
also more readable.
