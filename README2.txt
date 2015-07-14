1. IDEA, creazione di un nuovo progetto che usa Vaadin col plugin WebBase

2. File -> New -> Project...

3. Selezionare Java Enterprise a sinistra (la seconda opzione)

4. Controllare i valori (potrebbero differire):
    . Project SDK           = 1.8 (java version 1.8.0_25)
    . Java EE Version       = Java EE 7
    . Application Server    = Tomcat 8.0.15

5. NON abilitare nessuna Additional Libraries and Frameworks. Confermare -> Next

6. Selezionare Create project from template. Confermare -> Next

7. Controllare il nome del progetto e la directory d'installazione. Confermare -> Finish

8. Controllare che il progetto (minimale) funzioni.
    - è stata creata una configurazione col server Tomcat;
    - è stato creato il file web->WEB_INF->index.jsp, dove si può inserire quello che appare a video
    - senza necessità di ulteriori interventi, selezionando Run il programma funziona

9. In Project Settings -> Libraries
    - aggiungere New Project Library (tipo java), selezionando ~/Documents/IdeaProjects/webbase/out/artifacts/webbase_jar
    - selezionando la CARTELLA, a destra appariranno due path: uno per i Classes ed uno per i Sources
    - se in Project Setting appare in basso a sinistra la scritte Problems, cliccare su Fix e selezionare Add webbase_jar to the artifact

10. Creazione in src di un package (root directory), tipo it.algos.nomeDelProgetto

11. Creazione nella root directory di una classe xxxServlet che estende AlgosServlet. Punto di partenza della sessione nel server.

12. Creazione nella root directory di una classe che estende AlgosUI. Punto di partenza quando si accede dal browser.

13. Aggiungere in xxxServlet il riferimento alla UI con l'annotazione @VaadinServletConfiguration(ui = xxxUI.class)

14. Controllare che il progetto Vaadin con WebBase funzioni, selezionando Run

