# csv2bib
In some digital libraries, for instance SpringerLink, the search results are available only in .csv format, but the researchers needs in other formats like bibtex. In this sense, this project was created to help the researchers converting csv files into a bib or ris file.

[![Gittip](https://img.shields.io/badge/Latest%20stable-2.0-green.svg?style=flat-squared)]()
[![Gittip](https://img.shields.io/badge/build-passing-brightgreen.svg)]()

# Requirements

- Java 1.8 or above to run
- Maven >= 3.3.9

# How do I start?

1. Dowload the last release;
2. Make the command:

## Running with args in command line
```
java -jar csv2bib-version -fi=src/test/resources/SpringerLink.csv -dl=SpringerLink -doiIndex=5
```

## Running with properties parameters

1. Access and edit the config.properties file
2. Make the command

```
java -jar csv2bib-version
```


where you must change *version* with the a version available
 
 # Tips
## About the parameters

**-help** or *-h*: Display the commands available.

**-fileIn** or *-fi*: The path to the input file. (required)

**-dl**: Digital Library that the system will get the bibtex informations. (required)

**-doiIndex**: DOI index in the csv file. Starting with 0 (first column). (required)

**-header**: If the csv contains a header. (optional, default 1)

## Digital Libraries available
- DOI (Get the bibtex informations using crossref - this option is not so recommended)
- SpringerLink
