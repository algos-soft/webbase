1. IDEA, creazione di un nuovo progetto che usa Vaadin col plugin Webbase

2. File -> New -> Project...

3. Selezionare Java Enterprise a sinistra (la seconda opzione)

4. Controllare i valori (potrebbero differire):
    . Project SDK           = 1.8 (java version 1.8.0_25)
    . Java EE Version       = Java EE 7
    . Application Server    = Tomcat 8.0.15

5. NON abilitare nessuna Additional Libraries and Frameworks. Confermare -> Next

6. Selezionare Create project from template. Confermare -> Next

7. Controllare il nome del progetto (minuscolo) e la directory d'installazione. Confermare -> Finish

8. Controllare che il progetto (minimale) funzioni.
    - è stata creata una configurazione col server Tomcat;
    - è stato creato (vuoto) il file web.WEB-INF.web.xml, che verrà successivamente sovrascritto
    - è stato creato il file web.index.jsp, dove si può inserire quello che appare a video
    - senza necessità di ulteriori interventi, selezionando Run l'applicazione funziona (anche se il browser rimane bianco)
    - scrivendo nel file web.index.jsp (head o body) il risultato viene visualizzato
    - successivamente il file web.index.jsp non verrà più utilizzato

9. In Project Settings -> Libraries
    - aggiungere New Project Library (tipo java), selezionando ~/Documents/IdeaProjects/webbase/out/artifacts/webbase_jar
    - selezionando la CARTELLA, a destra apparirà il path per i Classes
    - se in Project Setting appare in basso a sinistra la scritte Problems, cliccare su Fix e selezionare Add webbase_jar to the artifact

10. Aprire il plugin Webbase e lanciare (in Ant) lo script templates.script.progetto:
    - nel primo dialogo, inserire (obbligatorio) il nome (Maiuscolo) del nuovo progetto
    - nel secondo dialogo, inserire (facoltativo, default 'MySqlUnit') il parametro di collegamento persistence-entity
    - nel terzo dialogo, inserire (facoltativo, default 'test') il nome del database mysql

11. È stato creato il package minimo:
    - creata (sotto src) la directory base del progetto -> it.algos.nomeProgetto
    - creato il file (sotto src) META-INF.persistence.xml: parametri di regolazione del database. Elenco di Entity
    - creato il file ivy.xml per le dependencies (modificabile)
    - creato il file ivysettings.xml per le location dove recuperare le dependencies (modificabile)
    - creata la classe xxxApp: repository di costanti generali del programma
    - creata la classe xxxBootStrap: prima classe in esecuzione del programma.
    - creata la classe xxxServlet: punto di partenza della sessione nel server.
    - creata la classe xxxUI: punto di partenza quando si accede dal browser
    - creata la directory vaadin.themes.algos, con anche le icone (theme non ancora funzionante)
    - sostituito il file web.WEB-INF.web.xml
    - sostituito il file web.index.jsp (che non viene utilizzato)
    - senza necessità di ulteriori interventi, selezionando Run l'applicazione funziona con già installato il modulo ereditato
        dal plugin Webbase: Versione

12. In Project Settings -> Modules
    - cliccare sul simbolo + per creare un nuovo modulo
    - selezionare un framework di tipo iviIDEA
    - nel dialogo che si apre a destra selezionare in alto la posizione del file ivy.xml appena creato
    - cliccare nel box 'use module specific ivy settings' e selezionare la posizione del file ivysettings.xml appena creato


// 12. In Project Settings -> Artifatcs in <output root> deve esserci webbase_war_exploded (come Project Library) per visualizzare le icone


13. L'applicazione funziona usando il theme 'valo' (standard). Per utilizzare il theme ''algos' (già caricato):
    - aprire la classe xxxUI e modificare le righe 14/15 per sostituire il theme valo con algos
    - in Project Settings -> Artifatcs selezionare l'artifact web:xxx exploded
    - a destra nel tab Output Layout selezionare l'icona della directory (a sinistra) e crearne una nuova directory
        dal titolo (obbligatorio) VAADIN
    - selezionare la directory appena creata e col tasto destro selezionare Add Copy of -> Directory Content
    - individuare il path della cartella 'vaadin' del progetto appena creato
    - il file vaadin.themes.algos.algos.scss è liberamente modificabile per personalizzare l'applicazopne
    - il file vaadin.themes.algos.algos.scss potrebbe presentare un errore in @import "../valo/valo.scss";
        È un BUG di IDEA che NON influenza la compilazione ed il corretto funzionamento dell'applicazione
    - senza necessità di ulteriori interventi, selezionando Run l'applicazione funziona con installato e funzionante il theme algos



Creazione di un nuovo Modulo:


1. Aprire il progetto Webbase e lanciare (in Ant) lo script templates.script.modulo:
    - nel primo dialogo, inserire (obbligatorio) il nome (Maiuscolo) del progetto di riferimento
    - nel secondo dialogo, inserire (obbligatorio) il nome (Maiuscolo) del nuovo modulo da creare

2. È stato creato il primo modulo (del tipo più ridotto possibile):
    - creata (sotto src.it.algos.nomeProgetto) la cartella/directory del modulo
    - creata la classe (domain) nomeModulo
    - creata la classe (entity) nomeModulo_
    - creata la classe (module) nomeModuloMod
    - modificato il file persistence.xml aggiungendo il nome della Domain class appena creata
    - modificato il metodo addAllModuli della classe nomeProgettoUI (punto di partenza per il browser)
        aggiungendo il nome del modulo appena creato
    - senza necessità di ulteriori interventi, selezionando Run l'applicazione funziona con installato e funzionante il nuovo modulo
