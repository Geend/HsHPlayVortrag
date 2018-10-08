

## Forms

### Form<T>
* Form binden
* Daten auslesen
* Felder vorbelegen

[Http Parameter]


-> Basis Projekt zeigen (User Klasse und Speichern von Usern im Controller)
-> creat_user.scala.html zeigen und erklären

-> showCreateUserForm() und createUser() ohne error handling implementieren

[Data Binding]

-> Frage: Was fehlt? --> Error handling (erstmal nur Binding Fehler)

-> Error handling implementieren (if(boundForm.hasErrors())...)


-> Frage: Was fehlt? --> Validierung (username requierd zB)
-> Simple Annotations Constraints in User Klasse implementieren --> Feldbezogene Fehlermeldung durch HtmlHelper

-> Username darf nicht "admin" sein -> validate Methode (String Rückgabe)
-> Ist jetzt globaler Fehler. Wie gehts das besser? -> ValidationError
-> Was bei mehreren Fehlern? List<ValidationError>


-> showEditUserForm() und editUser() implementieren
-> Demo: Man muss das pw wieder eingeben (wegen dem constraint)
-> Wie gehts besser? Validationsgruppen.
-> Interface anlegen und Gruppe für Passwort festlegen.
-> createUser anpassen



Sonstiges:
DynamicForm
Custom class-level constraints with DI support


### HTML Form Helper
* Generiert u.a Action URL, Validierungsfehler, Zeigt constraints an,...
* Input Felder müssen aber weiterhin hingeschrieben werden (insb. die Namen der Attribute)

## Validierung

### Annotationen


### Anzeigen von Validierungsfehlern
* Globale und Feldbezogene Fehler


### validate Methode (Constraints.Validatable)


### Gruppen
