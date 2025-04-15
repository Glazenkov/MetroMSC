# MetroMSC

MetroMSC is a Java-based data collection and processing application that gathers information from various sources and generates structured JSON output files.

## Project Overview

The application is designed to collect and process data from multiple sources including:
- Web pages
- CSV files
- JSON files

The processed data is then organized and output into two separate JSON files for further use.

## Features

- Web page parsing and data extraction
- CSV file processing
- JSON file parsing
- Automated file searching in directories
- Structured JSON output generation

## Technical Details

- **Language**: Java 17
- **Build Tool**: Maven
- **Dependencies**:
  - Lombok (for reducing boilerplate code)
  - Jackson (for JSON processing)
  - Jsoup (for HTML parsing)
  - org.json (for JSON handling)

## Project Structure

```
MetroMSC/
├── DataCollector/
│   ├── core/
│   ├── WebPageParsing.java
│   ├── SearchFilesInFolders.java
│   ├── ParsingJSONFiles.java
│   ├── Main.java
│   ├── ParsingCSVFiles.java
│   └── CreatingJSONFiles.java
├── pom.xml
└── README.md
```

## Getting Started

1. Ensure you have Java 17 and Maven installed
2. Clone the repository
3. Build the project using Maven:
   ```bash
   mvn clean install
   ```
4. Run the application:
   ```bash
   java -jar target/data_collector-1.0-SNAPSHOT.jar
   ```

## License

This project is licensed under the terms specified in the project's license file.