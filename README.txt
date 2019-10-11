For more documentation check the P1___ITCOM_5.pdf in the files.

This is the product of a 3 month project, we wanted to create an educational game, based on another game called
'Darkest Dungeon'.

The game requires Java to run.

Credits:

Mads Herlevsen
Christian Midjord Holfelt
Demira Zhivkova Dimitrova
Máté Péter Balatoni
Nourjan Sido
Patrick Dominique Vibild

------------

To make new maps, make a map in Tiled with 32x32 pixel squares. Add monsters and other objects,
look at exploring map to see their name. Add a property to the objects in the object layer, with "name" and "x",
where x is the object you want to add.
Next add it to the assents folder under maps (assets/maps/ + "map name" + .tmx).
Add it to the MapLayout enum with the number as the map before your new map.
The second argument as the effect you wish for the map, and last the name of the file.

------------

To make your own question list, make a backup of the old questions if you wish to keep them.
Then create a new file with the name "questionsanswers.txt".
In that file write the first line as your question the second line as the correct answer,
line three, four and five should be wrong but plausible answers.
Repeat this format as many times as you want, with a new question every 5 lines.

It should look like this:

1. question
2. correct
3. wrong
4. wrong
5. wrong
1. question
2. correct
3. wrong
4. wrong
5. wrong
1. question
2. correct
3. wrong
4. wrong
5. wrong

------------

The error screen will open if the "questionsanswers.txt" file can't be found in the "/assets/QnA/" folder.
Make sure the file is there, is spelled correctly and it is a .txt file.

If the file is there and it is in the correct format:

Then make sure there are atleast one full question in the file.
There needs to be atleast 5 lines of text to make a valid question.
