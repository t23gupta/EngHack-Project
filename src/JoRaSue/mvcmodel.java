package JoRaSue;

/**
 * mvcmodel
 * Creates model for the game "JoRaSue"
 * @author HeeSue Kim, Tarannum Gupta
 * @version 20170205
 */

import javax.swing.ImageIcon;

import JoRaSue.btnProperty;
import JoRaSue.EndGameProp;

public class mvcmodel {

	btnProperty btnArray[] = new btnProperty[9];
	btnProperty btnArraySingle = new btnProperty();
	boolean blnCompleteLine = false;
	
	
	public static boolean blnCheckOccupied(btnProperty btnArraySingle){
		if (btnArraySingle.btnType != btnTypeEnum.empty){
			return true;	//occupied
		}
		return false;		//not occupied
	}
	
	
	public static EndGameProp propCompLine(btnProperty btnArray[]){
		EndGameProp thisone = new EndGameProp();
		thisone.blnCompleteLine = false;
		thisone.intCompLine1 = 0;
		thisone.intCompLine2 = 0;
		thisone.intCompLine3 = 0;
		
			if ((btnArray[0].btnType != btnTypeEnum.empty) && (btnArray[0].btnType == btnArray[1].btnType) && (btnArray[0].btnType == btnArray[2].btnType) && (btnArray[1].btnType == btnArray[2].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 0;
				thisone.intCompLine2 = 1;
				thisone.intCompLine3 = 2;
			} else if ((btnArray[3].btnType != btnTypeEnum.empty) && (btnArray[3].btnType == btnArray[4].btnType) && (btnArray[3].btnType == btnArray[5].btnType) && (btnArray[4].btnType == btnArray[5].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 3;
				thisone.intCompLine2 = 4;
				thisone.intCompLine3 = 5;
			} else if ((btnArray[6].btnType != btnTypeEnum.empty) && (btnArray[6].btnType == btnArray[7].btnType) && (btnArray[6].btnType == btnArray[8].btnType) && (btnArray[7].btnType == btnArray[8].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 6;
				thisone.intCompLine2 = 7;
				thisone.intCompLine3 = 8;
			} else if ((btnArray[0].btnType != btnTypeEnum.empty) && (btnArray[0].btnType == btnArray[3].btnType) && (btnArray[0].btnType == btnArray[6].btnType) && (btnArray[3].btnType == btnArray[6].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 0;
				thisone.intCompLine2 = 3;
				thisone.intCompLine3 = 6;
			} else if ((btnArray[1].btnType != btnTypeEnum.empty) && (btnArray[1].btnType == btnArray[4].btnType) && (btnArray[1].btnType == btnArray[7].btnType) && (btnArray[4].btnType == btnArray[7].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 1;
				thisone.intCompLine2 = 4;
				thisone.intCompLine3 = 7;
			} else if ((btnArray[2].btnType != btnTypeEnum.empty) && (btnArray[2].btnType == btnArray[5].btnType) && (btnArray[2].btnType == btnArray[8].btnType) && (btnArray[5].btnType == btnArray[8].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 2;
				thisone.intCompLine2 = 5;
				thisone.intCompLine3 = 8;
			} else if ((btnArray[0].btnType != btnTypeEnum.empty) && (btnArray[0].btnType == btnArray[4].btnType) && (btnArray[0].btnType == btnArray[8].btnType) && (btnArray[4].btnType == btnArray[8].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 0;
				thisone.intCompLine2 = 4;
				thisone.intCompLine3 = 8;
			} else if ((btnArray[2].btnType != btnTypeEnum.empty) && (btnArray[2].btnType == btnArray[4].btnType) && (btnArray[2].btnType == btnArray[6].btnType) && (btnArray[4].btnType == btnArray[6].btnType)){
				thisone.blnCompleteLine = true;
				thisone.intCompLine1 = 2;
				thisone.intCompLine2 = 4;
				thisone.intCompLine3 = 6;
			}
			
			return thisone;
		
	}


	
}

