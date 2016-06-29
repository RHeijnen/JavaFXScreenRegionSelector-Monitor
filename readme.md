# JavaFXScreenRegionSelector-Monitor


* For JavaFX Only* 
Screen Region Monitor
- Select a region on the screen
    - Return its co-ordinates
    - Return Rectangle
- Monitor selected screen region
    - Calculate the pixel diffrence from the original screen cap
    - Threaded drawing of said pixels in an extra stage/pop up.


TODO / Buggs: 

- Replace Arrays <- Why did I even..
- Currently, creating the monitor on the same thread after-one-another will break,
  Needs to implement a waiting block so there is no need to split the action over 2 buttons, if so wanted.
  Example :
    ```sh
  ScreenRegionSelector SRS = new ScreenRegionSelector();<br />
  SRS.createScreenMonitor();<br />
  ```
  // Will result in errors, as the screen monitor is launched while the user is still prompt to select an area to start with.
  So currently it HAS to be implemented across 2 buttons.
- See how efficient the logic is, doubtfull
