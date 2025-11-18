# Internship Placement Management System (IPMS)

SC2002 — Object-Oriented Design & Programming  
AY2024/2025 Semester 1


---

## Project Structure

root/
├─ src/
│  ├─ boundary/
│  ├─ controller/
│  ├─ entity/
│  ├─ util/
│  └─ Main.java
├─ data/
│  ├─ student.csv
│  ├─ rep.csv
│  ├─ internship.csv
│  └─ *.dat (generated at runtime)
├─ html/          (generated Javadoc)
├─ report.pdf     (final SC2002 report)
└─ README.md


---

## How to Compile

Navigate to the src folder:

    cd src

Compile all .java files:

    javac -d ../out $(find . -name "*.java")

Alternatively, compile using IntelliJ by marking src/ as the Source Root.

---

## How to Run

Navigate into the out folder and run:

    java Main

The system will launch starting from the Welcome Page.

Remember that system.dat file will be generated after your initial run.
DO NOT DELETE THE FILE, YOU WILL LOSE YOUR PROGRESS

---

## Javadoc Documentation

The generated Javadoc is located in:

    html/index.html

Open index.html in any browser to view the API documentation.

---

## External Files

The system loads initial data from:

- data/student.csv
- data/rep.csv

The system also creates .dat files during runtime to store persistent state.

---


## Team Members

Mega     
Soren  
Cristian  
Cheng kiat  
Sean  
Course: SC2002

---

## Notes

- report.pdf contains required UML diagrams
- Detailed UML diagrams are located folder /UML/
- Source code is not included inside the report
- All implementation files are located in /src/
- Javadoc output is fully generated inside /html/
