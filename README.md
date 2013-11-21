# View Flow similar Path for Android

---

ViewFlow similar Path is an Android UI widget providing a horizontally scrollable ViewGroup with items populated from an Adapter. Scroll down to the bottom of the page for a screen shot.

---

## 使用说明


### In SlideActivity.java

    private int[] photosList = new int[] { R.drawable.bg1, R.drawable.bg2,
			R.drawable.bg3, R.drawable.bg2 };
	String[] titleText = {
			"分享生活<br><small><font size=\"1\">记录和分享每一个时刻</font></small>",
			"记录生活", "点滴", "记录生活" };
			
			
### In layout

     <ViewFlipper
        android:id="@+id/photos"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent"
        android:layout_alignParentTop="true"
        android:layout_below="@+id/titles"
        android:persistentDrawingCache="animation" >

    </ViewFlipper>

    <ViewFlipper
        android:id="@+id/titles"
        android:layout_width="fill_parent"
        android:layout_height="80dp"
        android:layout_marginBottom="100dp"
        android:layout_alignParentBottom="true"
        android:persistentDrawingCache="animation" >
    </ViewFlipper>

    <com.pageIndicator.CirclePageIndicator
    	android:id="@+id/viewflowindic"
    	android:layout_height="wrap_content"
    	android:layout_width="fill_parent" 
 		android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_gravity="center_horizontal"
        android:layout_marginBottom="80dp"
        android:padding="10dip"
     />