# FlashCardGenerator
Android app that generates flashcards manually or through voice recognition API. This project developed for HackPSU hackathon by Sreekuttan, Dhruvil, Viet, and Rohan.

## Inspiration

Flash cards are useful means of revision that many teachers recommend using it.However, it is time-consuming to write a set of flash cards while wasting a large amount of paper to write a small set of words, not to mention how annoying it is to manage different collections of cards without getting lost some or turning them into piles of mess.

## What it does

Generates flash cards for students manually or through speech to text process.
Neatly stores and manages flashcards or revision.
Help students review lessons through generating random flash cards.

## Available Voice Commands
- collections: jump to collections options.
- collections (collection name): jump to specified collection.
- (collection name): jump to specified collection.
- review: jump to review options.
- review (collection name): jump to the review screen of a specified collection.
- add: create new collection.
- new: create new collection.
- add (collection name): create new flashcard in specified collection.
- new (collection name): create new flashcard in specified collection.

## How we built it

We used Android Studio to design and code the app in Java and XML and speech-to-text API from IBM Watson to create command which the app follows to make flash cards.

## Challenges we ran into

One challenge we ran into was integrating the Speech To Text API into the app which was confusing and there were some bugs in the package probably due to lack of updates that we had to rewrite some API classes in order for the app to be compilable.

## Accomplishments that we're proud of

Successfully implemented the database to save the load flash cards.
Implemented Speech-To-Text API for writing the text from speech.
Understand and rewrite some code in Speech-To-Text API.

## What we learned

We learned to use SQL to build a database. We learned how to integrate an API into an app.

## What's next for FlashCard Generator

Have a way to schedule a time frame for studying a set of flash cards, In this period, the app will notify the user something like "Hey, its time to study for your biology exams!" We also want to utilize the text-to-speech API from IBM Watson to make the app speak the flashcard set to you.
