# VTDialog

Create beautiful dialogs easily.


![alt text](https://firebasestorage.googleapis.com/v0/b/sontondum.appspot.com/o/Screenshot_20210407-181212.jpg?alt=media)


### Usage

Implementation:

    implementation 'com.github.FivesoftCode:VTDialog:1.0.0'

Create simple dialog:

    VTDialog.from(this)
                .setTitle("Title") //Set dialog title
                .setMessage("This is message text.") //Set dialog message
                .setCancelable(true) //Set cancelable
                .setDialogIcon(null) //Set icon
                .setDismissOnButtonClick(true) //Dismiss dialog automiticaly when any button clicked.
                .setLeftButton("Great", view -> { //Add left button
                    Toast.makeText(this, "Left button clicked!", Toast.LENGTH_LONG).show();
                })
                .setRightButton("Cool!", null) //Add right button
                .show(); //Show dialog
                
Customize your dialog:

    yourDialog.customize(new VTDialog.DialogCustomization(){
    
                    @Override
                    public void customizeDialogBackground(LinearLayout dialogBackground) {
                        dialogBackground.setBackgroundColor(Color.RED);
                    }

                    @Override
                    public void customizeTitleTextView(TextView title) {
                        title.setTextSize(20);
                        title.setTextColor(Color.WHITE);
                    }

                    @Override
                    public void customizeButtons(TextView button) {
                        button.setBackgroundResource(R.drawable.simple_button);
                    }
                    
                });
     
### License

    The MIT License (MIT)

    Copyright (c) 2021 FivesoftCode

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
