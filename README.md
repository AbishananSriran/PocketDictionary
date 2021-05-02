[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
# A Simple Pocket Dictionary

### *What is this about?* 
   This is a simple pocket dictionary that I created while working with Java. Having worked with APIs in other programming languages, I wanted to make a dictionary application for a long time. This program was created using NetBeans IDE and Java Swing (javax.swing) + GUI Builder.
 
![Pocket Dictionary](https://user-images.githubusercontent.com/83186961/116798355-87920780-aabc-11eb-9967-c42b6261eb22.png)
 
### *Program Features*
* Uses an [open dictionary API](https://dictionaryapi.dev) to provide quick and accurate results for search queries.
* Formats and beautifies responses to make results clearer and structured.
* Seperates results by Part of Speech.
* Provides synonyms and examples of word usage where applicable.
* Caches previously searched results to avoid unnecessary HTTP GET requests.

### *Installation*
1. Download the source in the `master` branch by going to Code -> Download ZIP or use git command `git clone https://github.com/AbishananSriran/PocketDictionary` to fetch the most up-to-date source. 
2. Download and integrate the [org.json JAR file](https://mvnrepository.com/artifact/org.json/json) into the program's dependencies.
3. Run the program by compiling and running the `Dictionary.java` file. (`javac Dictionary.java` + `java Dictionary`) 
.
### *Usage*
1.  Search term to define in the text field at top of the program.
2.  Press "Search Term" button. Search may take up to 5 seconds to provide a response (if any).
3.  View the results in the text area below and enjoy!

### *TO-DO*
* Work on more efficient methods to store data (using readable files or SQL)
* Work on formatting quotes to avoid odd search results. (Example: "hello" results in: â€œhelloâ€�)
* Optimize methods and logic in code.
* Spice up the application UI!
