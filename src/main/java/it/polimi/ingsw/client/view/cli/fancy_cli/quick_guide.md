# <font size=+12>Quick guide</font>

# How to run the code

To use this package, the code must be compiled into a `.jar` file and run in a terminal, as the normal console some IDE
uses when running a program doesn't support some requested features.

1) First must be created a new jar artifact. To do so in IntelliJ, follow the guide on
   this [link](https://stackoverflow.com/a/45303637/2640826)
2) After that, and everytime the code must be run, you need to build the artifact. In IntelliJ, simply open `Build`
   ->`Build artifacts...` and then `build`/`rebuild`. You should now see the artifact
   under `out\artifacts\{project_name_jar}\{project-name.jar}`
3) Now you can run the program from the terminal you like (i.e. the one of your operating system or the IDE) by going to
   the folder in which the jar is located and running the command `java -jar {name-of-the-jar}` (make sure that you
   have `java` as an available command for the command line)

# How to use the package

This package provides some useful classes to create a more graphical CLI.
There are three main components to do that: `Widget`, `Canvas` and `InputReader`.

> A code example is provided at the end of the guide.

## 1. Widget

Widgets are the components used to create the content to show on the screen. They provide basic functionality on their
own, but can be combined to create more complicated designs.

To create new custom widget you can extend the `StatefulWidget` and `StatelessWidget` classes.
These classes provide a `build` method used to define which inner widget/s is going to compose your custom widget.
In this method, other interaction with the console should be avoided, as it can mess up with the drawing of the widgets.
There is also a `create` method that need to be called in the constructor of widgets which have custom attributes after all these attributes are initialized.
Not doing so will cause the widget to not render properly.

* `StatefulWidgets` are used when the content of the widget can change dynamically.
  Changes in the content of a `StatefulWidget` are reflected on the screen, and it will be repainted.
  The build method of the `StatefulWidget` is run every time it must be displayed on the screen or when something
  changes, so doing heavy calculations in this method should be avoided.

* `StatelessWidget`, on the other hand, should be used when the content of the widget can't change, or changes should be
  ignored.
  In fact, even if the content of this type of widgets can change, those changes are never reflected on the screen and
  the console is never updated.
  Because of this, the build method can be run only once to create the widget, and then recycle it every time it must be
  displayed on the screen.
  Due to that, `StatelessWidgets` are more lightweight than `StatefulWidgets`, and should be used everytime is possible.
  However, be careful when passing external widgets to a `StatelessWidget`, because every widget inside it will also be
  converted to be stateless. In this case though, if the content of the passed widget changes and is also used outside
  the `StatelessWidget`, the change will be shown on the screen at the next update (it will not update the screen
  automatically).

The widgets in this library are all stateful by default to be more flexible about their usage.
However, if you want to disable this behaviour (or convert any `StatefulWidget` into a `StatelessWidget`), you can use
the static method `StatelessWidget.from(Widget widget)` to convert any type of widget into a `StatelessWidget`. If the
provided widget is already stateless, nothing will change.

Widgets can be divided in three categories: basics, decorators and layouts.
Let's now see them in details.

### 1.1 Basic widgets

The basics widgets, as the name said, provide very basic graphical behaviour (i.e. show text on the screen).
They are the leaves of the widget tree you create when building something to show on the screen.

#### 1.1.1 Text

The `Text` widget is used, as expected, to show text on the screen.
The text provided can be of any length, and an opposite parameter of the constructor can be used to limit the horizontal
space occupied by this widget, wrapping the text accordingly and expanding in the vertical direction.
The `Text` widget has two more parameters, `foregroundColor` and `backgroundColor`, used to change the color of the
text. The text is also styleable by providing any combination of `TextStyle` arguments.

This widget support also multi lined strings as input. However, in that case the widget should be left free to calculate
it's size, as restricting the possible size can lead to a very bad output.

#### 1.1.2 Icon

The `Icon` widget let you draw icons on the console.
The foreground and background color of the icon can be changed, and the icons are always one character big.

### 1.2 Decorator widgets

The decorators usually wraps another widget to add some property or functionality to it.
These are useful when you need to add some specific visual effects to a widget (i.e. a border around a text).

#### 1.2.1 Padding

This widget let you add some blank space around a widget.
This could be symmetric, meaning that an equal space is applied in all four directions, or asymmetric, in which the
space is equal for the two vertical directions and for the two horizontal directions.

#### 1.2.2 Border

With this widget you can add a border around a widget. There are four types of borders:

* single, which creates a single line border
* double, which creates a double line border
* double-vertical, which creates a single line in the horizontal space and a double line in the vertical space
* double-horizontal, which creates a single line in the vertical space and a double line in the horizontal space

However, the last two are not supported in every terminal and should be used carefully

#### 1.2.3 SizedBox

The `SizedBox` widget is used to reserve a minimum amount of space on the terminal.
It can be used to assure that a widget is no less big that the sizes specified, or to reserve some empty space.
An additional `Alignment` property should be set to indicate how to align the child if it is smaller that the `SizedBox`
itself.

### 1.3 Layout widgets

Layout widgets are used to combine multiple widgets into one, arranging them in a specific order.

#### 1.3.1 Row

