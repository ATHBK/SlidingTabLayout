# SlidingTabLayout for ViewPager
SlidingTabLayout is a library that allows you to create a tab of viewpager with 2 style: 
- SlidingTab
- Tab Normal

  ![demo](ScreenShots/1.gif)
  
  
  ![demo](ScreenShots/2.gif)
  
  
  ![demo](ScreenShots/2017-03-24_1.png)
  
  
  ![demo](ScreenShots/2017-03-24_2.png)
---


# Table of Contents

1. [Gradle Dependency](https://github.com/ATHBK/IndicatorView#gradle-dependency)
   1. [Repository](https://github.com/ATHBK/IndicatorView#repository)
   2. [Dependency](https://github.com/ATHBK/IndicatorView#dependency)
2. [Basic Usage](https://github.com/ATHBK/IndicatorView#basic-usage)
   1. [IndicatorView XML](https://github.com/ATHBK/IndicatorView#indicatorview-xml)
   2. [Attributes](https://github.com/ATHBK/IndicatorView#indicator-attr )
3. [Init Java](https://github.com/ATHBK/IndicatorView#init-from-java)
4. [License](https://github.com/ATHBK/IndicatorView#license)

   
---

# Gradle Dependency


#### Repository

Add this in your root `build.gradle` file (**not** your module `build.gradle` file):

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

#### Dependency

Add this to your module's `build.gradle` file:

```gradle
dependencies {
	compile 'com.github.ATHBK:IndicatorView:v1.1.3'
}
```

---

# Basic Usage

#### SlidingTabLayout XML

To use this SlidingTabLayout in your layout simply copy and paste the xml below. This provides the default indicator. 

1. With TabLayout:
```xml
<com.athbk.slidingtablayout.TabLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/tabLayout"
        android:background="@color/colorGray"
        app:tab_text_color="@color/tab_color_seleted"
/>
````
2. With SlidingTabLayout:
```xml
<com.athbk.slidingtablayout.SlidingTabLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/tabLayout"
        android:background="@color/colorGray"
	/>
```
#### SlidingTabLayout Attr 

There are several other attributes that can be used to configure color text, icon, background tab, size.
1.  With TabLayout:


| Attrrs                 |                        |  type   |
| -----------------------|:----------------------:|---------|
| tab_text_size          | size of text           |dimension|
| tab_text_color         | color of text          |reference|
| tab_under_line_color   | color of under line    |color    |
| tab_under_line_visible | visible of under line  |boolean  |
| tab_auto_align         | auto align width size  |boolean  |
| tab_padding_left       | padding left           |dimension|
| tab_padding_right      | padding right          |dimension|
| tab_padding_top        | padding top            |dimension|
| tab_padding_bottom     | padidng bottom         |dimension|
| tab_background         | customer background tab|reference|

```xml
 	....
	app:indi_color_selected="#ffffff"
	app:indi_color_unselected="#40ffffff"
```
2. With SlidingTabLayout:

```xml
 	....
	app:indi_color_selected="#ffffff"
	app:indi_color_unselected="#40ffffff"
```
---

# Init from Java

#### Java

How to use in . 

```java	
	ViewPagerAdapter adapter = new ViewPagerAdapter(6);
        viewPager.setAdapter(adapter);
        indicator.setType(StyleIndicator.CIRCLE_STYLE_2);
        indicator.setViewPager(viewPager);
```
** Note: You must declare type first, then setViewPager.

** 3 style in class StyleIndicator.

- StyleIndicator.CIRCLE_STYLE_1
- StyleIndicator.CIRCLE_STYLE_2
- StyleIndicator.SHAPE

** Update v1.1.1: change size of type shape
```java
	indicator.setType(StyleIndicator.SHAPE);
        indicator.setHeightShape(16);
        indicator.setWidthShape(50);
        indicator.setViewPager(viewPager);
```
---
# License

    Copyright 2017 ATHBK

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
