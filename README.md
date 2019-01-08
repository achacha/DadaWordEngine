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

`mvn exec:java -Dexec.mainClass="io.github.achacha.dada.examples.PhonemixExample"` 

Available examples:

- io.github.achacha.dada.examples.GenerateRandomSentenceExample
- io.github.achacha.dada.examples.ParseAndRegenerateSentenceExample
- io.github.achacha.dada.examples.HyphenateExample
- io.github.achacha.dada.examples.PhonemixExample
- io.github.achacha.dada.examples.FindWordsThatRhymeExample


JSP integration
---
1. Copy TLD file from  /src/main/java/org/achacha/dada/integration/tags/tlds to WEB-INF/tlds
2. Initialize TagSingleton with WordData and HypenData during container/context initialization
    - TagSingleton.setWordData(new WordData("resource:/data/extended2018"));
    - TagSingleton.setHypenData(new HyphenData("resource:data/hyphen"));
3. Add tags to JSP page