The first of the layout widgets, can be used to arrange other widget next to each other in a row.
The content in the row can be changed dynamically with the `addChild` and `removeLast` methods, following the LIFO
rules.

#### 1.3.2 Column

Very similar to the one before, the `Column` widget arranges its children vertically in a column.
Even if `Row` and `Column` can be used freely one inside the other, it is preferred (when possible) to use column of
rows instead of row of columns as the latter, in some situation, can lead to unwanted behaviour.

#### 1.3.3 GridView

This widget creates a 3x3 grid with all the cells having the same size.
The alignment of the child in the cell can be selected by the corresponding parameter.

#### 1.3.4 Header

This is a little different from all the other layout widgets, as this is used only to arrange text in a prefixed way.
In fact this is meant to be used when a title of some sort should be displayed, with optionally a subtitle.
The two text are centered in the `Header` widget and displayed one below the other.

## 2. Canvas

All the previous widgets defines only how the content should be displayed on the screen, but the actual displaying is
made by the `Canvas` class.
The content of a canvas is made of a title, a subtitle and the actual content (a widget).

When displaying its content, the canvas has two possible behaviours; drawing right after the previous line written
keeping the chronology of all the changes that happened, or deleting the content shown on the console and rewriting a
new one.
The default behaviour is the latter as it simulates a UI updating its content.
The canvas has also a parameter to indicate if it needs to redraw itself if something in its content changes. This is
the default behaviour and should be left as it is to have a better user experience.
If you choose to disable this behaviour, remember to manually update the canvas by showing it again, or the user will
never see the changes!

Although more than one canvas at a time is not forbidden, it's generally preferable and more flexible to have a single
canvas showing content.
To switch from two canvas to one, try to place the two contents in a column, putting in between some empty space /
headers if needed.
If it's really necessary to use more than one canvas at a time, it's advised to disable the auto updating and the
deleting of the console when showing a canvas.

## 3. InputReader

This class is used to read inputs from the console. It has a method `readInput` used to show a message to the user, wait
for it to type a response, and then return the response to the caller of the method.

The `InputReader` can be used in addition to `Validator`s and `Completer`.
The former are used to do a preliminary check on the input based on the rules provided, the latter to suggest the user a
set of command he can choose from and autocomplete if `Tab` is pressed.
If the validation process fails, the `InputReader` will show an error message to the user and ask again for an input.
A more detailed list of each `Completer` can be found [here](https://github.com/jline/jline3/wiki/Completion).

It is perfectly fine, and suggested, to have multiple instances of this class to better handle the single cases in which
an input from the user is needed.
However, reading from multiple `InputReader` at the same time could lead to issue.

## 4. Code example

Here is provided a code example of the usage of this package.
It is not meant to be exhaustive but to provide only a general and simple usage of it.

```java
class FancyCliExample {

    public void runExample() {
        //Creates a new canvas that automatically updates and deletes the console
        Canvas canvas = new Canvas();

        String title = """
                *******  *  *******  *      ******
                   *     *     *     *      *
                   *     *     *     *      ****
                   *     *     *     *      *
                   *     *     *     *****  ******
                """;
        //sets the title as a multiline string and make it appear yellow
        canvas.setTitle(title);
        canvas.setTitleColor(Color.BRIGHT_YELLOW);
        //sets the subtitle as a simple string
        canvas.setSubtitle("This is a normal subtitle!");

        //creates a new empty 3x3 grid
        GridView gridView = new GridView();
        //fills every cell with a text widget displaying the name of the corresponding position
        for (var pos : GridView.Position.values()) {
            gridView.addChild(new Text(pos.toString()), pos);
        }
        //sets the grid created to be the content of the canvas, surrounding it with a doubled border
        canvas.setContent(new Border(gridView, BorderType.DOUBLE));
        //shows the content of the canvas
        canvas.show();
        
        delay();

        //creates a new text widget and put it in the center of the grid
        Text text = new Text("NEW TEXT!");
        gridView.addChild(text, GridView.Position.CENTER);
        
        delay();
        
        //changes the text displayed
        text.setText("same text as before");
        //convert the text in a StatelessWidget and assign it to the grid
        Widget statelessText = StatelessWidget.from(text);
        gridView.addChild(statelessText, GridView.Position.TOP_CENTER);
        
        delay();

        //creates a new InputReader that accept inputs containing "cmd " followed by a single argument
        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator(Validator.beginsWith("cmd "));
        inputReader.setNumOfArgsValidator(Validator.isOfNum(1));

        while (true) {
            try {
                //prompt the user to enter something and reads the input
                String[] inputs = inputReader.readInput("enter \"cmd\" followed by a string");
                String s = inputs[1];
                //if the argument of the command is "exit" closes the program
                if (Objects.equals(s, "exit"))
                    return;
                //change the text that the previously created widget must display
                text.setText(s);
                //manually update the canvas as the Text widget does not anymore respond to updates
                canvas.show();
                /*
                notice how the text in the center (that corresponds to the normal Text widget) changes
                based on the input, but the text in the top-center position (the Text widget converte into stateless)
                always remains the same.
                 */
            } catch (UserRequestExitException e) {
                return;
            }
        }
    }
    
    public void delay(){
        //used to create a delay and see the content change over time
        try {
            Thread.sleep(2000);
        } catch (
                InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
```
