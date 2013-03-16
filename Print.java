/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.DriverStationLCD;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Print {
    DriverStationLCD.Line l = null;
    
    String clear = "                                   ";
    //35 character limit on clear.

public void p(int line, String txt){
        switch(line){
            case 1: l = DriverStationLCD.Line.kUser1; break;
            case 2: l = DriverStationLCD.Line.kUser2; break;
            case 3: l = DriverStationLCD.Line.kUser3; break;
            case 4: l = DriverStationLCD.Line.kUser4; break;
            case 5: l = DriverStationLCD.Line.kUser5; break;
            case 6: l = DriverStationLCD.Line.kUser6; break;
        }
      DriverStationLCD.getInstance().println(l, 1, clear);
      DriverStationLCD.getInstance().println(l, 1, txt);
      DriverStationLCD.getInstance().updateLCD();
    }
    public void clear(){
        p(1,clear);
        p(2,clear);
        p(3,clear);
        p(4,clear);
        p(5,clear);
        p(6,clear);
        }
    public void sd(String txt, double n){
        SmartDashboard.putNumber(txt, n);
        //work on this at another time.
        /*NOT IMPLEMENTING THIS YEAR!!!*/
    }
}
