1. IDEA, creazione di un nuovo progetto che usa Vaadin col plugin Vaad

2. File -> New -> Project...

3. Selezionare Java Enterprise, a sinistra (frame di sinistra, la seconda riga)

4. Controllare i valori (potrebbero differire):
    . Project SDK           = 1.8 (java version 1.8.0_25)
    . Java EE Version       = Java EE 7
    . Application Server    = Tomcat 8.0.15

5. Selezionare Web Application, a destra in Additional Libraries and Frameworks
    - crea l'artifacts xxx:war exploded di tipo Web Application:Exploded necessario per visualizzare il War nel Server

6. Selezionare JavaEE Persistence, a destra in Additional Libraries and Frameworks
    - aggiunge la JPA come Modules e come Facets

7. Selezionare Set up library later, sotto

8. Confermare -> Next

9. Controllare il nome del progetto (minuscolo) e la directory d'installazione. Confermare -> Finish

10. Controllare che il progetto (minimale) compili.
    - è stata creata la directory META-INF nel percorso dei sorgenti
    - è stata creato il file persistence.xml in src/Meta-INF/persistence.xml (verrà regolato successivamente)
    - è stato creato l'artifacts necessario
    - è stata creata una configurazione col server Tomcat;
    - senza altri interventi, selezionando Run il programma compila senza errori ed apre il browser (bianco, vuoto)

11. Controllare che il progetto (minimale) funzioni.
    - modificare il file web.index.jsp inserendo del testo nei tag <body> e </body>
    - selezionando Run il programma apre il browser con la scritta inserita

12. In Project Settings -> Libraries
    - aggiungere New Project Library (tipo java), selezionando ~/Documents/IdeaProjects/webbase/out/artifacts/webbase_jar
    - se si vuole congelare lo sviluppo di webbase od usare una versione sicura, basta inserire qui la versione desiderata di webbase
    - selezionando la CARTELLA, a destra appariranno due path: uno per i Classes ed uno per i Sources
    - se in Project Setting appare in basso a sinistra la scritte Problems, cliccare su Fix e selezionare Add webbase_jar to the artifact

13. Aprire il progetto webbase e lanciare (in Ant) lo script templates.script.pack:
    - nel primo dialogo, inserire (obbligatorio) il nome (Maiuscolo) del nuovo progetto
    - nel secondo dialogo, inserire (facoltativo, default 'MySqlUnit') il parametro di collegamento persistence-entity
    - nel terzo dialogo, inserire (facoltativo, default 'test') il nome del database mysql

14. È stato creato il package minimo:
    - crea la directory base del progetto -> it.algos.nomeProgetto
    - crea la classe xxxBootStrap Prima classe in esecuzione del programma.
    - crea la classe xxxApp Repository di costanti generali del programma
    - crea la classe xxxServlet Punto di partenza della sessione nel server.
    - crea la classe xxxUI Punto di partenza quando si accede dal browser
    - sostituisce il file persistence.xml Parametri di regolazione del database. Elenco di Entity
    - sostituisce il file web.WEB-INF.web.xml
