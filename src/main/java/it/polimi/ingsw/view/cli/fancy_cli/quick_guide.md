# <font size=+12>Quick guide</font>

# How to run the code

To use this package, the code must be compiled into a `.jar` file and run in a terminal, as the normal console some IDE uses when running a program doesn't support some requested features.

1) First must be created a new jar artifact. To do so in IntelliJ, follow the guide on this [link](https://stackoverflow.com/a/45303637/2640826)
2) After that, and everytime the code must be run, you need to build the artifact. In IntelliJ, simply open `Build`->`Build artifacts...` and then `build`/`rebuild`. You should now see the artifact under `out\artifacts\{project_name_jar}\{project-name.jar}`
3) Now you can run the program from the terminal you like (i.e. the one of your operating system or the IDE) by going to the folder in which the jar is located and running the command `java -jar {name-of-the-jar}` (make sure that you have `java` as an available command for the command line)



# How to use the package
This package provides some useful classes to create a more graphical CLI.
There are three main components to do that: `Widget`, `Canvas` and `InputReader`.

## 1. Widget
Widgets are the components used to create the content to show on the screen. They provide basic functionality on their own, but can be combined to create more complicated designs.
Widgets can be divided in three categories: basics, decorators and layouts.

1) The basics widgets, as the name said, provide very basic graphical behaviour (i.e. show text on the screen). They are the leaves of the widget tree you create when building something to show on the screen.
2) The decorators usually wraps another widget to add some property or functionality to it. These are useful when you need to add some specific visual effects to a widget (i.e. a border around a text).
3) Layout widgets are used to combine multiple widgets into one, arranging them in a specific order.

Let's see now some widgets in details. 

### 1.1.1 Text

The `Text` widget is used, as expected, to show text on the screen.
The text provided can be of any length, and an opposite parameter of the constructor can be used to limit the horizontal space occupied by this widget, wrapping the text accordingly and expanding in the vertical direction.
The `Text` widget has two more parameters, `foregroundColor` and `backgroundColor`, used to change the color of the text. The text is also styleable by providing any combination of `TextStyle` arguments.

This widget support also multi lined strings as input. However, in that case the widget should be left free to calculate it's size, as restricting the possible size can lead to a very bad output.

### 1.1.2 Icon

The `Icon` widget let you draw icons on the console.
The foreground and background color of the icon can be changed, and the icons are always one character big.

### 1.2.1 Padding

This widget let you add some blank space around a widget. 
This could be symmetric, meaning that an equal space is applied in all four directions, or asymmetric, in which the space is equal for the two vertical directions and for the two horizontal directions.

### 1.2.2 Border

With this widget you can add a border around a widget. There are four types of borders:
* single, which creates a single line border
* double, which creates a double line border
* double-vertical, which creates a single line in the horizontal space and a double line in the vertical space
* double-horizontal, which creates a single line in the vertical space and a double line in the horizontal space

However, the last two are not supported in every terminal and should be used carefully

### 1.2.3 SizedBox

The `SizedBox` widget is used to reserve a minimum amount of space on the terminal. 
It can be used to assure that a widget is no less big that the sizes specified, or to reserve some empty space.
An additional `Alignment` property should be set to indicate how to align the child if it is smaller that the `SizedBox` itself.

### 1.3.1 Row

The first of the layout widgets, can be used to arrange other widget next to each other in a row.
The content in the row can be changed dynamically with the `addChild` and `removeLast` methods, following the LIFO rules.

### 1.3.2 Column

Very similar to the one before, the `Column` widget arranges its children vertically in a column.
Even if `Row` and `Column` can be used freely one inside the other, it is preferred (when possible) to use column of rows instead of row of columns as the latter, in some situation, can lead to unwanted behaviour.

### 1.3.3 GridView

This widget creates a 3x3 grid with all the cells having the same size. 
The alignment of the child in the cell can be selected by the corresponding parameter.

### 1.3.4 Header

This is a little different from all the other layout widgets, as this is used only to arrange text in a prefixed way.
In fact this is meant to be used when a title of some sort should be displayed, with optionally a subtitle. 
The two text are centered in the `Header` widget and displayed one below the other.

## 2. Canvas

All the previous widgets defines only how the content should be displayed on the screen, but the actual displaying is made by the `Canvas` class.
The content of a canvas is made of a title, a subtitle and the actual content (a widget).

When displaying its content, the canvas has two possible behaviours; drawing right after the previous line written keeping the chronology of all the changes that happened, or deleting the content shown on the console and rewriting a new one.
The default behaviour is the latter as it simulates a UI updating its content.
The canvas has also a parameter to indicate if it needs to redraw itself if something in its content changes. This is the default behaviour and should be left as it is to have a better user experience.
If you choose to disable this behaviour, remember to manually update the canvas by showing it again, or the user will never see the changes!

Although more than one canvas at a time is not forbidden, it's generally preferable and more flexible to have a single canvas showing content.
To switch from two canvas to one, try to place the two contents in a column, putting in between some empty space / headers if needed.
If it's really necessary to use more than one canvas at a time, it's advised to disable the auto updating and the deleting of the console when showing a canvas.

## 3. InputReader

This class is used to read inputs from the console. It has a method `readInput` used to show a message to the user, wait for it to type a response, and then return the response to the caller of the method.

The `InputReader` can be used in addition to `Validator`s and `Completer`.
The former are used to do a preliminary check on the input based on the rules provided, the latter to suggest the user a set of command he can choose from and autocomplete if `Tab` is pressed.
If the validation process fails, the `InputReader` will show an erro message to the user and ask again for an input.

It is perfectly fine, and suggested, to have multiple instances of this class to better handle the single cases in which an input from the user is needed.
However, reading from multiple `InputReader` at the same time could lead to issue.




