Check versions of dependencies
---
    mvn versions:dependency-updates-report
    
Report in ./target/site/
    
    
Create Jar, javadoc and sources without deploy
---
    mvn clean package


Publish artifact
---
    export GPG_TTY=$(tty)
    mvn clean deploy
