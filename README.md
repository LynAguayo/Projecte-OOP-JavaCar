# 🚗 JavaCar - Sistema de gestió de lloguers de vehicles 
## 📚 Taula de Continguts

1. [Introducció](#📖-introducció)
2. [Estructura dels Menús](#📋-estructura-dels-menús)
    - [Menú Client](#👤-menú-client)
    - [Menú Administrador](#👨‍💼-menú-administrador)
3. [Sistema de Càlcul d'Etiquetes Ambientals](#🔍-sistema-de-càlcul-detiquetes-ambientals)
    - [Context i Solució Tècnica](#context-i-solució-tècnica)
    - [Exemple Pràctic](#exemple-pràctic)
4. [Arquitectura del Sistema](#🏗️-arquitectura-del-sistema)
    - [Components Principals](#components-principals)
    - [Classes Auxiliars](#classes-auxiliars)
5. [Persistència de Dades](#💾-persistència-de-dades)
6. [Documentació Addicional](#📑-documentació)

## 📖 Introducció

JavaCar és un sistema de gestió de lloguers de vehicles desenvolupat en Java. El sistema presenta una arquitectura modular que permet:

- Gestió dual amb perfils diferents per a clients i administradors
- Càlcul automàtic de les etiquetes ambientals segons normativa DGT
- Sistema de recompenses ecològiques basat en punts
- Emmagatzematge persistent en fitxers de text

## 📋 Estructura dels Menús

### 👤 Menú Client
1. **Operacions de Lloguer**
   - Consulta de disponibilitat en temps real
   - Procés complet de lloguer amb selecció de dates
   - Cancel·lació de lloguers amb confirmació

2. **Gestió de Reserves**
   - Reserva anticipada amb generació de codi únic
   - Modificació flexible de dates i durada
   - Cancel·lació amb notificació

3. **Eines de Comparació**
   - Anàlisi comparativa de fins a 3 vehicles
   - Visualització de característiques tècniques

4. **Gestió del Compte**
   - Visualització i canvi de punts ecològics
   - Historial complet de lloguers
   - Sistema de valoració (1-5 estrelles)
   - Actualització de dades personals

### 👨‍💼 Menú Administrador
1. **Estadístiques i Informes**
   - Top 5 vehicles més llogats
   - Vehicles millor valorats per puntuació
   - Rànquing de clients per punts ecològics
   - Lloguers actius amb filtre temporal

## 🔍 Sistema de Càlcul d'Etiquetes Ambientals

### Context i Solució Tècnica
Per determinar l'etiqueta ambiental DGT sense modificar l'estructura existent de la classe Vehicle, vam implementar el sistema:

1. **Estima l'any de matriculació**:
   - Analitza els dos primers dígits de la matrícula
   - Fórmula:
     ```java
     if (digits <= 30) year = 2000 + digits;
     else year = 1900 + digits;
     ```
   - Exemple: "15" → 2015, "85" → 1985

2. **Determina l'estàndard Euro**:
    2. **Determina l'estàndard Euro**:

| Any de Matriculació | Estàndard Euro Assignat |
|---------------------|-------------------------|
| 2000 - 2004         | Euro 3                  |
| 2005 - 2009         | Euro 4                  |
| 2010 - 2014         | Euro 5                  |
| 2015 en endavant    | Euro 6                  |

3. **Assigna l'etiqueta final** segons:
   - **Turismes**:
      - 0 Emissions: Vehicles elèctrics purs
      - ECO: Híbrids/GNC/GLP (Euro 4+)
      - C: Gasolina Euro 4+ o Dièsel Euro 6
      - B: Gasolina Euro 3 o Dièsel Euro 4-5

   - **Furgonetes**:
      - ECO requereix Euro 6
      - Normatives més estrictes per dièsel

   - **Motos**:
      - ECO només per híbrides
      - Euro 2 → B
      - Euro 3-4 → C

### Exemple 
Per a un vehicle amb matrícula **"1234XYZ"**:
1. Extracció "12" → Any estimat: 2012
2. Estàndard Euro: 5 (2010-2014)
3. Assignació:
   - Dièsel → Etiqueta B
   - Gasolina → Etiqueta C
   - Híbrid → Etiqueta ECO

## 🏗️ Arquitectura del Sistema

### Components Principals
- **Gestió d'Usuaris**:
   - `Client.java`: Model de dades dels clients
   - `ClientService.java`: Lògica de negoci i persistència

- **Gestió de Vehicles**:
   - `Vehicle.java`: Classe abstracta base
   - Especialitzacions (`Cotxe.java`, `Moto.java`, `Furgoneta.java`)
   - `VehicleService.java`: CRUD complet

- **Gestió de Lloguers**:
   - `Lloguer.java`: Model amb estats (Pendent, Actiu, etc.)
   - `LloguerService.java`: Operacions complexes

### Classes Auxiliars
- `AjudaEntrada.java`: Validació robusta d'entrades
- `EtiquetaAmbiental.java`: Enum amb normativa DGT
- `Motor.java` i `Roda.java`: Components tècnics

## 💾 Persistència de Dades

El sistema utilitza 4 fitxers principals per a l'emmagatzematge de dades:

1. **clients.txt**  
   *Finalitat*: Emmagatzema la informació dels clients registrats  
   *Format*: `DNI;Nom;Adreça;Punts`  
   *Exemple*: `12345678A;Joan Garcia;Carrer Major 123;150`

2. **vehicles.txt**  
   *Finalitat*: Conté el registre de tots els vehicles disponibles  
   *Format*: `Tipus;Matrícula;Marca;Model;Preu;Motor;Potència;Rodes;Extra`  
   *Exemple*: `Cotxe;1234ABC;Seat;Ibiza;35.0;gasolina;110;4;5`  
   *Detalls camp Extra*:
    - Cotxe: Nombre de places (ex: 5)
    - Moto: Cilindrada en cc (ex: 600)
    - Furgoneta: Capacitat de càrrega en kg (ex: 1200)

3. **lloguers.txt**  
   *Finalitat*: Registra tots els lloguers realitzats  
   *Format*: `ID;DNI;Matrícula;Inici;Fi;Dies;Preu;Estat`  
   *Exemple*: `LL1650000000000;12345678A;1234ABC;15/01/2025;20/01/2025;3;105.0;COMPLETAT`

4. **valoracions.txt**  
   *Finalitat*: Emmagatzema les valoracions dels clients  
   *Format*: `DNI;Matrícula;Puntuació;Comentari`  
   *Exemple*: `12345678X;3456JKL;3;Molt còmode`

Tots els fitxers es troben al directori `/data/`.

## 📑 Documentació
Pots consultar els diagrames i la metodologia SCRUM de la pràctica en el següent enllaç:

[Memòria del Projecte](https://docs.google.com/document/d/1Rt8LnmzkUKNtWyHODPTeBAkKiQP0RvjFG2uBxGbGey0/edit?usp=sharing)
