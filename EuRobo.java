package binance_tarder;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
 
public class EuRobo implements Runnable {
    
        private boolean comando = true;
 
	public void run(){
            try{
		Robot robot = new Robot();
		robot.setAutoDelay(6000);
		while (comando) {
			robot.mouseMove(500, 250);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        
                        robot.setAutoDelay(3000);
			robot.mouseMove(450, 410);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        
                        robot.setAutoDelay(3000);
                        robot.mouseMove(500, 512);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        
                        robot.setAutoDelay(3000);
                        robot.mouseMove(685, 275);
                        robot.mousePress(InputEvent.BUTTON1_MASK);
                        robot.mouseRelease(InputEvent.BUTTON1_MASK);
                        
                        robot.setAutoDelay(3000);
                        
		}
            }catch(AWTException e){
                e.printStackTrace();
            }
	}

    public void setComando(boolean comando) {
        this.comando = comando;
    }
        
        
 
}