# Testing

Dokumentiert über diese Markdown-Datei eure Tests. Führt dazu in einer Tabelle alle Testfälle auf,
und gebt in einer zweiten Spalte das Ergebnis des jeweiligen Tests an. Über die Editor-Ansicht rechts oben
gelangst du in die Ansicht, in der du Inhalte hinzufügen kannst. Die Markdown-Syntax kannst du unter 
https://markdown.de/ einsehen.


# Testdokumentation

## Testfälle Assets

| Testfall-ID | Testbeschreibung                                                                                                                                                                                                                                                                                                               | Testergebnis                         |
|-------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------|
| TF_1        | **Patientendaten anzeigen**<br>**Vorbedingung:** Nutzer wählt im Hauptfenster einen Patienten aus<br>**Testschritte:** –<br>**Erwartetes Ergebnis:** Es werden alle Patienten, die in der Datenbank gespeichert sind, mit ihrer ID, Nachnamen, Vornamen, Geburtsdatum, Pflegestufe und Raumnummer in einer TableView angezeigt | ✅ Erfolgreich                        |
| TF_2        | **Datenbankdaten auf Vermögensstand überprüfen**<br>**Vorbedingung:** Zugriff auf Datenbank<br>**Testschritte:** 1. Öffnen der Datenbank<br>2. Überprüfen der Spalten, ob „Vermögensstand“ vorhanden ist<br>**Erwartetes Ergebnis:** Es gibt keine verbleibenden Daten zum Vermögensstand von Patienten                        | ✅ Erfolgreich (Feld nicht vorhanden) |

---

## Testfälle Pflegermodul

| Testfall-ID | Testbeschreibung                                                                                                                                                                                                                                                                                                                                | Testergebnis                                                                                                                         |
|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------|
| TF_1        | **Sichtbarkeit Pfleger-Button (angemeldet)**<br>**Vorbedingung:** Anwendung ist gestartet, Nutzer ist eingeloggt<br>**Testschritte:** 1. Prüfen, ob Button „Pfleger“ sichtbar ist<br>**Erwartetes Ergebnis:** Button ist sichtbar                                                                                                               | ✅ Erfolgreich                                                                                                                        |
| TF_2        | **Sichtbarkeit Pfleger-Button (unangemeldet)**<br>**Vorbedingung:** Anwendung ist gestartet, Nutzer ist **nicht** eingeloggt<br>**Testschritte:** 1. Prüfen, ob Button „Pfleger“ sichtbar ist<br>**Erwartetes Ergebnis:** Button ist **nicht** sichtbar                                                                                         | ✅ Erfolgreich                                                                                                                        |
| TF_3        | **Öffnen Pflegerübersicht**<br>**Vorbedingung:** Anwendung ist gestartet, Nutzer ist eingeloggt, Button „Pfleger“ ist sichtbar<br>**Testschritte:** 1. Klick auf Button „Pfleger“<br>2. Überprüfen, ob `AllCaregiverView.fxml` geladen wird<br>**Erwartetes Ergebnis:** `AllCaregiverView.fxml` wird korrekt angezeigt                          | ✅ Erfolgreich                                                                                                                        |
| TF_4        | **Fehlermeldung beim Öffnen Pflegerübersicht (View fehlt)**<br>**Vorbedingung:** Anwendung gestartet, Nutzer eingeloggt, `AllCaregiverView.fxml` fehlt<br>**Testschritte:** 1. Klick auf Button „Pfleger“<br>2. Verhalten beobachten<br>**Erwartetes Ergebnis:** Es erscheint eine Fehlermeldung zum fehlenden FXML-File                        | ✅ Fehler wird korrekt behandelt                                                                                                      |
| TF_5        | **Pflegerdaten in Ansicht anzeigen**<br>**Vorbedingung:** Anwendung gestartet, Nutzer eingeloggt<br>**Testschritte:** 1. Klick auf Button „Pfleger“<br>2. Daten in Tabellenansicht prüfen<br>**Erwartetes Ergebnis:** Pflegerdaten werden vollständig und korrekt angezeigt                                                                     | ✅ Erfolgreich                                                                                                                        |
| TF_6        | **Pflegerauswahl bei neuer Behandlung**<br>**Vorbedingung:** Anwendung gestartet, Nutzer eingeloggt<br>**Testschritte:** 1. Button „Behandlungen“ klicken<br>2. „Neue Behandlung anlegen“ klicken<br>3. Pfleger über Combobox auswählen<br>4. Behandlung speichern<br>**Erwartetes Ergebnis:** Pfleger wird korrekt in der Behandlung angezeigt | ✅ Erfolgreich                                                                                                                        |
| TF_7        | **~~Behandlung ohne Pfleger speichern~~**<br>**~~Vorbedingung:~~** ~~Anwendung gestartet, Nutzer eingeloggt~~<br>**~~Testschritte:~~** ~~1. Neue Behandlung anlegen~~<br>~~2. Kein Pfleger ausgewählt~~<br>~~3. Anlegen versuchen~~<br>**~~Erwartetes Ergebnis:~~** ~~Fehlermeldung erscheint, dass Pfleger ausgewählt werden muss~~            | ❌ Test wird nicht mehr benötigt, da keine ohne Angabe eines Pflegers der Button "Anlegen" in einer neuen Behandlung deaktiviert ist. |

