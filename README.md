# CheckMe
CheckMe is a library for selection groups, in general two types of selection mode
used in applications, single and multiple. Both of those types are supported by CheckMe.

## Prerequisites
First, dependency must be added to build.gradle file.
```groovy
implementation 'nurisezgin.com.checkme:checkme:1.0.0'
```

## How To Use
* CheckableView be used on its own as a **CompoundButton**.
```xml
    <nurisezgin.com.checkme.CheckableView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/checkable_state_drawable"
        app:isChecked="true"/>
```
* Can use with **CheckBox** or any of **CompoundButton** class.
```xml
    <nurisezgin.com.checkme.CheckableView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:background="@drawable/checkable_state_drawable"
        app:isChecked="true"
        app:useChildrenState="true">
        
        <CheckBox
            android:text="CheckMe"
            android:layout_gravity="left|center_vertical"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />
        
    </nurisezgin.com.checkme.CheckableView>
```

* Types of single selection usage.
```xml
    <nurisezgin.com.checkme.SingleSelectionGroup
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
        <nurisezgin.com.checkme.CheckableView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@drawable/checkable_state_drawable"/>
            
        <nurisezgin.com.checkme.CheckableView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@drawable/checkable_state_drawable"/>
            
    </nurisezgin.com.checkme.SingleSelectionGroup>
```

* Types of multiple selection usage.
```xml
    <nurisezgin.com.checkme.MultipleSelectionGroup
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
        <nurisezgin.com.checkme.CheckableView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@drawable/checkable_state_drawable"/>
            
        <nurisezgin.com.checkme.CheckableView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@drawable/checkable_state_drawable"/>
            
    </nurisezgin.com.checkme.MultipleSelectionGroup>
```

## Authors
* **Nuri SEZGIN**-[Email](acnnurisezgin@gmail.com)

## Licence

```
Copyright 2018 Nuri SEZGİN

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```