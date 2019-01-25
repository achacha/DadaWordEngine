DaDa Word Engine
===

What is it?
---
Generic word parsing, construction and processing tool for generating sentences based on word types.
You can generate random sentences based on defined structure by parsing or constructing a sentence based
on word types and generating random output using the parsed structure. 


Where is it?
---
**Source maintained at:** https://github.com/AlexChachanashviliOss/DadaWordEngine

**Maven Repository:** https://mvnrepository.com/artifact/io.github.achacha/DadaWordEngine

Releases at Maven Repository:

    <groupId>io.github.achacha</groupId>
    <artifactId>DadaWordEngine</artifactId>
    <version>[latest version]</version>


Features
---
- Support for multiple word sets (WordData)
- Parsing will detect word and its possible type (Sentence) 
- Word hyphenation (HyphenData)
- Transformation to phonetic form useful for search and match based on what it sounds like (PhoneticTransformer)
- Integrated as custom JSP tags for easier dynamic content generation 


Examples
---
See /src/main/java/org/achacha/dada/examples

Running examples:

    mvn exec:java -Dexec.mainClass="io.github.achacha.dada.examples.PhonemixExample" 


JSP integration
---
1. Copy TLD file from  {this jar}/WEB-INF/tlds to {your webapp resources}/WEB-INF/tlds
2. Initialize TagSingleton with WordData and HypenData during container/context initialization
    - TagSingleton.setWordData(new WordData("resource:/data/extended2018"));
    - TagSingleton.setHypenData(new HyphenData("resource:data/hyphen"));
3. Add tags to your JSP page (see provided examples in {this jar}/WEB-INF/*.jsp)


Word Data
---
1. All data is converted to lower case
2. Leader # is used as a comment
3. Whitespace is trimmed front and back
 