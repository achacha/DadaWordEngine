JSP Tags supported (also in /help.jsp)
-----
noun
pronoun
adjective
verb
adverb
conjunction
preposition


Importing
---
<%@ taglib prefix="d" uri="/taglib/dada" %>


Usage
---
<d:noun />



Tag Specifics
===
**ALL WORDS**
* additional forms
    * article - prepend an article 
        * a - Prepend a or an depending on grammar rules
        * the - Prepend the
    * capMode - capitalization mode
        * first - first letter capitalized, if artocle added that will be capitalized only
        * words - first letter of every word
        * all - all letters capitalized
    * save - store current word in a tag
    * load - load stored word
    * rhyme - try to find a word that rhymes with a saved word


**noun**
* additional forms
    * plural - add suffix to make the noun plural based on grammar rules
 
**pronoun**
* additional forms
    * personal - I, you, he, she, it, we, they, me, him, her, us, them 
    * subjective - I, you, he, she, it, we, they, who, what
    * objective - you, it, me, him, her, us, them, whom
    * possessive - mine, yours, his, hers, ours, theirs, this, that, these, those
    * demonstrative - this, that, these, those
    * interrogative - who, what, whom, which, whose, whoever, whichever, whomever
    * relative - who, what, whom, that, which, whose, whoever, whichever, whomever
    * reflexive - myself, wourself, himself, herself, itself, ourselves, themselves
    * reciprocal - each other, one another
    * indefinite - anything, everybody, another, each, few, many, some, all, any, etc...
  
**adjective**
* additional forms
    * er (comparative) - bigger, better, more
    * est (superlative) - biggest, best, most
 
**verb**
* additional forms
    * past - sat, ate, drank, ran
    * pastParticiple (has/had) - sat, ate, drunk, ran
    * singular - sits, eats, drinks, runs
    * present - sitting, eating, drinking, running
    * infinitive - to sit, to eat, to drink, to run
     
**conjunction**

**preposition**

Examples
------
<d:noun /> - lower case noun  *e.g. car*
<d:noun article="the" /> - prepended article *e.g. the car*
<d:noun article="the" capMode="first"/> - prepended article *e.g. The car*
<d:noun article="the" capMode="words"/> - prepended article *e.g. The Car*
<d:noun article="the" capMode="words" form="plural"/> - prepended article *e.g. The Cars*

<d:noun save="subject_of_poem"/> - Save generated word with given label
<d:noun load="subject_of_poem"/> - Load a previously saved word or generate a random noun if not yet saved
<d:noun rhyme="subject_of_poem"/> - Look for a noun that rhymes with saved word