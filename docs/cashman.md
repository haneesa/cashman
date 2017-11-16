# CashMan
## Data structures

### CurrencyNote (object)


+ `currencyValue`: 20 (number, required) - Value of currency.
+ `numberOfNotes`: 1 (number, required) - Number of notes.

### CurrencyNoteEntity (object)

+ id: `20-1` (string, required) - Unique id for response.
+ type: currency-notes (string, required) - Type for response.
+ attributes (CurrencyNote, required)

### CurrencyNotesResponse (object)

+ jsonapi (object)
    + version: 1.0 (string, required) - version of the micro service.
+ data (array, required)
   + (CurrencyNote)
   + (CurrencyNote)
      + id: `50-1` (string, required)

## Group currency-notes

## currency-notes [/currency-notes]
Currency notes endpoint is used to retrieve notes and loddd notes.

### Dispense notes [GET /currency-notes/{?amount}]
+ Parameters

   + amount: 12 (string, optional) - Amount to be dispensed.

+ Request (application/vnd.api+json)

+ Response 200 (application/vnd.api+json)

   + Attributes (CurrencyNotesResponse)

### Load twenty notes [POST /currency-notes/20/{numberOfNotes}]