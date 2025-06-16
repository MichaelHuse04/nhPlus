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

## Anmerkungen

- Alle Tests wurden manuell durchgeführt.
- Die Ergebnisse entsprechen den erwarteten Zuständen.
- Keine Abweichungen festgestellt.


