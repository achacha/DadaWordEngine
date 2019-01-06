DaDa Word Engine
===

What is it?
---
Generic word parsing, construction and processing tool for generating sentences based on word types.
You can generate random sentences based on defined structure by parsing or constructing a sentence based
on word types and generating random output using the parsed structure. 


Features
---
- Support for multiple word sets (WordData)
- Parsing will detect word and its possible type (Sentence) 
- Word hyphenation (HyphenData)
- Transformation to phonetic form useful for search and match based on what it sounds like (PhoneticTransformer)


Examples
---
See /src/main/java/org/achacha/dada/examples

Running examples:

`mvn exec:java -Dexec.mainClass="org.achacha.dada.examples.PhonemixExample"` 

Available examples:

- org.achacha.dada.examples.GenerateRandomSentenceExample
- org.achacha.dada.examples.HyphenateExample
- org.achacha.dada.examples.ParseAndRegenerateSentenceExample
- org.achacha.dada.examples.PhonemixExample

