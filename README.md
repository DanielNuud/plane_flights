# Plane Flights

**Plane Flights** on Spring Boot rakendus (Java 21, Gradle 8.12.1), mis võimaldab kasutajatel otsida, sorteerida ja osta lennupileteid ning seejärel vaadata oma tellimusi. Rakendus on tehtud CGI suvepraktikale kandideerimiseks.

---

## Ülevaade

Rakendus pakub järgmisi funktsionaalsusi:

- **Lennuotsing**: vali väljumise linn, saabumise linn, kuupäev, reisijate arv.
- **Sorteerimine**: võimalik sortida väljalennu aja, saabumisaja või lennufirma järgi.
- **Piletite ostmine**: valitud lennu detailsel lehel saab sisestada andmed ja ostu vormistada.
- **Tellimuste vaatamine**: eraldi leht, kus kuvatakse kasutaja tellimusi (vajalik sisselogimine).

---

## Tehnoloogiad

- **Java 21**
- **Spring Boot**
- **Gradle 8.12.1**
- **Thymeleaf** (front-end mallid)
- **Bootstrap 5** (UI kujundus)
- **Andmebaas:** PostgreSQL
- **API:** aviationstack.com ja api-ninjas.com

---

## Paigaldus ja käivitus

### Rakenduse pakkimine

1. **Klooni repositoorium**:
   ```bash
   git clone https://github.com/YourUsername/plane-flights.git
   cd plane-flights
2. **Käivita Gradle Wrapper (Linux/macOS)**:
   ```bash
   ./gradlew bootJar
3. **Valmib .jar fail kausta build/libs, nt:**
   ```bash
   build/libs/plane-flights-0.0.1-SNAPSHOT.jar

### Rakenduse pakkimine

1. **Jookse samas kaustas (või mine build/libs-kataloogi)**
2. **Kasuta käsku:**
   ```bash
   java -jar build/libs/plane-flights-0.0.1-SNAPSHOT.jar
3. **Ava brauseris:**
   http://localhost:8080

---

## Kasutamine

- **Avaleht**: sisaldab vormi, kus saab valida väljumise linna, saabumise linna, kuupäeva ja reisijate arvu. Nupu „Search“ järel kuvatakse sobivad lennud.

- **Sort / Filter** (vasakul): radio-nuppudega (Departure Time, Arrival Time, Airline Name) – klõps „Apply“ uuendab tulemused.

- **Buy** nupp: viib lennu detailsele lehele, kus saab sisestada reisija andmed ning teha ostu.

- **Orders**: leht kasutaja tellimuste vaatamiseks (vajab sisselogimist).

## Levinud probleemid

### Thymeleaf-i andmete kuvamine

Peamine keerukus ilmnes selles, kuidas korrektseid andmeid Thymeleafi šabloonides väljastada. Näiteks:
- Kasutada `th:if` või `th:each` õigel moel, et vältida `NullPointerException`-e, kui mõnel väljal puudus väärtus (nt. `airline == null`).
- Andmete sidumine Java objekti väljadega (nt. `flight.airline.logoUrl`) eeldab, et vastavad seosed on eelnevalt loodud ja mittetühjad.

### Mitme API andmete ühendamine

Rakendus hangib lennuinfot erinevatest allikatest (nt. lennuandmete API ja lennufirma (airline) API). See tekitas paar probleemi:

- **ICAO koodid**: kui ühes andmevoos puudus kindel ICAO kood või see polnud kehtiv, tuli lisada täiendavad kontrollid `AirlineApiService`-s, et vältida 400 vigu.
- **Erinevad struktuurid**: API-d kasutavad mõnikord erinevaid välja nimesid või formaate; tuli veenduda, et parsimine on ühtlustatud (nt. `flight.getAirlineIcao()` vs. `AirlineDataDTO.getIcao()`).
- **Null-juhtumid**: kui mõni API ei tagasta oodatud andmeid, pidi rakendus käsitlema seda nullina (selle asemel, et visata erind).

Kokkuvõttes tuli rakenduses igal pool kontrollida, kas väärtused on saadaval, ja rakendada fallbacke (nt. “Unknown Airline”), kui tegelikud andmed puuduvad.

---

### Lisainfo

Kasutasin ChatGPT-d, et aidata genereerida UI-komponente ning leida lahendusi tõrgetele (nt. Thymeleafi vigade ja API-integreerimisega seotud probleemide lahendamine).


## Kokkuvõte

Selle rakenduse arendamiseks kulus ligikaudu **1.5 nädalat**, samal ajal tegelesin ka diplomitööga. Seetõttu jäi osa algselt planeeritud funktsionaalsusest välja (nt. lennukiiste plaan ja täpsem istumiskoha soovitamine), kuna ajapuudus ei lubanud kõike valmis saada.

**Tulevikuplaan** on aga rakendust edasi arendada, et lõpuks rakendada täielikult kõik algselt kavandatud ülesanded (lennuki istmete detailne soovitamine). Praegune lahendus on seega alles põhi, millele lisafunktsionaalsus hiljem peale ehitada.






