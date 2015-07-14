Creazione di un nuovo progetto Vaadin con IDEA

1. File -> New -> Project...
2. Selezionare Java Enterprise a sinistra (la seconda opzione)
3. Controllare i valori (potrebbero differire):
    . Project SDK           = 1.8 (java version 1.8.0_25)
    . Java EE Version       = Java EE 7
    . Application Server    = Tomcat 8.0.15

4. Abilitare Vaadin
5. Controllare i valori (potrebbero differire):
    . Version               = Vaadin 7
    . Vaadin Distribution   = /usr/share/vaadin-all-7.5.0
6a. Spuntare Create Sample Application, se NON si vuole usare il plugin (AddOn) WebBase
    . Confermare il nome dell'applicazione base o cambiarlo
6b. NON spuntare Create Sample Application, se si vuole usare il plugin (AddOn) WebBase
7. Confermare -> Next
8. Controllare il nome del progetto e la directory d'installazione
9. Confermare -> Finish


10a. Progetto funzionante.
    - Il Wizard ha creato una configurazione col server Tomcat;
    - ha creato un Artifacts;
    - ha inserito l'artifacts nel Deployment del server;
    - ha creato la classe MyVaadinApplication che è la Main Class
    - ha creato il file WEB-INF -> web.xml, dove è indicata la classe di partenza del servlet
    - senza necessità di ulteriori interventi, selezionando Run il programma funziona


10b. Progetto da regolare per utilizzare WebBase
11. Creazione di un file ivy.xml (cartella base)
12. Creazione di un file ivysettings.xml (cartella base)
12. Creazione di un file persistence.xml (cartella base)
13. Configurazione del file persistence come JPA facet descriptor (suggerito in automatico)
14. Creazione in src di un package (root directory), tipo it.algos.nomeDelProgetto
15. Creazione nella root directory di una classe xxxServlet che estende AlgosServlet. Punto di partenza della sessione nel server.
16. Creazione nella root directory di una classe che estende AlgosUI. Punto di partenza quando si accede dal browser.
17. In Project Settings -> Modules
    - aggiungere al modulo (unico) un componente di tipo IvyIDEA
    - a destra in alto, selezionare il file ivy creato poco prima
    - a destra in basso, selezionare il file ivysettings creato poco prima
17bis. il modulo deve contenere 4 elementi
        - GWT (opzionale)
        - ivyIDEA (obbligatorio) con indicati i 2 files necessari
        - JPA (opzionale)
        - Web (obbligatorio) La posizione nei sorgenti è irrilevante
        - ma va obbligatoriamente modificato il Path relative to Deployment Root -> WEB-INF
        - se appare in basso un triangolo giallo di avviso (Web Facet resources are not included in an artifact), cliccare su Fix
18. Selezionare il progetto ->  (tasto destro) -> IvyIdea -> Resolve
19. In Project Settings -> Libraries
    - cancellare Vaadin Client
    - cancellare Vaadin Server
    - aggiungere New Project Library (tipo java), selezionando ~/Documents/IdeaProjects/webbase/out/artifacts/webbase_jar
    - selezionando la CARTELLA, a destra appariranno due path: uno per i Classes ed uno per i Sources
20. In Project Settings -> Facets
    - riprende i 4 componenti di Modules
21. In Project Settings -> Artifacts
    - aggiungere negli artifacts la libreria webbase_jar
    - se in Project Setting appare in basso a sinistra la scritte Problems, cliccare su Fix e selezionare Add webbase_jar to the artifact
21bis.  artifacts deve contenere in <output root>:
        - una cartella con tutti i sorgenti
        - una cartella WEB-INF con dentro:
            - una cartella classes con:
                - una cartella META-INF con:
                    - il file persistence.xml
            - una cartella data
            - una cartella lib con:
                - ivyIDEA (Module Library)
                - webbase_jar (Project Library)
            - un file web.xml
22. Regolazione del file WEB-INF -> web.xml
22a. Modifica del nome (esistente) della classe com.vaadin.server.VaadinServlet con xxxServlet (appena creata)
22b. Utilizzo di @VaadinServletConfiguration nella classe xxxServlet che estende AlgosServlet
    - Solo una classe nel programma può avere questa Annotation che indica al server da dove far partire l'applicazione
    - Eliminazione completa del file WEB-INF -> web.xml (non indispensabile), oppure
    - Sostituzione del file WEB-INF -> web.xml con informazioni (facoltative) sui <listener-class> (tipo Evento)