---

## Testfälle Login-Funktion

| Testfall-ID | Testbeschreibung | Testergebnis |
|-------------|------------------|--------------|
| TF_1 | **Erfolgreicher Login mit gültigen Zugangsdaten**<br>**Vorbedingung:** Ein registrierter Pfleger mit gültigen Login-Daten existiert<br>**Testschritte:**<br>1. Login-Maske öffnen<br>2. Benutzernamen und Passwort eingeben<br>3. Auf den Login-Button klicken<br>**Erwartetes Ergebnis:** Der Pfleger wird erfolgreich eingeloggt und auf die Startseite weitergeleitet | ✅ Erfolgreich |
| TF_2 | **Fehlermeldung bei falschen Anmeldedaten**<br>**Vorbedingung:** Ein registrierter Pfleger mit gültigen Login-Daten existiert<br>**Testschritte:**<br>1. Login-Maske öffnen<br>2. Falschen Benutzernamen und Passwort eingeben<br>3. Auf den Login-Button klicken<br>**Erwartetes Ergebnis:** Eine Fehlermeldung wird angezeigt | ✅ Erfolgreich |
| TF_3 | **Passwort ist verschlüsselt in der Datenbank gespeichert**<br>**Vorbedingung:** Ein registrierter Pfleger mit gültigen Login-Daten existiert<br>**Testschritte:**<br>1. Datenbank öffnen<br>2. Gespeicherte Passwörter überprüfen<br>**Erwartetes Ergebnis:** Passwörter sind verschlüsselt und nicht im Klartext lesbar | ✅ Erfolgreich |
| TF_4 | **Fehlermeldung bei leerem Benutzernamen-Feld**<br>**Vorbedingung:** Die Login-Maske ist geöffnet<br>**Testschritte:**<br>1. Benutzername leer lassen, Passwort eingeben<br>2. Auf den Login-Button klicken<br>**Erwartetes Ergebnis:** Fehlermeldung erscheint, dass beide Felder ausgefüllt werden müssen | ✅ Erfolgreich |
| TF_5 | **Fehlermeldung bei leerem Passwort-Feld**<br>**Vorbedingung:** Die Login-Maske ist geöffnet<br>**Testschritte:**<br>1. Passwort leer lassen, Benutzernamen eingeben<br>2. Auf den Login-Button klicken<br>**Erwartetes Ergebnis:** Fehlermeldung erscheint, dass beide Felder ausgefüllt werden müssen | ✅ Erfolgreich |
| TF_6 | **Keine Neu-Registrierung über die Login-Maske möglich**<br>**Vorbedingung:** Die Login-Maske ist geöffnet<br>**Testschritte:**<br>1. Neue, nicht registrierte Zugangsdaten eingeben<br>2. Auf den Login-Button klicken<br>**Erwartetes Ergebnis:** Fehlermeldung wird angezeigt | ✅ Erfolgreich |
| TF_7 | **Fehlermeldung bei komplett leeren Eingabefeldern**<br>**Vorbedingung:** Es existiert mindestens ein Account für einen Pfleger<br>**Testschritte:**<br>1. Beide Felder leer lassen<br>2. Auf den Login-Button klicken<br>**Erwartetes Ergebnis:** Fehlermeldung wird angezeigt | ✅ Erfolgreich |
| TF_8 | **Mehrfach Fehlversuche führen zur Fehlermeldung und Timeout**<br>**Vorbedingung:** Die Login-Maske ist aufrufbar<br>**Testschritte:**<br>1. Falsche Zugangsdaten eingeben<br>2. Mehrfach (mind. 5x) auf Login klicken<br>**Erwartetes Ergebnis:** Fehlermeldung und Timeout werden angezeigt | ✅ Erfolgreich |

