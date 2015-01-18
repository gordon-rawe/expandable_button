# expandable_button 
###Result and effects:

The final result and effects are as follows:

###Usage:

If you are using Eclipse,include expandable_button.jar in your Eclipse and add to build path;//jar file still not build^_^

If you are using Android studio, add dependencies as follows:

dependencies{
	compile "com.gordon.rawe:library:1.0.1"//Ivy center code is waiting to update...^_^
}


After setup, you can call ExpandableButton in your project now.

###Decription:

ExpandableButton is a class extends from the Button, because we want to use the text property,and if you want your button to more easily asign background properties, change my source code to extends from ImageButton, you can declare in your layout xml file as follows:
```xml
<rawe.gordon.com.expandablebutton.ExpandableButton
            android:id="@+id/red"
            android:layout_width="match_parent"
            android:layout_height="60dp"
            android:background="@drawable/expandable_button_background"
            android:text="我是红色"
            android:textAlignment="center"
            app:eb_color="#ff0000"
            android:layout_marginBottom="10dp"
            android:eb_exceed_margin="20dp" />
```
The main property are eb_color and the eb_exceed_margin: 
1. eb_color means the color background effects used to display.
2. the eb_exceed_margin means how much the background circle center can get out, the center coordinates are limited to the range ({-eb_exceed_margin<=x<=width+eb_exceed_margin} and {eb_exceed_margin<=y<=height+eb_exceed_margin})