---

## Testfälle Datenverwaltung

| Testfall-ID | Testbeschreibung                                                                                                                                                                                                                                                                                                                                                                                                      | Testergebnis |
|-------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
| TF_1 | **Erstelle in der Datenbank eine neue Tabelle für Abgeschlossene Behandlungen.**<br>**Vorbedingung:** Für Behandlungen gibt es nur eine Tabelle in der Datenbank.<br>**Testschritte:** Eine neue Tabelle wird in der Datenbank erstellt. <br>**Erwartetes Ergebnis:** Es sind zwei Tabellen in der Datenbank für Behandlungen vorhanden.                                                                                   | ✅ Erfolgreich |
| TF_2 | **Daten in der neuen Tabelle sind gesperrt und können nicht geändert werden.**<br>**Vorbedingung:** In der neuen Tabelle kann man Daten bearbeiten.<br>**Testschritte:** Daten werden in der neuen Tabelle gesperrt. <br>**Erwartetes Ergebnis:** Daten in der neuen Tabelle können nicht bearbeitet werden.                                                                                                               | ✅ Erfolgreich |
| TF_3 | **Füge alle Abgeschlossenen Behandlungen von der alten Tabelle in die neue hinzu.**<br>**Vorbedingung:** Neue Tabelle existiert, ist aber leer.<br>**Testschritte:** Datensätze werden aus der alten Tabelle kopiert. <br>**Erwartetes Ergebnis:** Neue Tabelle beinhaltet Datensätze von beendeten Behandlungen.                                                                                                          | ✅ Erfolgreich |
| TF_4 | **Lösche alle abgeschlossenen Behandlungen aus der alten Tabelle.**<br>**Vorbedingung:** Alte Tabelle enthält Datensätze von beendeten Behandlungen.<br>**Testschritte:** Datensätze werden gelöscht. <br>**Erwartetes Ergebnis:**  Alte Tabelle beinhaltet nur noch Datensätze von beendeten aktiven Behandlungen                                                                                                         | ✅ Erfolgreich |
| TF_5 | **Wenn eine Behandlung abgeschossen wird, wird sie automatisch in der neuen Tabelle hinzugefügt.**<br>**Vorbedingung:** Neue Tabelle existiert.<br>**Testschritte:** Behandlung wird abgeschlossen. <br>**Erwartetes Ergebnis:** Neue Tabelle bekommt einen neuen Eintrag.                                                                                                                                                 | ✅ Erfolgreich |
| TF_6 | **Wenn eine Behandlung abgeschossen wird, wird sie automatisch aus der alten Tabelle gelöscht.**<br>**Vorbedingung:** Alte Tabelle enthält Datensätze.<br>**Testschritte:** Behandlung wird abgeschlossen. <br>**Erwartetes Ergebnis:** Ein Datensatz wird aus der alten Tabelle gelöscht.                                                                                                                                 | ✅ Erfolgreich |
| TF_7 | **Daten werden in der neuen Tabelle nach 10 Jahren automatisch gelöscht.**<br>**Vorbedingung:** Datensätze, die kurz vor 10 Jahre alt sind, in der neuen Tabelle bereitstellen (z.B.: eine Minute vor zehn Jahren).<br>**Testschritte:** Warten bis die bereitgestellten Datensätze 10 Jahre alt sind. <br>**Erwartetes Ergebnis:** Datensätze, die 10 Jahre alt sind, werden automatisch gelöscht.                   | ✅ Erfolgreich |
| TF_8 | **Personenbezogene Daten können aus der alten Tabelle manuel erst nach 10 gelöscht werden.**<br>**Vorbedingung:** Datensätze, die kurz vor 10 Jahre alt sind, in der alten Tabelle bereitstellen (z.B.: eine Minute vor zehn Jahren).<br>**Testschritte:** Warten bis die bereitgestellten Datensätze 10 Jahre alt sind.<br>**Erwartetes Ergebnis:** Datensätze, die älter als 10 Jahre sind, können gelöscht werden. | ✅ Erfolgreich |


---
## Anmerkungen

- Alle Tests wurden manuell durchgeführt.
- Die Ergebnisse entsprechen den erwarteten Zuständen.
- Keine Abweichungen festgestellt.


